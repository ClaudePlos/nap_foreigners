package pl.kskowronski.views.workers_after_decision;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kskowronski.data.entity.egeria.ckk.Address;
import pl.kskowronski.data.entity.egeria.ek.WorkerDTO;
import pl.kskowronski.data.entity.global.EatFirma;
import pl.kskowronski.data.entity.inap.NapForeignerLog;
import pl.kskowronski.data.entity.inap.NapForeignerLogDTO;
import pl.kskowronski.data.entity.inap.Requirement;
import pl.kskowronski.data.service.MapperDate;
import pl.kskowronski.data.service.egeria.ckk.AddressRepo;
import pl.kskowronski.data.service.egeria.ckk.AddressService;
import pl.kskowronski.data.service.egeria.ek.WorkerService;
import pl.kskowronski.data.service.global.EatFirmaService;
import pl.kskowronski.data.service.inap.NapForeignerLogService;
import pl.kskowronski.data.service.inap.RequirementKeyService;
import pl.kskowronski.data.service.inap.RequirementService;
import pl.kskowronski.views.main.MainView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Route(value = "after_decision", layout = MainView.class)
@PageTitle("after decision")
@CssImport("./styles/views/workers_to_acceptation/workers-to-acceptation-view.css")
@JavaScript("./js/notification.js")
public class WorkersAfterDecisionView extends HorizontalLayout {

    private NapForeignerLogService napForeignerLogService;
    private EatFirmaService eatFirmaService;
    private AddressService addressService;
    private WorkerService workerService;
    private RequirementService requirementService;
    private RequirementKeyService requirementKeyService;

    private Grid<NapForeignerLogDTO> gridWorkersAfterDecision;

    private MapperDate mapperDate = new MapperDate();

    private Button butPlus = new Button("+");
    private Button butMinus = new Button("-");
    private TextField textPeriod = new TextField("Okres");

    public WorkersAfterDecisionView(@Autowired NapForeignerLogService napForeignerLogService
            , @Autowired AddressService addressService
            , @Autowired WorkerService workerService
            , @Autowired RequirementService requirementService
            , @Autowired RequirementKeyService requirementKeyService
            , @Autowired EatFirmaService eatFirmaService) throws Exception {
        this.napForeignerLogService = napForeignerLogService;
        this.eatFirmaService = eatFirmaService;
        this.addressService = addressService;
        this.workerService = workerService;
        this.requirementService = requirementService;
        this.requirementKeyService = requirementKeyService;
        setId("workers-to-acceptation-view");
        setHeight("95%");

        Date now = Date.from(LocalDate.now().minus(0, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
        textPeriod.setValue(mapperDate.dtYYYYMM.format(now));
        textPeriod.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        butPlus.setWidth("5px");
        butPlus.addClickListener(event -> {
            Long mc = Long.parseLong(textPeriod.getValue().substring(textPeriod.getValue().length() - 2));
            if (mc < 12) {
                mc++;
                String mcS = "0" + mc;
                textPeriod.setValue(textPeriod.getValue().substring(0, 5) + mcS.substring(mcS.length() - 2));
            } else {
                Long year = Long.parseLong(textPeriod.getValue().substring(0, 4));
                year++;
                textPeriod.setValue(year + "-01");
            }
            try {
                getDataForPeriod();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        butMinus.addClickListener(event -> {
            Long mc = Long.parseLong(textPeriod.getValue().substring(textPeriod.getValue().length() - 2));
            if (mc > 1) {
                mc--;
                String mcS = "0" + mc;
                textPeriod.setValue(textPeriod.getValue().substring(0, 5) + mcS.substring(mcS.length() - 2));
            } else {
                Long year = Long.parseLong(textPeriod.getValue().substring(0, 4));
                year--;
                textPeriod.setValue(year + "-12");
            }
            try {
                getDataForPeriod();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        add(butMinus, textPeriod, butPlus);


        this.gridWorkersAfterDecision = new Grid<>(NapForeignerLogDTO.class);
        this.gridWorkersAfterDecision.setHeightFull();

        gridWorkersAfterDecision.setColumns("whenDecided", "whoDecided", "status");


        gridWorkersAfterDecision.addColumn(TemplateRenderer.<NapForeignerLogDTO>of(
                "<div title='[[item.description]]'>[[item.description]]</div>")
                .withProperty("description", NapForeignerLogDTO::getDescription))
                .setHeader("description");

        gridWorkersAfterDecision.addColumn("prcNumber");
        gridWorkersAfterDecision.addColumn("prcName");
        gridWorkersAfterDecision.addColumn("prcSurname");

        gridWorkersAfterDecision.addColumn(new NativeButtonRenderer<NapForeignerLogDTO>("Powiadomienie",
                item -> {
                    Date dateNow = Date.from(LocalDate.now().minus(0, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
                    String dateParam = mapperDate.dtDDMMYYYY.format(dateNow);

                    GenerateNotificationPDF(item.getPrcId(), item.getProcessId(), item.getPrcName(), item.getPrcSurname(), item.getPrcNumber(), dateParam);

                }));

        add(gridWorkersAfterDecision);
        getDataForPeriod();
    }


    private void getDataForPeriod() throws Exception {
        Optional<List<NapForeignerLogDTO>> foreigners = napForeignerLogService.findAllForPeriod(textPeriod.getValue());
        if (foreigners.get().size() == 0) {
            Notification.show("Brak pozycji do wyświetlenia w danym miesiącu", 3000, Notification.Position.MIDDLE);
        }
        gridWorkersAfterDecision.setItems(foreigners.get());
    }

    private void GenerateNotificationPDF(BigDecimal prcId, BigDecimal processId, String prcName, String prcSurname, BigDecimal prcNumber, String dateNow){
        String address = "";
        String frmName = "";
        String startDate = "";

        Optional<Requirement> requirement = requirementService.getRequirementForProcess(processId);
        if (requirement.isPresent()){
            frmName = eatFirmaService.findById(requirement.get().getFrmId()).get().getFrmNazwa();
            startDate = mapperDate.dtDDMMYYYY.format(requirementKeyService.getDateFrom(processId));
        }

        Optional<EatFirma> company = eatFirmaService.findByCompanyName(frmName);
        if (company.isPresent()){
            Optional<Address> companyAddress = addressService.getMainAddressForClient( company.get().getFrmKlId() );
            if ( companyAddress.isPresent() ){
                address = companyAddress.get().getMiejscowosc() + " " + companyAddress.get().getUlica() + " " + companyAddress.get().getNumerDomu()
                        + ", "  + companyAddress.get().getKodPocztowy();

            }
        }

        Date workerBirthDate = workerService.getWorkerBirthDate(prcId);

        String initFunction = "generateNotification($0, $1, $2, $3, $4, $5, $6, $7, $8);";
        UI.getCurrent().getPage().executeJs(initFunction, this, prcName, prcSurname, prcNumber.toString(), dateNow, frmName
        , address
        , mapperDate.dtDDMMYYYY.format(workerBirthDate)
        , startDate);

    }

}
package pl.kskowronski.views.workers_suspended;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kskowronski.data.entity.egeria.ek.WorkerDTO;
import pl.kskowronski.data.entity.inap.NapForeignerLogDTO;
import pl.kskowronski.data.service.MapperDate;
import pl.kskowronski.data.service.inap.NapForeignerLogService;
import pl.kskowronski.views.main.MainView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Route(value = "suspended", layout = MainView.class)
@PageTitle("workers suspended")
@CssImport("./styles/views/workers_to_acceptation/workers-to-acceptation-view.css")
public class WorkersSuspendedView extends HorizontalLayout {

    private NapForeignerLogService napForeignerLogService;

    private Grid<NapForeignerLogDTO> gridWorkersSuspended;

    private MapperDate mapperDate = new MapperDate();

    private Button butPlus = new Button("+");
    private Button butMinus = new Button("-");
    private TextField textPeriod = new TextField("Okres");

    public WorkersSuspendedView(@Autowired NapForeignerLogService napForeignerLogService) throws Exception {
        this.napForeignerLogService = napForeignerLogService;
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


        this.gridWorkersSuspended = new Grid<>(NapForeignerLogDTO.class);
        this.gridWorkersSuspended.setHeightFull();

        gridWorkersSuspended.setColumns("whenDecided", "whoDecided", "status");


        gridWorkersSuspended.addColumn(TemplateRenderer.<NapForeignerLogDTO>of(
                "<div title='[[item.description]]'>[[item.description]]</div>")
                .withProperty("description", NapForeignerLogDTO::getDescription))
                .setHeader("description");

        gridWorkersSuspended.addColumn("prcNumber");
        gridWorkersSuspended.addColumn("prcName");
        gridWorkersSuspended.addColumn("prcSurname");

        add(gridWorkersSuspended);


        getDataForPeriod();
    }



    private void getDataForPeriod() throws Exception {
        Optional<List<NapForeignerLogDTO>> foreigners = napForeignerLogService.findAllSuspendedForPeriod(textPeriod.getValue());
        if (foreigners.get().size() == 0) {
            Notification.show("Brak pozycji do wyświetlenia w danym miesiącu", 3000, Notification.Position.MIDDLE);
        }
        gridWorkersSuspended.setItems(foreigners.get());
    }

}

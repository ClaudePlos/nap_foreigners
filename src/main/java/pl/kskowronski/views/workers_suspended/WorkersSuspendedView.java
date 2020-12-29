package pl.kskowronski.views.workers_suspended;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kskowronski.data.entity.egeria.ek.Occupation;
import pl.kskowronski.data.entity.egeria.ek.WorkerDTO;
import pl.kskowronski.data.entity.inap.*;
import pl.kskowronski.data.service.MailService;
import pl.kskowronski.data.service.MapperDate;
import pl.kskowronski.data.service.MyIcons;
import pl.kskowronski.data.service.egeria.css.DictionaryService;
import pl.kskowronski.data.service.egeria.ek.OccupationRepo;
import pl.kskowronski.data.service.egeria.ek.WorkerService;
import pl.kskowronski.data.service.global.EatFirmaService;
import pl.kskowronski.data.service.inap.*;
import pl.kskowronski.views.main.MainView;

import javax.mail.MessagingException;
import java.math.BigDecimal;
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
    private WorkerService workerService;
    private MailService mailService;
    private ProcessInstanceService processInstanceService;
    private DocumentService documentService;

    private Grid<NapForeignerLogDTO> gridWorkersSuspended;
    private Grid<DocumentDTO> gridDocuments;

    private MapperDate mapperDate = new MapperDate();

    private Button butPlus = new Button("+");
    private Button butMinus = new Button("-");
    private TextField textPeriod = new TextField("Okres");

    private User userLogged;

    Optional<List<NapForeignerLogDTO>> foreigners;

    public WorkersSuspendedView(@Autowired NapForeignerLogService napForeignerLogService
                                , @Autowired WorkerService workerService
                                , @Autowired DocumentService documentService
                                , @Autowired ProcessInstanceService processInstanceService
                                , @Autowired RequirementService requirementService
                                , @Autowired RequirementKeyService requirementKeyService
                                , @Autowired DictionaryService dictionaryService
                                , @Autowired EatFirmaService eatFirmaService
                                , @Autowired OccupationRepo occupationRepo
                                , @Autowired MailService mailService) throws Exception {
        this.napForeignerLogService = napForeignerLogService;
        this.workerService = workerService;
        this.mailService = mailService;
        this.processInstanceService = processInstanceService;
        this.documentService = documentService;
        setId("workers-to-acceptation-view");
        setHeight("95%");

        VaadinSession session = VaadinSession.getCurrent();
        userLogged = session.getAttribute(User.class);

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
        this.gridDocuments = new Grid<>(DocumentDTO.class);
        this.gridWorkersSuspended.setHeightFull();

        gridWorkersSuspended.setColumns();
        gridWorkersSuspended.addComponentColumn(item -> createIconTenderType(item)).setHeader("Refresh").setWidth("40px");

        gridWorkersSuspended.addColumn("processId");
        gridWorkersSuspended.addColumn("whenDecided");
        gridWorkersSuspended.addColumn("whoDecided");
        gridWorkersSuspended.addColumn("status");


        gridWorkersSuspended.addColumn(TemplateRenderer.<NapForeignerLogDTO>of(
                "<div title='[[item.description]]'>[[item.description]]</div>")
                .withProperty("description", NapForeignerLogDTO::getDescription))
                .setHeader("description");

        gridWorkersSuspended.addColumn("prcNumber");
        gridWorkersSuspended.addColumn("prcName");
        gridWorkersSuspended.addColumn("prcSurname");

        gridWorkersSuspended.addColumn(new NativeButtonRenderer<NapForeignerLogDTO>("Umowa",
                item -> {

                    Dialog dialog = new Dialog();
                    dialog.add(new Html("<b>Szczegóły wniosku:</b>"));
                    VerticalLayout vertical = new VerticalLayout ();
                    Optional<Requirement> requirement = requirementService.getRequirementForProcess(item.getProcessId());
                    dialog.add( " " +  eatFirmaService.findById(requirement.get().getFrmId()).get().getFrmNazwa());
                    Optional<List<RequirementKey>> requirements = requirementKeyService.getRequirementForProcess(item.getProcessId());
                    if (requirements.get().size() > 0){
                        requirements.get().stream().forEach( e -> {
                                    String num = e.getLiczba() == BigDecimal.ZERO ? "" : e.getLiczba().toString();
                                    String text = e.getTekst() == null ? "" : e.getTekst();
                                    String date = mapperDate.dtYYYYMMDD.format(e.getDate()).equals("1970-01-01") ? "" : mapperDate.dtYYYYMMDD.format(e.getDate());

                                    if (e.getType().equals("STN_PRACY")){
                                        Optional<Occupation> occupation = occupationRepo.findById(e.getLiczba());
                                        if (occupation.isPresent()){
                                            text = occupation.get().getStnNazwa();
                                        }
                                    }

                                    if (e.getType().equals("PRZEDMIOT_UMOWY")){
                                        Optional<String> subjectOfTheContract = dictionaryService.getSubjectOfTheContract(text);
                                        if (subjectOfTheContract.isPresent()){
                                            text = subjectOfTheContract.get();
                                        }
                                    }

                                    Label lab = new Label(e.getType() + ": "+ num + date + text);
                                    vertical.add(lab);
                                }
                        );

                    } else {
                        Notification.show("Brak informacji odnośnie umowy", 3000, Notification.Position.MIDDLE);
                    }
                    dialog.add(vertical);
                    dialog.setWidth("400px");
                    dialog.setHeight("300px");
                    dialog.open();
                }
        )).setWidth("50px");

        gridWorkersSuspended.addColumn(new NativeButtonRenderer<NapForeignerLogDTO>("Akceptuję",
                item -> {
                    Dialog dialog = new Dialog();
                    dialog.add(new Text("Podaj powód: "));
                    Input inputReject = new Input();
                    inputReject.setValue("OK");
                    Button confirmButton = new Button("Akceptuję", event -> {
                        workerService.acceptForeignerApplication("Zaakceptowane przez HR (" + userLogged.getUsername() + ")" + inputReject.getValue()
                                , item.getProcessId());
                        NapForeignerLog napForeignerLog = new NapForeignerLog();
                        napForeignerLog.setId(item.getId());
                        napForeignerLog.setPrcId(item.getPrcId());
                        napForeignerLog.setStatus(NapForeignerLog.STATUS_ACCEPT);
                        napForeignerLog.setDescription("Zaakceptowane przez HR (" + userLogged.getUsername() + ")");
                        napForeignerLog.setWhoDecided(userLogged.getUsername());
                        napForeignerLog.setWhenDecided(new Date());
                        napForeignerLog.setProcessId(item.getProcessId());
                        napForeignerLogService.save(napForeignerLog);
                        Notification.show("Zaakceptowano process: " + item.getProcessId() + " dla " + item.getPrcSurname(), 3000, Notification.Position.MIDDLE);
                        this.foreigners.get().remove(item); // NEVER instantiate your service or dao yourself, instead inject it into the view
                        this.gridWorkersSuspended.getDataProvider().refreshAll();
                        dialog.close();
                    });
                    dialog.add(inputReject, confirmButton);
                    dialog.open();
                }
        )).setWidth("50px");


        gridWorkersSuspended.addColumn(new NativeButtonRenderer<NapForeignerLogDTO>("Ponowienie",
                item -> {
                    Dialog dialog = new Dialog();
                    String topic = "Obcokrajowcy. Wniosek do poprawy "+ item.getPrcSurname()+ " " + item.getPrcName() + " ProcId: " + item.getProcessId();
                    //dialog.add(new Text("Wiadomość do " + item.getRunProcess() + "@rekeep.pl od " + userLogged.getUsername() + "@rekeep.pl"));
                    //dialog.add(new Text("Temat: " + topic));

                    String runProcess = processInstanceService.getProcessInstance(item.getProcessId()).get().getRunProcess();

                    String content = "<div><b>Wiadomość do: </b>" + runProcess + "@rekeep.pl od " + userLogged.getUsername() + "@rekeep.pl"
                            + "  <br><b>Temat:</b> " + topic +  "<br><b>Tekst:</b></div>";
                    Html html = new Html(content);
                    dialog.add(html);

                    HorizontalLayout hl01 = new HorizontalLayout();
                    TextArea inputReject = new TextArea();
                    inputReject.setHeight("200px");
                    inputReject.setWidth("480px");
                    Button confirmButton = new Button("Zawieszam i wysyłam mail", event -> {
                        workerService.acceptForeignerApplication("Odrzucone przez HR (" + userLogged.getUsername()  +") Powód: " + inputReject.getValue()
                                , item.getProcessId());
                        NapForeignerLog napForeignerLog = new NapForeignerLog();
                        napForeignerLog.setId(item.getId());
                        napForeignerLog.setPrcId(item.getPrcId());
                        napForeignerLog.setStatus(NapForeignerLog.STATUS_SUSPENDED);
                        napForeignerLog.setDescription("Zawieszone przez HR (" + userLogged.getUsername()  +")");
                        napForeignerLog.setWhoDecided(userLogged.getUsername());
                        napForeignerLog.setWhenDecided(new Date());
                        napForeignerLog.setProcessId(item.getProcessId());
                        napForeignerLog.setRefresh("N");
                        napForeignerLogService.save(napForeignerLog);
                        Notification.show("Wniosek zawiszony procId: " + item.getProcessId() + " dla " + item.getPrcSurname(), 3000, Notification.Position.MIDDLE);
                        item.setRefresh("N");
                        this.gridWorkersSuspended.getDataProvider().refreshAll();
                        sendMailTo(runProcess + "@rekeep.pl"
                                ,userLogged.getUsername() + "@rekeep.pl"
                                , inputReject.getValue()
                                , topic );
                        dialog.close();
                    });
                    hl01.add(inputReject);
                    dialog.add(hl01,confirmButton);
                    dialog.open();
                }
        )).setWidth("50px");


        gridDocuments.setColumns("nazwa", "opis", "frmName");

        gridDocuments.addColumn(new NativeButtonRenderer<DocumentDTO>("PDF",
                item -> {
                    String pdfUrl = documentService.generateUrlForPDF(item.getId());
                    UI.getCurrent().getPage().executeJavaScript("window.open('" + pdfUrl + "','_blank')");    ;
                }
        ));

        gridWorkersSuspended.setItemDetailsRenderer(new ComponentRenderer<>(worker -> {
            VerticalLayout layout = new VerticalLayout();
            Optional<List<DocumentDTO>> documents = documentService.getDocumentForPrc(worker.getPrcId());
            gridDocuments.setItems(documents.get());
            layout.add(gridDocuments);
            return layout;
        }));


        add(gridWorkersSuspended);


        getDataForPeriod();
    }



    private void getDataForPeriod() throws Exception {
        foreigners = napForeignerLogService.findAllSuspendedForPeriod(textPeriod.getValue());
        if (foreigners.get().size() == 0) {
            Notification.show("Brak pozycji do wyświetlenia w danym miesiącu", 3000, Notification.Position.MIDDLE);
        }
        gridWorkersSuspended.setItems(foreigners.get());
    }


    private Image createIconTenderType(NapForeignerLogDTO item) {

        Image icon;
        if (item.getRefresh().equals("N")){
            icon = MyIcons.ICON_WAIT.create();
        } else {
            icon = MyIcons.ICON_OK.create();
        }
        return icon;
    }

    private void sendMailTo(String to, String cc, String text, String topic){
        try {
            mailService.sendMail(to, cc,
                    topic ,
                    "<b>Wiadomość od: " + cc + "</b><br><br> "+ text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

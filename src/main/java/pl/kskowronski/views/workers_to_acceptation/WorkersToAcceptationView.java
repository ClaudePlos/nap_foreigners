package pl.kskowronski.views.workers_to_acceptation;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kskowronski.data.entity.egeria.ek.WorkerDTO;
import pl.kskowronski.data.entity.inap.*;
import pl.kskowronski.data.service.MailService;
import pl.kskowronski.data.service.egeria.ek.ForeignerService;
import pl.kskowronski.data.service.egeria.ek.WorkerService;
import pl.kskowronski.data.service.inap.DocumentService;
import pl.kskowronski.data.service.inap.NapForeignerLogService;
import pl.kskowronski.data.service.suncode.SunDokService;
import pl.kskowronski.views.components.ContractDialog;
import pl.kskowronski.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "to_acceptation", layout = MainView.class)
@PageTitle("workers to acceptation")
@CssImport("./styles/views/workers_to_acceptation/workers-to-acceptation-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class WorkersToAcceptationView extends HorizontalLayout {

    private transient ForeignerService foreignerService;
    private SunDokService sunDokService;
    private transient MailService mailService;

    @Autowired
    ContractDialog contractDialog;

    private Grid<WorkerDTO> gridWorkersToAccept;
    private Grid<DocumentDTO> gridDocuments;

    private transient Optional<List<WorkerDTO>> workers;

    private transient User userLogged;

    private TextField filterText = new TextField();
    private Label labSizeRowGrid = new Label("0");

    public WorkersToAcceptationView(@Autowired WorkerService workerService
            , @Autowired MailService mailService
            , @Autowired ForeignerService foreignerService
            , @Autowired DocumentService documentService
            , @Autowired NapForeignerLogService napForeignerLogService
            , SunDokService sunDokService) {
        this.foreignerService = foreignerService;
        this.mailService = mailService;
        this.sunDokService = sunDokService;
        setId("workers-to-acceptation-view");
        setHeight("95%");

        Checkbox checkbox = new Checkbox();
        checkbox.setLabel("Polskie?");
        checkbox.setValue(false);
        checkbox.addValueChangeListener(event -> {getWorkersToAccept();});

        VaadinSession session = VaadinSession.getCurrent();
        userLogged = session.getAttribute(User.class);

        this.gridWorkersToAccept = new Grid<>(WorkerDTO.class);
        this.gridDocuments = new Grid<>(DocumentDTO.class);
        this.gridWorkersToAccept.setHeightFull();
        // hide details after click
        this.gridWorkersToAccept.setSelectionMode(Grid.SelectionMode.NONE);

        getWorkersToAccept();

        filterText.setPlaceholder("Search...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
        add(filterText, new Label("Ilość wierszy: "), labSizeRowGrid);

        gridWorkersToAccept.setColumns();
        gridWorkersToAccept.addColumn("status").setWidth("25px");
        gridWorkersToAccept.addColumn("procesId");
        gridWorkersToAccept.addColumn("prcNumer");
        gridWorkersToAccept.addColumn("prcNazwisko");

        gridWorkersToAccept.addColumn("prcImie");
        gridWorkersToAccept.addColumn("prcObywatelstwo");
        gridWorkersToAccept.addColumn("dataOd");

        //gridWorkersToAccept.setColumns("status", "procesId", "prcNumer", "prcNazwisko", "prcImie", "prcObywatelstwo", "dataOd");

        gridWorkersToAccept.addColumn(WorkerDTO::getTypeOfAgreement).setHeader("Um").setWidth("50px");;

        gridWorkersToAccept.addColumn("runDate").setWidth("120px");

        gridDocuments.setColumns("nazwa");
        gridDocuments.addColumn("opis").setResizable(true);

        gridWorkersToAccept.addColumn(TemplateRenderer.<WorkerDTO> of(
                "<div title='[[item.sk]] [[item.runProcess]] ProcId:[[item.processId]]'>[[item.sk]]<br><small>[[item.runProcess]]</small> [<small>[[item.platform]]</small>]</div>")
                .withProperty("sk", WorkerDTO::getSk)
                .withProperty("processId", WorkerDTO::getProcesId)
                .withProperty("runProcess", WorkerDTO::getRunProcess)
                .withProperty("platform", WorkerDTO::getPlatform))
                .setHeader("runProcess").setResizable(true);

        gridWorkersToAccept.addColumn(new NativeButtonRenderer<WorkerDTO>("Umowa",
                item -> {
                    VerticalLayout vertical = new VerticalLayout ();
                    contractDialog.openContract(item);
                    contractDialog.add(vertical);
                    contractDialog.open();
                }
        )).setWidth("50px");

        gridWorkersToAccept.addColumn(new NativeButtonRenderer<WorkerDTO>("Akceptuję",
                item -> {
                    VerticalLayout v01 = new VerticalLayout();
                    Dialog dialog = new Dialog();
                    dialog.add(new Text("Podaj powód: "));
                    TextArea inputReject = new TextArea();
                    inputReject.setHeight("180px");
                    inputReject.setWidth("400px");
                    inputReject.setValue("OK");
                    Button confirmButton = new Button("Akceptuję", event -> {
                        workerService.acceptForeignerApplication("Zaakceptowane przez HR (" + userLogged.getUsername() + ")" + inputReject.getValue()
                                , item.getProcesId());
                        NapForeignerLog napForeignerLog = new NapForeignerLog();
                        napForeignerLog.setPrcId(item.getPrcId());
                        napForeignerLog.setStatus(NapForeignerLog.STATUS_ACCEPT);
                        napForeignerLog.setDescription("Zaakceptowane przez HR (" + userLogged.getUsername() + ") Decyzja:" + inputReject.getValue() );
                        napForeignerLog.setWhoDecided(userLogged.getUsername());
                        napForeignerLog.setWhenDecided(new Date());
                        napForeignerLog.setProcessId(item.getProcesId());
                        napForeignerLog.setWhoRunInInap(item.getRunProcess());
                        napForeignerLog.setSkForApplication(item.getSk());
                        napForeignerLog.setPlatform(item.getPlatform());
                        napForeignerLogService.save(napForeignerLog);
                        Notification.show("Zaakceptowano process: " + item.getProcesId() + " dla " + item.getPrcNazwisko(), 3000, Notification.Position.MIDDLE);
                        this.workers.get().remove(item); // NEVER instantiate your service or dao yourself, instead inject it into the view
                        this.gridWorkersToAccept.getDataProvider().refreshAll();
                        dialog.close();
                        refreshNumSize();
                    });
                    v01.add(inputReject, confirmButton);
                    dialog.add(v01);
                    dialog.open();
                }
        )).setWidth("50px");


        gridWorkersToAccept.addColumn(new NativeButtonRenderer<WorkerDTO>("Zawieszam",
                item -> {
                    Dialog dialog = new Dialog();

                    String to = "";
                    if (item.getPlatform().equals("suncode")) {
                        to = item.getRunProcess();
                    } else {
                        to = item.getRunProcess() + "@rekeep.pl";
                    }

                    String topic = "Obcokrajowcy. Wniosek do poprawy "+ item.getPrcNazwisko() + " " + item.getPrcImie() + " ProcId: " + item.getProcesId();

                    String content = "<div><b>Wiadomość do: </b>" + to +  " od " + userLogged.getUsername() + "@rekeep.pl"
                            + "  <br><b>Temat:</b> " + topic +  "<br><b>Tekst:</b></div>";
                    Html html = new Html(content);
                    dialog.add(html);

                    HorizontalLayout hl01 = new HorizontalLayout();
                    TextArea inputReject = new TextArea();
                    inputReject.setHeight("200px");
                    inputReject.setWidth("480px");
                    String finalTo = to;
                    Button confirmButton = new Button("Zawieszam i wysyłam mail", event -> {
                        workerService.acceptForeignerApplication("Zawieszone przez HR (" + userLogged.getUsername()  +") Powód: " + inputReject.getValue()
                                , item.getProcesId());
                        NapForeignerLog napForeignerLog = new NapForeignerLog();
                        napForeignerLog.setPrcId(item.getPrcId());
                        napForeignerLog.setStatus(NapForeignerLog.STATUS_SUSPENDED);
                        napForeignerLog.setDescription("Zawieszone przez HR (" + userLogged.getUsername()  +"): " + inputReject.getValue());
                        napForeignerLog.setWhoDecided(userLogged.getUsername());
                        napForeignerLog.setWhenDecided(new Date());
                        napForeignerLog.setProcessId(item.getProcesId());
                        napForeignerLog.setRefresh("N");
                        napForeignerLog.setWhoRunInInap(item.getRunProcess());
                        napForeignerLog.setSkForApplication(item.getSk());
                        napForeignerLog.setPlatform(item.getPlatform());
                        napForeignerLogService.save(napForeignerLog);
                        Notification.show("Wniosek zawiszony procId: " + item.getProcesId() + " dla " + item.getPrcNazwisko(), 3000, Notification.Position.MIDDLE);
                        this.workers.get().remove(item); // NEVER instantiate your service or dao yourself, instead inject it into the view
                        this.gridWorkersToAccept.getDataProvider().refreshAll();



                        sendMailTo(finalTo
                                ,userLogged.getUsername() + "@rekeep.pl"
                                , inputReject.getValue()
                                , topic );
                        dialog.close();
                        refreshNumSize();
                    });
                    hl01.add(inputReject);
                    dialog.add(hl01,confirmButton);
                    dialog.open();
                }
        )).setWidth("50px");



        gridWorkersToAccept.addColumn(new NativeButtonRenderer<WorkerDTO>("Odrzucam",
                item -> {
                    Dialog dialog = new Dialog();

                    dialog.add(new Text("Podaj powód: "));
                    Input inputReject = new Input();
                    Button confirmButton = new Button("Odrzucam", event -> {
                        workerService.acceptForeignerApplication("Odrzucone przez HR (" + userLogged.getUsername()  +") Powód: " + inputReject.getValue()
                                , item.getProcesId());
                        NapForeignerLog napForeignerLog = new NapForeignerLog();
                        napForeignerLog.setPrcId(item.getPrcId());
                        napForeignerLog.setStatus(NapForeignerLog.STATUS_NO_ACCEPT);
                        napForeignerLog.setDescription("Odrzucone przez HR (" + userLogged.getUsername()  +") Powód: " + inputReject.getValue());
                        napForeignerLog.setWhoDecided(userLogged.getUsername());
                        napForeignerLog.setWhenDecided(new Date());
                        napForeignerLog.setProcessId(item.getProcesId());
                        napForeignerLog.setWhoRunInInap(item.getRunProcess());
                        napForeignerLog.setSkForApplication(item.getSk());
                        napForeignerLog.setPlatform(item.getPlatform());
                        napForeignerLogService.save(napForeignerLog);
                        Notification.show("Odrzucone process: " + item.getProcesId() + " dla " + item.getPrcNazwisko(), 3000, Notification.Position.MIDDLE);
                        this.workers.get().remove(item); // NEVER instantiate your service or dao yourself, instead inject it into the view
                        this.gridWorkersToAccept.getDataProvider().refreshAll();

                        String topic = "Obcokrajowcy. Wniosek dla "+ item.getPrcNazwisko() + " " + item.getPrcImie() + " odrzucony. ProcId: " + item.getProcesId();

                        String to = "";
                        if (item.getPlatform().equals("suncode")) {
                            to = item.getRunProcess();
                        } else {
                            to = item.getRunProcess() + "@rekeep.pl";
                        }


                        sendMailTo( to
                                ,userLogged.getUsername() + "@rekeep.pl"
                                , inputReject.getValue()
                                , topic );

                        dialog.close();
                        refreshNumSize();
                    });
                    dialog.add(inputReject, confirmButton);
                    dialog.open();

                }
        )).setWidth("50px");


        gridDocuments.addColumn(new NativeButtonRenderer<DocumentDTO>("PDF",
                item -> {
                    String pdfUrl = documentService.generateUrlForPDF(item.getId(), item.getPlatform(), item.getPath());

//                    Dialog dialog = new Dialog();
//                    dialog.setWidth("800px");
//                    dialog.setHeight("600px");
//
//                    IFrame pdfViewer = new IFrame(pdfUrl);
//                    pdfViewer.setSizeFull();
//
//                    dialog.add(pdfViewer);
//                    dialog.open();

                    UI.getCurrent().getPage().executeJavaScript("window.open('" + pdfUrl + "','_blank')");
                }
        ));

        gridWorkersToAccept.setItemDetailsRenderer(new ComponentRenderer<>(worker -> {
            VerticalLayout layout = new VerticalLayout();
            Optional<List<DocumentDTO>> documents = documentService.getDocumentForPrc(worker.getPrcId());
            Optional<List<DocumentDTO>> documentsSun = sunDokService.getDocumentForPrc(worker.getPrcId());

            if (documentsSun.isPresent()) {
                documentsSun.get().forEach( doc -> {
                    documents.get().add(doc);
                });
            }

            gridDocuments.setItems(documents.get());

            layout.add(gridDocuments);
            return layout;
        }));

        add(gridWorkersToAccept);
    }

    private void getWorkersToAccept(){
        workers = foreignerService.findAll();
        if ( workers.get().isEmpty() ){
            Notification.show("Brak kandydatów do akceptacji", 3000, Notification.Position.MIDDLE);
        }

        gridWorkersToAccept.setItems(workers.get());
        refreshNumSize();
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

    private void updateList() {
        List<WorkerDTO> workersDTO = workers.get().stream()
                .filter(item -> item.getPrcNazwisko().toUpperCase().contains(filterText.getValue().toUpperCase())
                        || item.getPrcImie().toUpperCase().contains(filterText.getValue().toUpperCase())
                        || item.getRunProcess().toUpperCase().contains(filterText.getValue().toUpperCase())
                        || item.getProcesId().toString().contains(filterText.getValue().toUpperCase())
                        || item.getSk().contains(filterText.getValue().toUpperCase())
                        || item.getPlatform().toUpperCase().contains(filterText.getValue().toUpperCase())
                        || item.getTypeOfAgreement().contains(filterText.getValue().toUpperCase())
                )
                .collect(Collectors.toList());
        gridWorkersToAccept.setItems(workersDTO);
        labSizeRowGrid.setText(String.valueOf(workersDTO.size()));
    }

    private void refreshNumSize(){
        labSizeRowGrid.setText(String.valueOf(workers.get().size()));
    }

}

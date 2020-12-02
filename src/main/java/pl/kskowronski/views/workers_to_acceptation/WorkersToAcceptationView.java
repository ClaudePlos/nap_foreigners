package pl.kskowronski.views.workers_to_acceptation;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kskowronski.data.entity.egeria.ek.Worker;
import pl.kskowronski.data.entity.egeria.ek.WorkerDTO;
import pl.kskowronski.data.entity.inap.DocumentDTO;
import pl.kskowronski.data.entity.inap.NapForeignerLog;
import pl.kskowronski.data.entity.inap.RequirementKey;
import pl.kskowronski.data.entity.inap.User;
import pl.kskowronski.data.service.MapperDate;
import pl.kskowronski.data.service.egeria.ek.WorkerService;
import pl.kskowronski.data.service.inap.DocumentService;
import pl.kskowronski.data.service.inap.NapForeignerLogService;
import pl.kskowronski.data.service.inap.RequirementKeyService;
import pl.kskowronski.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Route(value = "to_acceptation", layout = MainView.class)
@PageTitle("workers to acceptation")
@CssImport("./styles/views/workers_to_acceptation/workers-to-acceptation-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class WorkersToAcceptationView extends HorizontalLayout {

    WorkerService workerService;
    DocumentService documentService;
    NapForeignerLogService napForeignerLogService;

    private Grid<WorkerDTO> gridWorkersToAccept;
    private Grid<DocumentDTO> gridDocuments;

    private Optional<List<WorkerDTO>> workers;

    private User userLogged;

    private MapperDate mapperDate = new MapperDate();

    public WorkersToAcceptationView(@Autowired WorkerService workerService
            , @Autowired DocumentService documentService
            , @Autowired NapForeignerLogService napForeignerLogService
            , @Autowired RequirementKeyService requirementKeyService) {
        this.workerService = workerService;
        this.documentService = documentService;
        this.napForeignerLogService = napForeignerLogService;
        setId("workers-to-acceptation-view");
        setHeight("95%");

        Checkbox checkbox = new Checkbox();
        checkbox.setLabel("Polskie?");
        checkbox.setValue(false);
        checkbox.addValueChangeListener(event -> {getWorkersToAccept(event.getValue());});
        //add(checkbox);

        VaadinSession session = VaadinSession.getCurrent();
        userLogged = session.getAttribute(User.class);

        this.gridWorkersToAccept = new Grid<>(WorkerDTO.class);
        this.gridDocuments = new Grid<>(DocumentDTO.class);
        this.gridWorkersToAccept.setHeightFull();
        // hide details after click
        this.gridWorkersToAccept.setSelectionMode(Grid.SelectionMode.NONE);

        getWorkersToAccept(false);

        gridWorkersToAccept.setColumns("status","prcNumer", "prcNazwisko", "prcImie", "prcObywatelstwo", "runDate");
        gridDocuments.setColumns("nazwa", "opis", "frmId");

        gridWorkersToAccept.addColumn(TemplateRenderer.<WorkerDTO> of(
                "<div title='[[item.sk]] [[item.runProcess]]'>[[item.sk]]<br><small>[[item.runProcess]]</small></div>")
                .withProperty("sk", WorkerDTO::getSk)
                .withProperty("runProcess", WorkerDTO::getRunProcess))
                .setHeader("runProcess");

        gridWorkersToAccept.addColumn(new NativeButtonRenderer<WorkerDTO>("Umowa",
                item -> {

                    Dialog dialog = new Dialog();
                    dialog.add(new Html("<b>Szczegóły wniosku:</b>"));
                    VerticalLayout vertical = new VerticalLayout ();
                    Optional<List<RequirementKey>> requirements = requirementKeyService.getRequirementForProcess(item.getProcesId());
                    if (requirements.get().size() > 0){
                        requirements.get().stream().forEach( e -> {
                                    String num = e.getLiczba() == BigDecimal.ZERO ? "" : e.getLiczba().toString();
                                    String text = e.getTekst() == null ? "" : e.getTekst();
                                    String date = mapperDate.dtYYYYMMDD.format(e.getDate()).equals("1970-01-01") ? "" : mapperDate.dtYYYYMMDD.format(e.getDate());
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

        gridWorkersToAccept.addColumn(new NativeButtonRenderer<WorkerDTO>("Akceptuję",
                item -> {
                    workerService.acceptForeignerApplication("Zaakceptowane przez HR (" + userLogged.getUsername()  +")", item.getProcesId());
                    NapForeignerLog napForeignerLog = new NapForeignerLog();
                    napForeignerLog.setPrcId(item.getPrcId());
                    napForeignerLog.setStatus(NapForeignerLog.STATUS_ACCEPT);
                    napForeignerLog.setDescription("Zaakceptowane przez HR (" + userLogged.getUsername()  +")");
                    napForeignerLog.setWhoDecided(userLogged.getUsername());
                    napForeignerLog.setWhenDecided(new Date());
                    napForeignerLogService.save(napForeignerLog);
                    Notification.show("Zaakceptowano process: " + item.getProcesId() + " dla " + item.getPrcNazwisko(), 3000, Notification.Position.MIDDLE);
                    this.workers.get().remove(item); // NEVER instantiate your service or dao yourself, instead inject it into the view
                    this.gridWorkersToAccept.getDataProvider().refreshAll();
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
                        napForeignerLogService.save(napForeignerLog);
                        Notification.show("Odrzucone process: " + item.getProcesId() + " dla " + item.getPrcNazwisko(), 3000, Notification.Position.MIDDLE);
                        this.workers.get().remove(item); // NEVER instantiate your service or dao yourself, instead inject it into the view
                        this.gridWorkersToAccept.getDataProvider().refreshAll();
                        dialog.close();
                    });
                    dialog.add(inputReject, confirmButton);
                    dialog.open();

                }
        )).setWidth("50px");


        gridDocuments.addColumn(new NativeButtonRenderer<DocumentDTO>("PDF",
                item -> {
                    String pdfUrl = documentService.generateUrlForPDF(item.getId());
                    UI.getCurrent().getPage().executeJavaScript("window.open('" + pdfUrl + "','_blank')");    ;
                }
        ));

        gridWorkersToAccept.setItemDetailsRenderer(new ComponentRenderer<>(worker -> {
            VerticalLayout layout = new VerticalLayout();
            Optional<List<DocumentDTO>> documents = documentService.getDocumentForPrc(worker.getPrcId());
            gridDocuments.setItems(documents.get());
            layout.add(gridDocuments);
            return layout;
        }));


//        gridWorkerAccept.setItemDetailsRenderer(TemplateRenderer.<WorkerDTO>of(
//                "<div style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box;'>"
//                        + "<div>Hi! My name is <b>[[item.firstName]]!</b></div>"
//                        + "<div>Test</div>"
//                        + "</div>")
//                .withProperty("firstName", WorkerDTO::getPrcNazwisko)
//                .withEventHandler("handleClick", worker -> {
////                    Optional<List<DocumentDTO>> documents = documentService.getDocumentForPrc(worker.getPrcId());
////                    Grid<DocumentDTO> gridDocuments = new Grid<DocumentDTO>();
////                    gridDocuments.setItems(documents.get());
////                    add(gridDocuments);
//                    Notification.show("Tescik");
//                    gridWorkerAccept.getDataProvider().refreshItem(worker);
//                }));



        add(gridWorkersToAccept);
    }

    private void getWorkersToAccept( Boolean polishNationality){
        workers = workerService.listWorkersToAccept(polishNationality);
        if ( workers.get().size() == 0 ){
            Notification.show("Brak kandydatów do akceptacji", 3000, Notification.Position.MIDDLE);
        }

        gridWorkersToAccept.setItems(workers.get());
    }



}

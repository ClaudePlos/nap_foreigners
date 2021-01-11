package pl.kskowronski.views.components;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kskowronski.data.entity.egeria.ek.Occupation;
import pl.kskowronski.data.entity.egeria.ek.WorkerDTO;
import pl.kskowronski.data.entity.inap.NapForeignerLogDTO;
import pl.kskowronski.data.entity.inap.Requirement;
import pl.kskowronski.data.entity.inap.RequirementKey;
import pl.kskowronski.data.service.MapperDate;
import pl.kskowronski.data.service.egeria.css.DictionaryService;
import pl.kskowronski.data.service.egeria.ek.OccupationRepo;
import pl.kskowronski.data.service.global.EatFirmaService;
import pl.kskowronski.data.service.inap.RequirementKeyService;
import pl.kskowronski.data.service.inap.RequirementService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@UIScope
public class ContractDialog extends Dialog {


    private RequirementService requirementService;
    private EatFirmaService eatFirmaService;
    private RequirementKeyService requirementKeyService;
    private DictionaryService dictionaryService;
    private OccupationRepo occupationRepo;

    private MapperDate mapperDate = new MapperDate();

    @Autowired
    public ContractDialog(RequirementService requirementService, EatFirmaService eatFirmaService
            , RequirementKeyService requirementKeyService, DictionaryService dictionaryService,  OccupationRepo occupationRepo) {
        this.requirementService = requirementService;
        this.eatFirmaService= eatFirmaService;
        this.requirementKeyService = requirementKeyService;
        this.dictionaryService = dictionaryService;
        this.occupationRepo = occupationRepo;
    }

    public void openContract(NapForeignerLogDTO item) {
        this.removeAll();
        add(new Html("<b>Szczegóły wniosku:</b>"));
        VerticalLayout vertical = new VerticalLayout ();
        Optional<Requirement> requirement = requirementService.getRequirementForProcess(item.getProcessId());
        add( " " +  eatFirmaService.findById(requirement.get().getFrmId()).get().getFrmNazwa());
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
        add(vertical);

        final Button close = new Button("Zamknij", e -> {
            close();
        });
        add(close);
    }

    public void openContract(WorkerDTO item) {
        this.removeAll();
        add(new Html("<b>Szczegóły wniosku:</b>"));
        VerticalLayout vertical = new VerticalLayout ();
        Optional<Requirement> requirement = requirementService.getRequirementForProcess(item.getProcesId());
        add( " " +  eatFirmaService.findById(requirement.get().getFrmId()).get().getFrmNazwa());
        Optional<List<RequirementKey>> requirements = requirementKeyService.getRequirementForProcess(item.getProcesId());
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
        add(vertical);

        final Button close = new Button("Zamknij", e2 -> {
            close();
        });
        add(close);
    }


}

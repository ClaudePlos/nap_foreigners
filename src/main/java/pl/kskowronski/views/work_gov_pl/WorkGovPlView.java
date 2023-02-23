package pl.kskowronski.views.work_gov_pl;

import com.helger.commons.csv.CSVWriter;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Sort;
import pl.kskowronski.data.entity.egeria.ek.WorkGovpl;
import pl.kskowronski.data.service.egeria.ek.WorkGovplRepo;
import pl.kskowronski.views.main.MainView;
import com.vaadin.flow.component.button.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Route(value = "work_gov_pl", layout = MainView.class)
@PageTitle("praca.gov.pl")
@RouteAlias(value = "work_gov_pl", layout = MainView.class)
public class WorkGovPlView extends VerticalLayout {

    private WorkGovplRepo workGovplRepo;
    private Grid<WorkGovpl> grid = new Grid<>(WorkGovpl.class, false);

    private List<WorkGovpl> listToExcel = new ArrayList<>();
    private DateTimeFormatter dtDDMMYYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    private Anchor a;
    public WorkGovPlView(WorkGovplRepo workGovplRepo) {
        this.workGovplRepo = workGovplRepo;

        Button clearTable = new Button("Czyść tabelę", e -> {
            workGovplRepo.deleteAll();
            this.getDataFromDB();
        });

        Button buttRefresh = new Button("Odśwież", e -> {
            this.getDataFromDB();
        });



        grid.addColumn(WorkGovpl::getFrmNazwa).setHeader("Firma").setResizable(true);
        grid.addColumn(WorkGovpl::getPrcNumer).setHeader("Numer").setResizable(true);
        grid.addColumn(WorkGovpl::getPrcImie).setHeader("Imie").setResizable(true);
        grid.addColumn(WorkGovpl::getPrcImie2).setHeader("Imie2").setResizable(true);
        grid.addColumn(WorkGovpl::getPrcNazwisko).setHeader("PrcNazwisko").setResizable(true);
        grid.addColumn(WorkGovpl::getPrcPlec).setHeader("PrcPlec").setResizable(true);
        grid.addColumn(WorkGovpl::getPrcDataUr).setHeader("PrcDataUr").setResizable(true);
        grid.addColumn(WorkGovpl::getPrcPesel).setHeader("PrcPesel").setResizable(true);
        grid.addColumn(WorkGovpl::getPrcObywatelstwo).setHeader("PrcObywatelstwo").setResizable(true);
        grid.addColumn(WorkGovpl::getPrcPaszport).setHeader("PrcPaszport").setResizable(true);
        grid.addColumn(WorkGovpl::getPrcDowodOsob).setHeader("PrcDowodOsob").setResizable(true);
        grid.addColumn(WorkGovpl::getZatDataPrzyj).setHeader("ZatDataPrzyj").setResizable(true);
        grid.addColumn(WorkGovpl::getZatDataZmiany).setHeader("ZatDataZmiany").setResizable(true);
        grid.addColumn(WorkGovpl::getStnNazwa).setHeader("StnNazwa").setResizable(true);
        grid.addColumn(WorkGovpl::getKodZawodu).setHeader("KodZawodu").setResizable(true);
        grid.addColumn(WorkGovpl::getRodzajUmowy).setHeader("RodzajUmowy").setResizable(true);
        grid.addColumn(WorkGovpl::getSkKod).setHeader("SkKod").setResizable(true);
        grid.addColumn(WorkGovpl::getAdrKodPocztowy).setHeader("AdrKodPocztowy").setResizable(true);
        grid.addColumn(WorkGovpl::getWojewodztwo).setHeader("Wojewodztwo").setResizable(true);
        grid.addColumn(WorkGovpl::getAdrGmina).setHeader("AdrGmina").setResizable(true);
        grid.addColumn(WorkGovpl::getAdrUlica).setHeader("AdrUlica").setResizable(true);
        grid.addColumn(WorkGovpl::getAdrPowiat).setHeader("AdrPowiat").setResizable(true);
        grid.addColumn(WorkGovpl::getAdrMiejscowosc).setHeader("AdrMiejscowosc").setResizable(true);
        grid.addColumn(WorkGovpl::getAdrNumberDomu).setHeader("AdrNumberDomu").setResizable(true);
        grid.addColumn(WorkGovpl::getAdrNumerLokalu).setHeader("AdrNumerLokalu").setResizable(true);
        grid.addColumn(WorkGovpl::getEtat).setHeader("Etat").setResizable(true);
        grid.addColumn(WorkGovpl::getZatStawka).setHeader("ZatStawka").setResizable(true);





        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        add(new HorizontalLayout(clearTable, buttRefresh), grid);

        grid.addSelectionListener(selection -> {
            try {
                this.refreshDataToExcel();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        this.getDataFromDB();
        setPadding(false);

    }

    private void getDataFromDB() {
        List<WorkGovpl> list = workGovplRepo.findAll(Sort.by(Sort.Direction.ASC, "frmNazwa", "prcNazwisko"));
        grid.setItems(list);
    }

    private void refreshDataToExcel() throws IOException {

        listToExcel.clear();
        grid.getSelectedItems().stream().forEach( i -> {
            listToExcel.add(i);
        });



        if (a != null){
            remove(a);
        }

        a = new Anchor(new StreamResource("obcokrajowcy_egeria.csv", this::getInputStream), "Export do CSV");
        a.getElement().setAttribute("download", true);

        add(a);
    }


    private InputStream getInputStream() {

        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);
        csvWriter.setSeparatorChar(';');
        csvWriter.writeNext("Firma", "Numer", "Imie", "Imie2", "Nazwisko", "Plec", "DataUr", "Pesel", "Obywatelstwo", "Paszport", "DowodOsob", "DataPrzyj", "DataZmiany", "Stanowisko", "KodZawodu", "RodzajUmowy", "SkKod", "KodPocztowy", "Wojewodztwo", "Gmina", "Ulica", "Powiat", "Miejscowosc", "NumDomu", "NumLokalu", "Etat", "Stawka");
        listToExcel.forEach(c -> csvWriter.writeNext("" + c.getFrmNazwa(), c.getPrcNumer().toString(), c.getPrcImie(), c.getPrcImie2() , c.getPrcNazwisko() , c.getPrcPlec(), dtDDMMYYYY.format(c.getPrcDataUr()) , c.getPrcPesel() , c.getPrcObywatelstwo() , c.getPrcPaszport()
                ,  c.getPrcDowodOsob() , dtDDMMYYYY.format(c.getZatDataPrzyj()), dtDDMMYYYY.format(c.getZatDataZmiany()) , c.getStnNazwa() , c.getKodZawodu() , c.getRodzajUmowy() , c.getSkKod() , c.getAdrKodPocztowy()
                , c.getWojewodztwo() , c.getAdrGmina() , c.getAdrUlica() , c.getAdrPowiat() , c.getAdrMiejscowosc() , c.getAdrNumberDomu() , c.getAdrNumerLokalu(), c.getEtat() , c.getZatStawka() + "" ));

        try {
            return IOUtils.toInputStream(stringWriter.toString(), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

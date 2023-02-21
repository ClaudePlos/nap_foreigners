package pl.kskowronski.views.work_gov_pl;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import org.vaadin.reports.PrintPreviewReport;
import pl.kskowronski.data.entity.egeria.ek.WorkGovpl;
import pl.kskowronski.data.service.egeria.ek.WorkGovplRepo;
import pl.kskowronski.views.main.MainView;
import com.vaadin.flow.component.button.Button;

import java.util.ArrayList;
import java.util.List;

@Route(value = "work_gov_pl", layout = MainView.class)
@PageTitle("praca.gov.pl")
@RouteAlias(value = "work_gov_pl", layout = MainView.class)
public class WorkGovPlView extends VerticalLayout {

    private WorkGovplRepo workGovplRepo;
    private Grid<WorkGovpl> grid = new Grid<>(WorkGovpl.class, false);

    private List<WorkGovpl> listToExcel = new ArrayList<>();
    private StreamResource excel;
    private PrintPreviewReport report = new PrintPreviewReport<>(WorkGovpl.class,"prcNumer","prcImie","prcImie2","prcNazwisko"
            ,"prcPlec","prcDataUr","prcPesel","prcObywatelstwo","prcPaszport","prcDowodOsob","zatDataPrzyj","zatDataZmiany","stnNazwa"
            ,"kodZawodu","rodzajUmowy","skKod","adrKodPocztowy","wojewodztwo","adrGmina","adrUlica"
            ,"adrPowiat","adrMiejscowosc","adrNumberDomu","adrNumerLokalu","etat","zatStawka"
    );
    private Anchor a;
    public WorkGovPlView(WorkGovplRepo workGovplRepo) {
        this.workGovplRepo = workGovplRepo;

        Button clearTable = new Button("Czyść tabelę", e -> {
            workGovplRepo.deleteAll();
            this.getDataFromDB();
        });

        grid.addColumn(WorkGovpl::getPrcNumer).setHeader("Numer");
        grid.addColumn(WorkGovpl::getPrcImie).setHeader("Imie");
        grid.addColumn(WorkGovpl::getPrcImie2).setHeader("Imie2");
        grid.addColumn(WorkGovpl::getPrcNazwisko).setHeader("PrcNazwisko");
        grid.addColumn(WorkGovpl::getPrcPlec).setHeader("PrcPlec");
        grid.addColumn(WorkGovpl::getPrcDataUr).setHeader("PrcDataUr");
        grid.addColumn(WorkGovpl::getPrcPesel).setHeader("PrcPesel");
        grid.addColumn(WorkGovpl::getPrcObywatelstwo).setHeader("PrcObywatelstwo");
        grid.addColumn(WorkGovpl::getPrcPaszport).setHeader("PrcPaszport");
        grid.addColumn(WorkGovpl::getPrcDowodOsob).setHeader("PrcDowodOsob");
        grid.addColumn(WorkGovpl::getZatDataPrzyj).setHeader("ZatDataPrzyj");
        grid.addColumn(WorkGovpl::getZatDataZmiany).setHeader("ZatDataZmiany");
        grid.addColumn(WorkGovpl::getStnNazwa).setHeader("StnNazwa");
        grid.addColumn(WorkGovpl::getKodZawodu).setHeader("KodZawodu");
        grid.addColumn(WorkGovpl::getRodzajUmowy).setHeader("RodzajUmowy");
        grid.addColumn(WorkGovpl::getSkKod).setHeader("SkKod");
        grid.addColumn(WorkGovpl::getAdrKodPocztowy).setHeader("AdrKodPocztowy");
        grid.addColumn(WorkGovpl::getWojewodztwo).setHeader("Wojewodztwo");
        grid.addColumn(WorkGovpl::getAdrGmina).setHeader("AdrGmina");
        grid.addColumn(WorkGovpl::getAdrUlica).setHeader("AdrUlica");
        grid.addColumn(WorkGovpl::getAdrPowiat).setHeader("AdrPowiat");
        grid.addColumn(WorkGovpl::getAdrMiejscowosc).setHeader("AdrMiejscowosc");
        grid.addColumn(WorkGovpl::getAdrNumberDomu).setHeader("AdrNumberDomu");
        grid.addColumn(WorkGovpl::getAdrNumerLokalu).setHeader("AdrNumerLokalu");
        grid.addColumn(WorkGovpl::getEtat).setHeader("Etat");
        grid.addColumn(WorkGovpl::getZatStawka).setHeader("ZatStawka");


        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        add(clearTable, grid);

        grid.addSelectionListener(selection -> {
            this.refreshDataToExcel();
        });

        excel = report.getStreamResource("obcokrajowcy.xls", workGovplRepo::findAll, PrintPreviewReport.Format.XLS);


        this.getDataFromDB();
    }

    private void getDataFromDB() {
        List<WorkGovpl> list = workGovplRepo.findAll();
        grid.setItems(list);
    }

    private void refreshDataToExcel() {
        listToExcel.clear();
        grid.getSelectedItems().stream().forEach( i -> {
            listToExcel.add(i);
        });

        excel = report.getStreamResource("obcokrajowcy.xls", workGovplRepo::findAll, PrintPreviewReport.Format.XLS);
        report.setItems(listToExcel);

        if (a != null){
            remove(a);
        }

        a = new Anchor(excel,"EXCEL");
        add(a);
    }
}

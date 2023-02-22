package pl.kskowronski.views.work_gov_pl;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Page;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import org.springframework.data.domain.Sort;
import org.vaadin.reports.PrintPreviewReport;
import pl.kskowronski.data.entity.egeria.ek.WorkGovpl;
import pl.kskowronski.data.service.egeria.ek.WorkGovplRepo;
import pl.kskowronski.views.main.MainView;
import com.vaadin.flow.component.button.Button;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private PrintPreviewReport report = new PrintPreviewReport<>(
    );




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

  //    WorkGovpl.class,"prcNumer","prcImie","prcImie2","prcNazwisko"
//            ,"prcPlec","prcDataUr","prcPesel","prcObywatelstwo","prcPaszport","prcDowodOsob","zatDataPrzyj","zatDataZmiany","stnNazwa"
//            ,"kodZawodu","rodzajUmowy","skKod","adrKodPocztowy","wojewodztwo","adrGmina","adrUlica"
//            ,"adrPowiat","adrMiejscowosc","adrNumberDomu","adrNumerLokalu","etat","zatStawka"
        Style headerStyle = new StyleBuilder(true).setFont(Font.ARIAL_MEDIUM).build();


        report.getReportBuilder()
//                .setMargins(20, 20, 40, 40)
//                .setAllowDetailSplit(true)
//                .setUseFullPageWidth(true)
                .setPageSizeAndOrientation(Page.Page_Legal_Landscape())
//                .setTitle("Call report")
//                .addAutoText("For internal use only", AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT, 1200, headerStyle)
//                .addAutoText(LocalDateTime.now().toString(), AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT, 1200, headerStyle)
//                .addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT, 1200, 100, headerStyle)
                .addColumn(ColumnBuilder.getNew().setColumnProperty("frmNazwa", String.class).setFixedWidth(true).setTitle("Firma").setStyle(headerStyle).build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("prcNumer", BigDecimal.class).setFixedWidth(true).setTitle("Numer").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("prcImie", String.class).setFixedWidth(true).setTitle("Imie").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("prcImie2", String.class).setFixedWidth(true).setTitle("Imie2").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("prcNazwisko", String.class).setFixedWidth(true).setTitle("Nazwisko").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("prcPlec", String.class).setFixedWidth(true).setTitle("Plec").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("prcDataUr", LocalDate.class).setFixedWidth(true).setTitle("DataUr").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("prcPesel", String.class).setFixedWidth(true).setTitle("Pesel").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("prcObywatelstwo", String.class).setFixedWidth(true).setTitle("Obywatelstwo").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("prcPaszport", String.class).setFixedWidth(true).setTitle("Paszport").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("prcDowodOsob", String.class).setFixedWidth(true).setTitle("DowodOsob").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("zatDataPrzyj", LocalDate.class).setFixedWidth(true).setTitle("DataPrzyj").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("zatDataZmiany", LocalDate.class).setFixedWidth(true).setTitle("DataZmiany").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("stnNazwa", String.class).setFixedWidth(true).setTitle("Stanowisko").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("kodZawodu", String.class).setFixedWidth(true).setTitle("KodZawodu").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("rodzajUmowy", String.class).setFixedWidth(true).setTitle("RodzajUmowy").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("skKod", String.class).setFixedWidth(true).setTitle("SkKod").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("adrKodPocztowy", String.class).setFixedWidth(true).setTitle("KodPocztowy").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("wojewodztwo", String.class).setFixedWidth(true).setTitle("Wojewodztwo").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("adrGmina", String.class).setFixedWidth(true).setTitle("Gmina").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("adrUlica", String.class).setFixedWidth(true).setTitle("Ulica").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("adrPowiat", String.class).setFixedWidth(true).setTitle("Powiat").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("adrMiejscowosc", String.class).setFixedWidth(true).setTitle("Miejscowosc").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("adrNumberDomu", String.class).setFixedWidth(true).setTitle("NumDomu").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("adrNumerLokalu", String.class).setFixedWidth(true).setTitle("NumLokalu").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("etat", String.class).setFixedWidth(true).setTitle("Etat").build())
                .addColumn(ColumnBuilder.getNew().setColumnProperty("zatStawka", String.class).setFixedWidth(true).setTitle("Stawka").build())
        ;

        excel = report.getStreamResource("obcokrajowcy_egeria.xls", workGovplRepo::findAll, PrintPreviewReport.Format.XLS);


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



        excel = report.getStreamResource("obcokrajowcy_egeria.xls", workGovplRepo::findAll, PrintPreviewReport.Format.XLS);

        report.setItems(listToExcel);


        if (a != null){
            remove(a);
        }

        a = new Anchor(excel,"EXCEL");
        add(a);
    }
}

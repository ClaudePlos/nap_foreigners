package pl.kskowronski.data.entity.egeria.ek;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "NAP_FOREIGNERS_PRACAGOV")
public class WorkGovpl {


    @Id
    @Column(name = "ID")
    private BigDecimal id;

    @Column(name = "PRC_NUMER")
    private BigDecimal prcNumer;

    @Column(name = "PRC_IMIE")
    private String prcImie;

    @Column(name = "PRC_IMIE2")
    private String prcImie2;

    @Column(name = "PRC_NAZWISKO")
    private String prcNazwisko;

    @Column(name = "PRC_PLEC")
    private String prcPlec;

    @Column(name = "PRC_DATA_UR")
    private LocalDate prcDataUr;

    @Column(name = "PRC_PESEL")
    private String prcPesel;

    @Column(name = "PRC_OBYWATELSTWO")
    private String prcObywatelstwo;

    @Column(name = "PRC_PASZPORT")
    private String prcPaszport;

    @Column(name = "PRC_DOWOD_OSOB")
    private String prcDowodOsob;

    @Column(name = "ZAT_DATA_PRZYJ")
    private LocalDate zatDataPrzyj;

    @Column(name = "ZAT_DATA_ZMIANY")
    private LocalDate zatDataZmiany;

    @Column(name = "STN_NAZWA")
    private String stnNazwa;

    @Column(name = "KOD_ZAWODU")
    private String kodZawodu;

    @Column(name = "RODZAJ_UMOWY")
    private String rodzajUmowy;

    @Column(name = "SK_KOD")
    private String skKod;

    @Column(name = "ADR_KOD_POCZTOWY")
    private String adrKodPocztowy;

    @Column(name = "WOJWODZTWO")
    private String wojewodztwo;

    @Column(name = "ADR_GMINA")
    private String adrGmina;

    @Column(name = "ADR_ULICA")
    private String adrUlica;

    @Column(name = "ADR_POWIAT")
    private String adrPowiat;

    @Column(name = "ADR_MIEJSCOWOSC")
    private String adrMiejscowosc;

    @Column(name = "ADR_NUMER_DOMU")
    private String adrNumberDomu;

    @Column(name = "ADR_NUMER_LOKALU")
    private String adrNumerLokalu;

    @Column(name = "ETAT")
    private String etat;

    @Column(name = "ZAT_STAWKA")
    private String zatStawka;

    @Column(name = "FRM_NAZWA")
    private String frmNazwa;

    public WorkGovpl() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getPrcNumer() {
        return prcNumer;
    }

    public void setPrcNumer(BigDecimal prcNumer) {
        this.prcNumer = prcNumer;
    }

    public String getPrcImie() {
        return prcImie;
    }

    public void setPrcImie(String prcImie) {
        this.prcImie = prcImie;
    }

    public String getPrcImie2() {
        return prcImie2;
    }

    public void setPrcImie2(String prcImie2) {
        this.prcImie2 = prcImie2;
    }

    public String getPrcNazwisko() {
        return prcNazwisko;
    }

    public void setPrcNazwisko(String prcNazwisko) {
        this.prcNazwisko = prcNazwisko;
    }

    public String getPrcPlec() {
        return prcPlec;
    }

    public void setPrcPlec(String prcPlec) {
        this.prcPlec = prcPlec;
    }

    public LocalDate getPrcDataUr() {
        return prcDataUr;
    }

    public void setPrcDataUr(LocalDate prcDataUr) {
        this.prcDataUr = prcDataUr;
    }

    public String getPrcPesel() {
        return prcPesel;
    }

    public void setPrcPesel(String prcPesel) {
        this.prcPesel = prcPesel;
    }

    public String getPrcObywatelstwo() {
        return prcObywatelstwo;
    }

    public void setPrcObywatelstwo(String prcObywatelstwo) {
        this.prcObywatelstwo = prcObywatelstwo;
    }

    public String getPrcPaszport() {
        return prcPaszport;
    }

    public void setPrcPaszport(String prcPaszport) {
        this.prcPaszport = prcPaszport;
    }

    public String getPrcDowodOsob() {
        return prcDowodOsob;
    }

    public void setPrcDowodOsob(String prcDowodOsob) {
        this.prcDowodOsob = prcDowodOsob;
    }

    public LocalDate getZatDataPrzyj() {
        return zatDataPrzyj;
    }

    public void setZatDataPrzyj(LocalDate zatDataPrzyj) {
        this.zatDataPrzyj = zatDataPrzyj;
    }

    public LocalDate getZatDataZmiany() {
        return zatDataZmiany;
    }

    public void setZatDataZmiany(LocalDate zatDataZmiany) {
        this.zatDataZmiany = zatDataZmiany;
    }

    public String getStnNazwa() {
        return stnNazwa;
    }

    public void setStnNazwa(String stnNazwa) {
        this.stnNazwa = stnNazwa;
    }

    public String getKodZawodu() {
        return kodZawodu;
    }

    public void setKodZawodu(String kodZawodu) {
        this.kodZawodu = kodZawodu;
    }

    public String getRodzajUmowy() {
        return rodzajUmowy;
    }

    public void setRodzajUmowy(String rodzajUmowy) {
        this.rodzajUmowy = rodzajUmowy;
    }

    public String getSkKod() {
        return skKod;
    }

    public void setSkKod(String skKod) {
        this.skKod = skKod;
    }

    public String getAdrKodPocztowy() {
        return adrKodPocztowy;
    }

    public void setAdrKodPocztowy(String adrKodPocztowy) {
        this.adrKodPocztowy = adrKodPocztowy;
    }

    public String getWojewodztwo() {
        return wojewodztwo;
    }

    public void setWojewodztwo(String wojewodztwo) {
        this.wojewodztwo = wojewodztwo;
    }

    public String getAdrGmina() {
        return adrGmina;
    }

    public void setAdrGmina(String adrGmina) {
        this.adrGmina = adrGmina;
    }

    public String getAdrUlica() {
        return adrUlica;
    }

    public void setAdrUlica(String adrUlica) {
        this.adrUlica = adrUlica;
    }

    public String getAdrPowiat() {
        return adrPowiat;
    }

    public void setAdrPowiat(String adrPowiat) {
        this.adrPowiat = adrPowiat;
    }

    public String getAdrMiejscowosc() {
        return adrMiejscowosc;
    }

    public void setAdrMiejscowosc(String adrMiejscowosc) {
        this.adrMiejscowosc = adrMiejscowosc;
    }

    public String getAdrNumberDomu() {
        return adrNumberDomu;
    }

    public void setAdrNumberDomu(String adrNumberDomu) {
        this.adrNumberDomu = adrNumberDomu;
    }

    public String getAdrNumerLokalu() {
        return adrNumerLokalu;
    }

    public void setAdrNumerLokalu(String adrNumerLokalu) {
        this.adrNumerLokalu = adrNumerLokalu;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getZatStawka() {
        return zatStawka;
    }

    public void setZatStawka(String zatStawka) {
        this.zatStawka = zatStawka;
    }

    public String getFrmNazwa() {
        return frmNazwa;
    }

    public void setFrmNazwa(String frmNazwa) {
        this.frmNazwa = frmNazwa;
    }
}

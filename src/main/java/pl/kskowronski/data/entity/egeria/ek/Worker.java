package pl.kskowronski.data.entity.egeria.ek;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "ek_pracownicy")
public class Worker {

    @Id
    @Column(name = "PRC_ID")
    private BigDecimal prcId;

    @Column(name = "PRC_NUMER")
    private BigDecimal prcNumer;

    @Column(name = "PRC_DOWOD_OSOB")
    private String prcDowodOsob;

    @Column(name = "PRC_PESEL")
    private String prcPesel;

    @Column(name = "PRC_IMIE")
    private String prcImie;

    @Column(name = "PRC_NAZWISKO")
    private String prcNazwisko;

    @Column(name = "PRC_DG_KOD_EK")
    private String prcDgKodEk;

    @Column(name = "PRC_PLEC")
    private String prcPlec;

    @Column(name = "PRC_OBYWATELSTWO")
    private String prcObywatelstwo;

    public Worker() {
    }

    public BigDecimal getPrcId() {
        return prcId;
    }

    public void setPrcId(BigDecimal prcId) {
        this.prcId = prcId;
    }

    public BigDecimal getPrcNumer() {
        return prcNumer;
    }

    public void setPrcNumer(BigDecimal prcNumer) {
        this.prcNumer = prcNumer;
    }

    public String getPrcDowodOsob() {
        return prcDowodOsob;
    }

    public void setPrcDowodOsob(String prcDowodOsob) {
        this.prcDowodOsob = prcDowodOsob;
    }

    public String getPrcPesel() {
        return prcPesel;
    }

    public void setPrcPesel(String prcPesel) {
        this.prcPesel = prcPesel;
    }

    public String getPrcImie() {
        return prcImie;
    }

    public void setPrcImie(String prcImie) {
        this.prcImie = prcImie;
    }

    public String getPrcNazwisko() {
        return prcNazwisko;
    }

    public void setPrcNazwisko(String prcNazwisko) {
        this.prcNazwisko = prcNazwisko;
    }

    public String getPrcDgKodEk() {
        return prcDgKodEk;
    }

    public void setPrcDgKodEk(String prcDgKodEk) {
        this.prcDgKodEk = prcDgKodEk;
    }

    public String getPrcPlec() {
        return prcPlec;
    }

    public void setPrcPlec(String prcPlec) {
        this.prcPlec = prcPlec;
    }

    public String getPrcObywatelstwo() {
        return prcObywatelstwo;
    }

    public void setPrcObywatelstwo(String prcObywatelstwo) {
        this.prcObywatelstwo = prcObywatelstwo;
    }
}

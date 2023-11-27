package pl.kskowronski.data.entity.suncode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "SUN_DOK_OBCOKRAJOWIEC")
public class SunDok {

    @Id
    @Column(name = "ID", nullable = false)
    private BigDecimal id;

    @Column(name="INNE")
    private String inne;

    @Column(name="PESEL")
    private String pesel;

    @Column(name="PIT2")
    private String pit2;

    @Column(name="ZASWIADCZENIA")
    private String zaswiadczenia;

    @Column(name="SWIADECTWO_PRACY")
    private String zwiadzectwoPracy;

    @Column(name = "PASZPORT")
    private String paszport;

    @Column(name = "NIEPELNOSPRAWNOSC")
    private String niepelnosprawnosc;

    @Column(name = "LEGALIZUJACY_POBYT")
    private String legalizujacyPobyt;

    @Column(name = "LEGALIZUJACY_PRACE")
    private String legalizujacyPrace;

    @Column(name = "KWEST_OSOBOWY")
    private String kwestOsobowy;

    @Column(name = "ZDOLNOSC_DO_PRACY")
    private String zdolnoscDoPracy;

    @Column(name = "SUN_ZAT_ID")
    private String sunZatId;

    @Column(name = "STATUS")
    private String status;

    public SunDok() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getInne() {
        return inne;
    }

    public void setInne(String inne) {
        this.inne = inne;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getPit2() {
        return pit2;
    }

    public void setPit2(String pit2) {
        this.pit2 = pit2;
    }

    public String getZaswiadczenia() {
        return zaswiadczenia;
    }

    public void setZaswiadczenia(String zaswiadczenia) {
        this.zaswiadczenia = zaswiadczenia;
    }

    public String getZwiadzectwoPracy() {
        return zwiadzectwoPracy;
    }

    public void setZwiadzectwoPracy(String zwiadzectwoPracy) {
        this.zwiadzectwoPracy = zwiadzectwoPracy;
    }

    public String getPaszport() {
        return paszport;
    }

    public void setPaszport(String paszport) {
        this.paszport = paszport;
    }

    public String getNiepelnosprawnosc() {
        return niepelnosprawnosc;
    }

    public void setNiepelnosprawnosc(String niepelnosprawnosc) {
        this.niepelnosprawnosc = niepelnosprawnosc;
    }

    public String getLegalizujacyPobyt() {
        return legalizujacyPobyt;
    }

    public void setLegalizujacyPobyt(String legalizujacyPobyt) {
        this.legalizujacyPobyt = legalizujacyPobyt;
    }

    public String getLegalizujacyPrace() {
        return legalizujacyPrace;
    }

    public void setLegalizujacyPrace(String legalizujacyPrace) {
        this.legalizujacyPrace = legalizujacyPrace;
    }

    public String getKwestOsobowy() {
        return kwestOsobowy;
    }

    public void setKwestOsobowy(String kwestOsobowy) {
        this.kwestOsobowy = kwestOsobowy;
    }

    public String getZdolnoscDoPracy() {
        return zdolnoscDoPracy;
    }

    public void setZdolnoscDoPracy(String zdolnoscDoPracy) {
        this.zdolnoscDoPracy = zdolnoscDoPracy;
    }

    public String getSunZatId() {
        return sunZatId;
    }

    public void setSunZatId(String sunZatId) {
        this.sunZatId = sunZatId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

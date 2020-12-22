package pl.kskowronski.data.entity.egeria.ckk;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "ckk_adresy")
public class Address {

    @Id
    @Column(name = "adr_Id", nullable = false)
    private BigDecimal id;

    @Column(name="adr_typ")
    private String typ;

    @Column(name="adr_woj_id")
    private BigDecimal wojId;

    @Column(name="adr_lp")
    private BigDecimal lp;

    @Column(name="adr_miejscowosc")
    private String miejscowosc;

    @Column(name="adr_typ_ulicy")
    private String typUlicy;

    @Column(name="adr_zatwierdzony")
    private String zatwierdzony;

    @Column(name="adr_f_aktualne")
    private String aktualne;

    @Column(name="adr_gmina")
    private String gmina;

    @Column(name="adr_kod_pocztowy")
    private String kodPocztowy;

    @Column(name="adr_ulica")
    private String ulica;

    @Column(name="adr_numer_domu")
    private String numerDomu;

    @Column(name="adr_numer_lokalu")
    private String numerLokalu;

    @Column(name="adr_poczta")
    private String poczta;

    @Column(name="adr_powiat")
    private String powiat;

    @Column(name="adr_prc_id")
    private BigDecimal prcId;

    @Column(name="adr_kl_kod")
    private BigDecimal klKod;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public BigDecimal getWojId() {
        return wojId;
    }

    public void setWojId(BigDecimal wojId) {
        this.wojId = wojId;
    }

    public BigDecimal getLp() {
        return lp;
    }

    public void setLp(BigDecimal lp) {
        this.lp = lp;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getTypUlicy() {
        return typUlicy;
    }

    public void setTypUlicy(String typUlicy) {
        this.typUlicy = typUlicy;
    }

    public String getZatwierdzony() {
        return zatwierdzony;
    }

    public void setZatwierdzony(String zatwierdzony) {
        this.zatwierdzony = zatwierdzony;
    }

    public String getAktualne() {
        return aktualne;
    }

    public void setAktualne(String aktualne) {
        this.aktualne = aktualne;
    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getNumerDomu() {
        return numerDomu;
    }

    public void setNumerDomu(String numerDomu) {
        this.numerDomu = numerDomu;
    }

    public String getNumerLokalu() {
        return numerLokalu;
    }

    public void setNumerLokalu(String numerLokalu) {
        this.numerLokalu = numerLokalu;
    }

    public String getPoczta() {
        return poczta;
    }

    public void setPoczta(String poczta) {
        this.poczta = poczta;
    }

    public String getPowiat() {
        return powiat;
    }

    public void setPowiat(String powiat) {
        this.powiat = powiat;
    }

    public BigDecimal getPrcId() {
        return prcId;
    }

    public void setPrcId(BigDecimal prcId) {
        this.prcId = prcId;
    }

    public BigDecimal getKlKod() {
        return klKod;
    }

    public void setKlKod(BigDecimal klKod) {
        this.klKod = klKod;
    }
}

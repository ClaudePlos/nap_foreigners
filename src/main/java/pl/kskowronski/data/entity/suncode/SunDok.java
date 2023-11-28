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

    @Column(name = "SUN_ZAT_ID")
    private String sunZatId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PRC_ID")
    private BigDecimal prcId;

    @Column(name = "SCIEZKA")
    private String sciezka;

    @Column(name = "RODZAJ_DOK")
    private String rodzajDok;

    public SunDok() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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

    public BigDecimal getPrcId() {
        return prcId;
    }

    public void setPrcId(BigDecimal prcId) {
        this.prcId = prcId;
    }

    public String getSciezka() {
        return sciezka;
    }

    public void setSciezka(String sciezka) {
        this.sciezka = sciezka;
    }

    public String getRodzajDok() {
        return rodzajDok;
    }

    public void setRodzajDok(String rodzajDok) {
        this.rodzajDok = rodzajDok;
    }
}

package pl.kskowronski.data.entity.inap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "nzap_klucze_zapotrzebowan")
public class RequirementKey {

    @Id
    @Column(name = "ID")
    private BigDecimal id;

    @Column(name = "TYP")
    private String type;

    @Column(name = "DATA")
    private Date date;

    @Column(name = "TEKST")
    private String tekst;

    @Column(name = "LICZBA")
    private BigDecimal liczba;

    @Column(name = "ZAP_ID")
    private BigDecimal zapId;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public BigDecimal getLiczba() {
        return liczba;
    }

    public void setLiczba(BigDecimal liczba) {
        this.liczba = liczba;
    }

    public BigDecimal getZapId() {
        return zapId;
    }

    public void setZapId(BigDecimal zapId) {
        this.zapId = zapId;
    }
}

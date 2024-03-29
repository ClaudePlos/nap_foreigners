package pl.kskowronski.data.entity.inap;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "nzap_zapotrzebowania")
public class Requirement {

    @Id
    @Column(name = "ID")
    private BigDecimal id;

    @Column(name = "PROCES_ID")
    private BigDecimal procesId;

    @Column(name = "TYTUL")
    private String title;

    @Column(name = "FRM_ID")
    private BigDecimal frmId;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getProcesId() {
        return procesId;
    }

    public void setProcesId(BigDecimal procesId) {
        this.procesId = procesId;
    }

    public BigDecimal getFrmId() {
        return frmId;
    }

    public void setFrmId(BigDecimal frmId) {
        this.frmId = frmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

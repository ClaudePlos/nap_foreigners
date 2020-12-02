package pl.kskowronski.data.entity.inap;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "ndok_opisy")
public class DocDesc {

    @Id
    @Column(name="id")
    private BigDecimal id;

    @Column(name="dok_id")
    private BigDecimal dokId;

    @Column(name="prc_id")
    private BigDecimal prcId;

    public DocDesc() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getDokId() {
        return dokId;
    }

    public void setDokId(BigDecimal dokId) {
        this.dokId = dokId;
    }

    public BigDecimal getPrcId() {
        return prcId;
    }

    public void setPrcId(BigDecimal prcId) {
        this.prcId = prcId;
    }
}

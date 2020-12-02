package pl.kskowronski.data.entity.inap;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "NAP_FOREIGNERS_LOG")
public class NapForeignerLog {

    public static String STATUS_ACCEPT = "ZAAKCEPTOWANE" ;
    public static String STATUS_NO_ACCEPT = "ODRZUCONE" ;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;

    @Column(name = "PRC_ID")
    private BigDecimal prcId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "WHO_DECIDED")
    private String whoDecided;

    @Column(name = "WHEN_DECIDED")
    @Temporal(TemporalType.DATE)
    private Date whenDecided;

    public NapForeignerLog() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getPrcId() {
        return prcId;
    }

    public void setPrcId(BigDecimal prcId) {
        this.prcId = prcId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWhoDecided() {
        return whoDecided;
    }

    public void setWhoDecided(String whoDecided) {
        this.whoDecided = whoDecided;
    }

    public Date getWhenDecided() {
        return whenDecided;
    }

    public void setWhenDecided(Date whenDecided) {
        this.whenDecided = whenDecided;
    }
}

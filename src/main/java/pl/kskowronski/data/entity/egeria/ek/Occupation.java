package pl.kskowronski.data.entity.egeria.ek;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "zpt_stanowiska")
public class Occupation {

    @Id
    @Column(name = "STN_ID")
    private BigDecimal stnId;

    @Column(name = "STN_NAZWA")
    private String stnNazwa;

    public BigDecimal getStnId() {
        return stnId;
    }

    public void setStnId(BigDecimal stnId) {
        this.stnId = stnId;
    }

    public String getStnNazwa() {
        return stnNazwa;
    }

    public void setStnNazwa(String stnNazwa) {
        this.stnNazwa = stnNazwa;
    }
}

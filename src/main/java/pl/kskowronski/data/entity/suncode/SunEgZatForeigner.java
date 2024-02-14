package pl.kskowronski.data.entity.suncode;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "sun_eg_zat_obcokrajowiec")
public class SunEgZatForeigner {

    @Id
    @Column(name = "SUN_ZAT_ID")
    private BigDecimal sunZatId;

    @Column(name = "ZAT_ID")
    private BigDecimal zatId;

    @Column(name = "ZAT_FRM_ID")
    private BigDecimal zatFrmId;

    @Column(name = "ZAT_STAWKA")
    private BigDecimal zatStawka;

    @Column(name = "ZAT_TEMAT")
    private String zatTopic;

    @Column(name = "ZAT_TYP_STAWKI")
    private String zatTYpeOfRate;

    @Column(name = "ZAT_DATA_ZMIANY")
    private Date zatDataOd;

    @Column(name = "ZAT_OKRES_DO")
    private Date zatDataDo;

    @Column(name = "ZAT_ANEKS")
    private String zatAneks;

    @Column(name = "ZAT_F_RODZAJ")
    private String zatFRodzaj;

    @Column(name = "ZAT_TYP_UMOWY")
    private String zatTypUmowy;

    public SunEgZatForeigner() {
    }

    public BigDecimal getZatId() {
        return zatId;
    }

    public void setZatId(BigDecimal zatId) {
        this.zatId = zatId;
    }

    public BigDecimal getSunZatId() {
        return sunZatId;
    }

    public void setSunZatId(BigDecimal sunZatId) {
        this.sunZatId = sunZatId;
    }

    public BigDecimal getZatFrmId() {
        return zatFrmId;
    }

    public void setZatFrmId(BigDecimal zatFrmId) {
        this.zatFrmId = zatFrmId;
    }

    public BigDecimal getZatStawka() {
        return zatStawka;
    }

    public void setZatStawka(BigDecimal zatStawka) {
        this.zatStawka = zatStawka;
    }

    public String getZatTopic() {
        return zatTopic;
    }

    public void setZatTopic(String zatTopic) {
        this.zatTopic = zatTopic;
    }

    public String getZatTYpeOfRate() {
        return zatTYpeOfRate;
    }

    public void setZatTYpeOfRate(String zatTYpeOfRate) {
        this.zatTYpeOfRate = zatTYpeOfRate;
    }

    public Date getZatDataOd() {
        return zatDataOd;
    }

    public void setZatDataOd(Date zatDataOd) {
        this.zatDataOd = zatDataOd;
    }

    public Date getZatDataDo() {
        return zatDataDo;
    }

    public void setZatDataDo(Date zatDataDo) {
        this.zatDataDo = zatDataDo;
    }

    public String getZatAneks() {
        return zatAneks;
    }

    public void setZatAneks(String zatAneks) {
        this.zatAneks = zatAneks;
    }

    public String getZatFRodzaj() {
        return zatFRodzaj;
    }

    public void setZatFRodzaj(String zatFRodzaj) {
        this.zatFRodzaj = zatFRodzaj;
    }

    public String getZatTypUmowy() {
        return zatTypUmowy;
    }

    public void setZatTypUmowy(String zatTypUmowy) {
        this.zatTypUmowy = zatTypUmowy;
    }
}

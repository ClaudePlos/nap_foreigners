package pl.kskowronski.data.entity.inap;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ndok_dokumenty")
public class Document {

    public static String TYPE_DOCUMENT = "DOKUMENT" ;

    @Id
    @Column(name = "ID")
    private BigDecimal id;

    @Column(name = "NAZWA")
    private String nazwa;

    @Column(name = "OPIS")
    private String opis;

    @Column(name = "TYP")
    private String type;

    @Column(name = "UTWORZYL")
    private String documentMadeBy;

    @Column(name = "USUNIETY")
    private String deleted;

    @Column(name = "DATA_UTWORZENIA")
    @Temporal(TemporalType.DATE)
    private Date documentDateMade;

    @Column(name = "VER")
    private String ver;

    @Column(name="frm_id")
    private BigDecimal frmId ;

    public Document() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public BigDecimal getFrmId() {
        return frmId;
    }

    public void setFrmId(BigDecimal frmId) {
        this.frmId = frmId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocumentMadeBy() {
        return documentMadeBy;
    }

    public void setDocumentMadeBy(String documentMadeBy) {
        this.documentMadeBy = documentMadeBy;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Date getDocumentDateMade() {
        return documentDateMade;
    }

    public void setDocumentDateMade(Date documentDateMade) {
        this.documentDateMade = documentDateMade;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
}

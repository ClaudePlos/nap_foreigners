package pl.kskowronski.data.entity.inap;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DocumentDTO {

    private BigDecimal id;
    private String nazwa;
    private String opis;
    private BigDecimal frmId;
    private String frmName;
    private String path;
    private String platform;
    private LocalDate audytDU;

    public DocumentDTO() {
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

    public String getFrmName() {
        return frmName;
    }

    public void setFrmName(String frmName) {
        this.frmName = frmName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public LocalDate getAudytDU() {
        return audytDU;
    }

    public void setAudytDU(LocalDate audytDU) {
        this.audytDU = audytDU;
    }
}

package pl.kskowronski.data.entity.egeria.ek;

import java.math.BigDecimal;

public class WorkStatisticDTO {

    private String frmName;
    private String typeOfAgreement;
    private BigDecimal workersSum;
    private BigDecimal frmId;

    public String getFrmName() {
        return frmName;
    }

    public void setFrmName(String frmName) {
        this.frmName = frmName;
    }

    public String getTypeOfAgreement() {
        return typeOfAgreement;
    }

    public void setTypeOfAgreement(String typeOfAgreement) {
        this.typeOfAgreement = typeOfAgreement;
    }

    public BigDecimal getWorkersSum() {
        return workersSum;
    }

    public void setWorkersSum(BigDecimal workersSum) {
        this.workersSum = workersSum;
    }

    public BigDecimal getFrmId() {
        return frmId;
    }

    public void setFrmId(BigDecimal frmId) {
        this.frmId = frmId;
    }
}

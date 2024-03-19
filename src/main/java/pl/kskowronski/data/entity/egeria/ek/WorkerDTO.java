package pl.kskowronski.data.entity.egeria.ek;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class WorkerDTO {

    private BigDecimal prcId;
    private BigDecimal prcNumer;
    private String prcPesel;
    private String prcDowodOsob;
    private String prcImie;
    private String prcNazwisko;
    private String prcDgKodEk;
    private String prcPlec;
    private String prcObywatelstwo;
    private BigDecimal procesId;
    private String sk;
    private String runProcess;
    private Date runDate;
    private String status;
    private String frmName;
    private String typeOfAgreement;
    private String platform;
    private LocalDate dataOd;

    public WorkerDTO() {
    }

    public BigDecimal getPrcId() {
        return prcId;
    }

    public void setPrcId(BigDecimal prcId) {
        this.prcId = prcId;
    }

    public BigDecimal getPrcNumer() {
        return prcNumer;
    }

    public void setPrcNumer(BigDecimal prcNumer) {
        this.prcNumer = prcNumer;
    }

    public String getPrcPesel() {
        return prcPesel;
    }

    public void setPrcPesel(String prcPesel) {
        this.prcPesel = prcPesel;
    }

    public String getPrcDowodOsob() {
        return prcDowodOsob;
    }

    public void setPrcDowodOsob(String prcDowodOsob) {
        this.prcDowodOsob = prcDowodOsob;
    }

    public String getPrcImie() {
        return prcImie;
    }

    public void setPrcImie(String prcImie) {
        this.prcImie = prcImie;
    }

    public String getPrcNazwisko() {
        return prcNazwisko;
    }

    public void setPrcNazwisko(String prcNazwisko) {
        this.prcNazwisko = prcNazwisko;
    }

    public String getPrcDgKodEk() {
        return prcDgKodEk;
    }

    public void setPrcDgKodEk(String prcDgKodEk) {
        this.prcDgKodEk = prcDgKodEk;
    }

    public String getPrcPlec() {
        return prcPlec;
    }

    public void setPrcPlec(String prcPlec) {
        this.prcPlec = prcPlec;
    }

    public String getPrcObywatelstwo() {
        return prcObywatelstwo;
    }

    public void setPrcObywatelstwo(String prcObywatelstwo) {
        this.prcObywatelstwo = prcObywatelstwo;
    }

    public BigDecimal getProcesId() {
        return procesId;
    }

    public void setProcesId(BigDecimal procesId) {
        this.procesId = procesId;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getRunProcess() {
        return runProcess;
    }

    public void setRunProcess(String runProcess) {
        this.runProcess = runProcess;
    }

    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public LocalDate getDataOd() {
        return dataOd;
    }

    public void setDataOd(LocalDate dataOd) {
        this.dataOd = dataOd;
    }
}

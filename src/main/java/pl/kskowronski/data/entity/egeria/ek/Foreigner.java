package pl.kskowronski.data.entity.egeria.ek;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "napv_foreigners_to_accept")
public class Foreigner {

    @Id
    @Column(name = "PRC_ID")
    private BigDecimal prcId;

    @Column(name = "PROCES_ID")
    private BigDecimal procesId;

    @Column(name = "PRC_NUMER")
    private BigDecimal prcNumer;

    @Column(name = "PRC_IMIE")
    private String prcImie;

    @Column(name = "PRC_NAZWISKO")
    private String prcNazwisko;

    @Column(name = "PRC_OBYWATELSTWO")
    private String prcObywatelstwo;

    @Column(name = "SK")
    private String SK;

    @Column(name = "RUN_PROCESS")
    private String runProcess;

    @Column(name = "UTWORZONO")
    private Date runDate;

    @Column(name = "STATUS")
    private String status;

    public Foreigner() {
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

    public BigDecimal getProcesId() {
        return procesId;
    }

    public void setProcesId(BigDecimal procesId) {
        this.procesId = procesId;
    }

    public String getPrcObywatelstwo() {
        return prcObywatelstwo;
    }

    public void setPrcObywatelstwo(String prcObywatelstwo) {
        this.prcObywatelstwo = prcObywatelstwo;
    }

    public String getSK() {
        return SK;
    }

    public void setSK(String SK) {
        this.SK = SK;
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
}

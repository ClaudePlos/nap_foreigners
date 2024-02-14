package pl.kskowronski.data.entity.inap;

import java.math.BigDecimal;
import java.util.Date;

public class NapForeignerLogDTO  {

    private BigDecimal id;
    private BigDecimal prcId;
    private String status;
    private String description;
    private String whoDecided;
    private Date whenDecided;
    private BigDecimal prcNumber;
    private String prcName;
    private String prcSurname;
    private BigDecimal processId;
    private String refresh;
    private String skForApplication;
    private String whoRunInInap;
    private String platform;


    public NapForeignerLogDTO() {
        // Do nothing because of X and Y.
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

    public String getPrcName() {
        return prcName;
    }

    public void setPrcName(String prcName) {
        this.prcName = prcName;
    }

    public String getPrcSurname() {
        return prcSurname;
    }

    public void setPrcSurname(String prcSurname) {
        this.prcSurname = prcSurname;
    }

    public BigDecimal getPrcNumber() {
        return prcNumber;
    }

    public void setPrcNumber(BigDecimal prcNumber) {
        this.prcNumber = prcNumber;
    }

    public BigDecimal getProcessId() {
        return processId;
    }

    public void setProcessId(BigDecimal processId) {
        this.processId = processId;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getSkForApplication() {
        return skForApplication;
    }

    public void setSkForApplication(String skForApplication) {
        this.skForApplication = skForApplication;
    }

    public String getWhoRunInInap() {
        return whoRunInInap;
    }

    public void setWhoRunInInap(String whoRunInInap) {
        this.whoRunInInap = whoRunInInap;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}

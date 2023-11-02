package pl.kskowronski.data.entity.egeria.css;

import java.math.BigDecimal;

public class CostCenterDTO {

    private BigDecimal skId;
    private String skKod;
    private String SkDesc;
    private String city;
    private String street;
    private String businessType;
    private String contractType;

    public BigDecimal getSkId() {
        return skId;
    }

    public void setSkId(BigDecimal skId) {
        this.skId = skId;
    }

    public String getSkKod() {
        return skKod;
    }

    public void setSkKod(String skKod) {
        this.skKod = skKod;
    }

    public String getSkDesc() {
        return SkDesc;
    }

    public void setSkDesc(String skDesc) {
        SkDesc = skDesc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
}

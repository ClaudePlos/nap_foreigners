package pl.kskowronski.data.entity.egeria.css;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

//select wsl_wartosc, wsl_opis, wsl_alias from css_wartosci_slownikow where wsl_sl_nazwa = 'NAP_PRZEDMIOTY_UMOW_ZLECEN'
@Entity
@Table(name = "css_wartosci_slownikow")
public class Dictionary {

    @Id
    @Column(name = "wsl_wartosc", nullable = false)
    private String value;

    @Column(name = "wsl_opis")
    private String description;

    @Column(name = "wsl_alias")
    private String alias;

    @Column(name = "wsl_sl_nazwa")
    private String dictionaryName;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }
}

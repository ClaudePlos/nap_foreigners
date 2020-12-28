package pl.kskowronski.data.entity.inap;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "nzad_instancje_procesow")
public class ProcessInstance {

    @Id
    @Column(name = "ID")
    private BigDecimal id;

    @Column(name = "URUCHOMIL")
    private String runProcess;

    public ProcessInstance() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getRunProcess() {
        return runProcess;
    }

    public void setRunProcess(String runProcess) {
        this.runProcess = runProcess;
    }
}

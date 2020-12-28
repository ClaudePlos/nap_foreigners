package pl.kskowronski.data.service.inap;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kskowronski.data.entity.inap.ProcessInstance;

import java.math.BigDecimal;

public interface ProcessInstanceRepo extends JpaRepository<ProcessInstance, BigDecimal> {

}

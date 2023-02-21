package pl.kskowronski.data.service.egeria.ek;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kskowronski.data.entity.egeria.ek.WorkGovpl;

import java.math.BigDecimal;

public interface WorkGovplRepo extends JpaRepository<WorkGovpl, BigDecimal> {

}

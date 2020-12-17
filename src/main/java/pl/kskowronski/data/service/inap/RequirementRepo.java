package pl.kskowronski.data.service.inap;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kskowronski.data.entity.inap.Requirement;

import java.math.BigDecimal;
import java.util.Optional;

public interface RequirementRepo extends JpaRepository<Requirement, BigDecimal> {

    Optional<Requirement> getByProcesId(BigDecimal processId);

}



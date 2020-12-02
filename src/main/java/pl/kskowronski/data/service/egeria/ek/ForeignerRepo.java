package pl.kskowronski.data.service.egeria.ek;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kskowronski.data.entity.egeria.ek.Foreigner;

import java.math.BigDecimal;

public interface ForeignerRepo extends JpaRepository<Foreigner, BigDecimal> {
}

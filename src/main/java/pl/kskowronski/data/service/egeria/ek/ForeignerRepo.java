package pl.kskowronski.data.service.egeria.ek;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kskowronski.data.entity.egeria.ek.Foreigner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ForeignerRepo extends JpaRepository<Foreigner, BigDecimal> {

}

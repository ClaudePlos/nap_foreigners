package pl.kskowronski.data.service.egeria.ek;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kskowronski.data.entity.egeria.ek.Occupation;

import java.math.BigDecimal;

public interface OccupationRepo extends JpaRepository<Occupation, BigDecimal>  {
}

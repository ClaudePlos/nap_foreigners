package pl.kskowronski.data.service.egeria.css;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kskowronski.data.entity.egeria.css.CostCenterGeolocation;

public interface CostCenterGeolocationRepo extends JpaRepository<CostCenterGeolocation, String> {
}

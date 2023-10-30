package pl.kskowronski.data.service.egeria.css;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kskowronski.data.entity.egeria.css.CostCenterGeolocation;

import java.util.List;
import java.util.Optional;

public interface CostCenterGeolocationRepo extends JpaRepository<CostCenterGeolocation, String> {

    @Query("select g from CostCenterGeolocation g where g.businessType = :businessType")
    List<CostCenterGeolocation> getAllForBusinessType(@Param("businessType") String businessType);

}

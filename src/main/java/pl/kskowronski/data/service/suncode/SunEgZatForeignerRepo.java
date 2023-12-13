package pl.kskowronski.data.service.suncode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kskowronski.data.entity.suncode.SunDok;
import pl.kskowronski.data.entity.suncode.SunEgZatForeigner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SunEgZatForeignerRepo extends JpaRepository<SunEgZatForeigner, BigDecimal> {

    @Query("select s from SunEgZatForeigner s where s.sunZatId = :sunZatId")
    Optional<SunEgZatForeigner> findBySunZatId(@Param("sunZatId") BigDecimal sunZatId);
}

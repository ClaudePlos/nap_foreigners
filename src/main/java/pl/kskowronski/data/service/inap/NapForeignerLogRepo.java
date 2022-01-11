package pl.kskowronski.data.service.inap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kskowronski.data.entity.inap.NapForeignerLog;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NapForeignerLogRepo extends JpaRepository<NapForeignerLog, BigDecimal> {

    @Query("select f from NapForeignerLog f where  f.whenDecided >= :dateFrom and f.whenDecided <= :dateTo order by f.whenDecided desc")
    Optional<List<NapForeignerLog>> findAllForPeriod(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

    @Query("select f from NapForeignerLog f where  f.whenDecided >= :dateFrom and f.whenDecided <= :dateTo " +
            "and f.status in ('ZAAKCEPTOWANE','ODRZUCONE')" +
            "order by f.whenDecided desc")
    Optional<List<NapForeignerLog>> findAllAcceptAndDelForPeriod(@Param("dateFrom") Date dateFrom
            , @Param("dateTo") Date dateTo
           // , Pageable pageable
    );

    @Query("select f from NapForeignerLog f where  f.whenDecided >= :dateFrom and f.whenDecided <= :dateTo " +
            "and f.status in ('ZAWIESZONE')" +
            "order by f.whenDecided desc")
    Optional<List<NapForeignerLog>> findAllSuspendedForPeriod(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

}

package pl.kskowronski.data.service.suncode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kskowronski.data.entity.inap.Document;
import pl.kskowronski.data.entity.suncode.SunDok;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SunDokRepo extends JpaRepository<SunDok, BigDecimal> {

    @Query("select d from SunDok d where d.prcId = :prcId order by d.audytDU desc")
    Optional<List<SunDok>> getDocumentForPrc(@Param("prcId") BigDecimal prcId);

}

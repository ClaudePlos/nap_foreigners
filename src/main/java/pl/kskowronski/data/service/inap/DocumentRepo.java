package pl.kskowronski.data.service.inap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kskowronski.data.entity.inap.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DocumentRepo extends JpaRepository<Document, BigDecimal> {

    @Query("select d from Document d where exists (select 1 from DocDesc dd where dd.prcId = :prcId and dd.dokId = d.id) " +
            "and d.deleted = 'N'")
    Optional<List<Document>> getDocumentForPrc(@Param("prcId") BigDecimal prcId);

}

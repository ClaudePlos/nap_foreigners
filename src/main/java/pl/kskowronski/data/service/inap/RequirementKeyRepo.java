package pl.kskowronski.data.service.inap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kskowronski.data.entity.inap.RequirementKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RequirementKeyRepo extends JpaRepository<RequirementKey, BigDecimal> {

    @Query("select rk from RequirementKey rk where rk.zapId = (select r.id from Requirement r where r.procesId = :processId) " +
            " and rk.type in ('DATA_OD','DATA_DO','TYP_STAWKI','PRZEDMIOT_UMOWY_OPIS','STAWKA','STN_PRACY') order by rk.type")
    Optional<List<RequirementKey>> getRequirementForProcess(@Param("processId") BigDecimal processId);

    @Query("select rk.date from RequirementKey rk where rk.zapId = (select r.id from Requirement r where r.procesId = :processId) " +
            " and rk.type in ('DATA_OD')")
    Date getDateFrom(@Param("processId") BigDecimal processId);

}

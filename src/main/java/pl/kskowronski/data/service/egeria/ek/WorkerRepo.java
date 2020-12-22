package pl.kskowronski.data.service.egeria.ek;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kskowronski.data.entity.egeria.ek.Foreigner;
import pl.kskowronski.data.entity.egeria.ek.Worker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WorkerRepo extends JpaRepository<Worker, BigDecimal> {

    @Query("select w from Worker w where w.prcDgKodEk = :prcDgKodEk and w.prcObywatelstwo != :nationality")
    Optional<List<Worker>> findByPrcDgKodEkAAndPrcObywatelstwoNotLike(@Param("prcDgKodEk") String prcDgKodEk, @Param("nationality") String nationality );

    @Query("select w from Worker w " +
            "where w.prcId in (select f.prcId from Foreigner f where f.prcObywatelstwo != 'Polskie' " +
                "and 0 = (select count(1) from NapForeignerLog n where n.prcId = f.prcId and f.procesId = n.processId))")
    Optional<List<Worker>> listWorkersToAccept();

    @Query("select w from Worker w " +
            "where w.prcId in (select f.prcId from Foreigner f where f.prcObywatelstwo = 'Polskie' " +
            "and 0 = (select count(1) from NapForeignerLog n where n.prcId = f.prcId and f.procesId = n.processId))")
    Optional<List<Worker>> listWorkersToAcceptOnlyPolishNationality();

    @Query("select f from Foreigner f where f.prcId = :prcId")
    Optional<Foreigner> getForeignerForPrcId(@Param("prcId") BigDecimal prcId);

    @Query("select w.prcDataUr from Worker w where w.prcId = :prcId")
    Date getByPrcDataUrForPrcId(@Param("prcId") BigDecimal prcId);

}

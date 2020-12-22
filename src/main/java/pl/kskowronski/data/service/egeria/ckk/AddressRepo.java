package pl.kskowronski.data.service.egeria.ckk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kskowronski.data.entity.egeria.ckk.Address;

import java.math.BigDecimal;
import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, BigDecimal> {

    @Query("select a from Address a where a.klKod = :klKod and a.aktualne = 'T' and a.zatwierdzony = 'T' and a.typ = 'S'")
    Optional<Address> getMainAddressForClient(@Param("klKod") BigDecimal klKod);

}

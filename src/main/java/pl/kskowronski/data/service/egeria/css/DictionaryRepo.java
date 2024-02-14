package pl.kskowronski.data.service.egeria.css;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kskowronski.data.entity.egeria.css.Dictionary;

import java.util.Optional;


public interface DictionaryRepo extends JpaRepository<Dictionary, String>  {

    @Query("select d.description from Dictionary d where d.value = :value and d.dictionaryName = 'NAP_PRZEDMIOTY_UMOW_ZLECEN'")
    Optional<String> getSubjectOfTheContract(@Param("value") String value);

    @Query("select d.description from Dictionary d where d.value = :value and d.dictionaryName = 'RODZAJ_UMOWY_Z12'")
    Optional<String> getTypeOfContract(@Param("value") String value);

}

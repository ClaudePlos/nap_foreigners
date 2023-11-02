package pl.kskowronski.data.service.egeria.css;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kskowronski.data.entity.egeria.css.CostCenterDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CostCentersService {

    @PersistenceContext
    private EntityManager em;

    public CostCentersService() {
    }

    @Transactional
    public void deleteItemsFromNApSkGeolocationForArchiveSk() {
        List<String> listSkKodArch = new ArrayList<>();
        String sql = "select sk_id from css_stanowiska_kosztow where sk_stan_definicji = 'A'" +
                " and sk_id in (select sk_id from nap_sk_geolocation)";
        listSkKodArch =  em.createNativeQuery(sql).getResultList();

        for (String skId : listSkKodArch) {
            String queryString = "delete from nap_sk_geolocation g where g.sk_id = :value";
            int deletedCount = em.createNativeQuery(queryString)
                    .setParameter("value", skId)
                    .executeUpdate();
           // System.out.println("Liczba usuniętych rekordów: " + deletedCount);
        }
    }


    public List<CostCenterDTO> getAllCostCentersForRekeep(String businessGoal) {
        List<CostCenterDTO> costCenters = new ArrayList<>();
        String sql =
                "select sk_id, sk_kod, sk_opis  \n" +
                        ", (select wap_wartosc from css_wartosci_pol where sk_wap_id = wap_id and wap_dep_id = 100022) city\n" +
                        ", (select wap_wartosc from css_wartosci_pol where sk_wap_id = wap_id and wap_dep_id = 100023) street\n" +
                        ", (select wap_wartosc from css_wartosci_pol where sk_wap_id = wap_id and wap_dep_id = 100610) contractType\n" +
                        " from css_stanowiska_kosztow where sk_stan_definicji = 'Z' and (sk_kod like '" + businessGoal + "%')" +
                        " and sk_kod not in (select sk_kod from nap_sk_geolocation)";

        List<Object[]> results =  em.createNativeQuery(sql).getResultList();

        results.forEach( row -> {
            CostCenterDTO c = new CostCenterDTO();
            c.setSkId((BigDecimal) row[0]);
            c.setSkKod((String) row[1]);
            c.setSkDesc((String) row[2]);
            c.setCity((String) row[3]);
            c.setStreet((String) row[4]);
            c.setContractType((String) row[5]);
            c.setBusinessType(businessGoal);
            costCenters.add(c);
        });

        return costCenters;
    }

}

package pl.kskowronski.data.service.egeria.css;

import org.springframework.stereotype.Service;
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


    public List<CostCenterDTO> getAllCostCentersForRekeep(String businessGoal) {
        List<CostCenterDTO> costCenters = new ArrayList<>();
        String sql =
                "select sk_id, sk_kod, sk_opis  \n" +
                        ", (select wap_wartosc from css_wartosci_pol where sk_wap_id = wap_id and wap_dep_id = 100022) city\n" +
                        ", (select wap_wartosc from css_wartosci_pol where sk_wap_id = wap_id and wap_dep_id = 100023) street\n" +
                        "  from css_stanowiska_kosztow where sk_stan_definicji = 'Z' and (sk_kod like '" + businessGoal + "%')";

        List<Object[]> results =  em.createNativeQuery(sql).getResultList();

        results.forEach( row -> {
            CostCenterDTO c = new CostCenterDTO();
            c.setSkId((BigDecimal) row[0]);
            c.setSkKod((String) row[1]);
            c.setSkDesc((String) row[2]);
            c.setCity((String) row[3]);
            c.setStreet((String) row[4]);
            c.setBusinessType(businessGoal);
            costCenters.add(c);
        });

        return costCenters;
    }

}

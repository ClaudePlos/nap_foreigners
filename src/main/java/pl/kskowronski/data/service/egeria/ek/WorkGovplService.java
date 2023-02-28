package pl.kskowronski.data.service.egeria.ek;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.kskowronski.data.entity.egeria.ek.WorkGovpl;
import pl.kskowronski.data.entity.egeria.ek.WorkStatisticDTO;
import pl.kskowronski.data.service.global.ConsolidationService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkGovplService {

    @PersistenceContext
    private EntityManager em;

    private WorkGovplRepo workGovplRepo;

    private ConsolidationService consolidationService;

    public WorkGovplService(WorkGovplRepo workGovplRepo, ConsolidationService consolidationService) {
        this.workGovplRepo = workGovplRepo;
        this.consolidationService = consolidationService;
    }

    public List<WorkGovpl> findAll() {
        return workGovplRepo.findAll(Sort.by(Sort.Direction.ASC, "frmNazwa", "prcNazwisko"));
    }

    public void deleteAll() {
        workGovplRepo.deleteAll();
    }

// spółki do raportu Catermed, Naprzód Hospital, Naprzód Marketing, Izan, Jol-Mark, Naprzód Service
    public List<WorkStatisticDTO> getListToStatistic() {
        List<WorkStatisticDTO> workers = new ArrayList<>();
        consolidationService.setConsolidateCompany();
        String sql =
                "select frm_nazwa--, ob_pelny_kod\n" +
                        ", typ_umowy\n" +
                        ", count(prc_id) pracownikow\n" +
                        ", frm_id\n" +
                        "from (\n" +
                        "--- na UZ\n" +
                        "SELECT distinct zat_typ_umowy,PRC_ID,PRC_NUMER,PRC_NAZWISKO,PRC_IMIE,PRC_NIP,PRC_DATA_UR,PRC_PESEL,PRC_DOWOD_OSOB, frm_nazwa, ob_pelny_kod, prc_plec, zat_typ_angaz\n" +
                        ", (select wsl_alias from CSS_WARTOSCI_SLOWNIKOW where wsl_sl_nazwa = 'TYP_UMOWY' and wsl_wartosc = zat_typ_umowy) typ_umowy\n" +
                        ", case when upper(prc_obywatelstwo) like 'POLSKIE' or prc_obywatelstwo is null then 1 else 0 end pl\n" +
                        ", case when upper(prc_obywatelstwo) not like 'POLSKIE' and prc_obywatelstwo is not null then 1 else 0 end obce\n" +
                        "--, trunc(months_between(:data, prc_data_ur)/12) wiek\n" +
                        "--, decode(substr(zat_status,-1,1),0,0,1) stat\n" +
                        ", null stat, frm_id\n" +
                        " FROM EK_PRACOWNICY, ek_zatrudnienie, css_Stanowiska_kosztow, css_obiekty_w_przedsieb, css_stanowiska_obiekty, eat_firmy  \n" +
                        "WHERE zat_prc_id = prc_id\n" +
                        "and zat_sk_id = sk_id\n" +
                        "and zat_frm_id = frm_id\n" +
                        "and so_sk_id = sk_id\n" +
                        "and so_ob_id = ob_id\n" +
                        "and zat_id in (\n" +
                        "     SELECT zat_id FROM ek_zatrudnienie\n" +
                        "      WHERE prc_id = zat_prc_id \n" +
                        "        AND zat_typ_umowy in (1,2)  -- uz\n" +
                        "        AND sysdate between zat_data_zmiany and NVL(zat_data_do, sysdate))\n" +
                        "union all \n" +
                        "-- na UP\n" +
                        "SELECT distinct zat_typ_umowy, PRC_ID,PRC_NUMER,PRC_NAZWISKO,PRC_IMIE,PRC_NIP,PRC_DATA_UR,PRC_PESEL,PRC_DOWOD_OSOB, frm_nazwa, ob_pelny_kod, prc_plec, zat_typ_angaz\n" +
                        ", (select wsl_alias from CSS_WARTOSCI_SLOWNIKOW where wsl_sl_nazwa = 'TYP_UMOWY' and wsl_wartosc = zat_typ_umowy) typ_umowy\n" +
                        ", case when upper(prc_obywatelstwo) like 'POLSKIE' or prc_obywatelstwo is null then 1 else 0 end pl\n" +
                        ", case when upper(prc_obywatelstwo) not like 'POLSKIE' and prc_obywatelstwo is not null then 1 else 0 end obce\n" +
                        "--, trunc(months_between(:data, prc_data_ur)/12) wiek\n" +
                        ", decode(substr(zat_status,-1,1),0,0,1) stat, frm_id\n" +
                        " FROM EK_PRACOWNICY, ek_zatrudnienie, css_Stanowiska_kosztow, css_obiekty_w_przedsieb, css_stanowiska_obiekty, eat_firmy  \n" +
                        "WHERE zat_prc_id = prc_id\n" +
                        "and zat_sk_id = sk_id\n" +
                        "and zat_frm_id = frm_id\n" +
                        "and so_sk_id = sk_id\n" +
                        "and so_ob_id = ob_id\n" +
                        "AND zat_id in(\n" +
                        "       SELECT zat_id FROM ek_zatrudnienie \n" +
                        "       WHERE prc_id = zat_prc_id \n" +
                        "         AND zat_typ_umowy in (0,7) \n" +
                        "         AND sysdate between zat_data_zmiany and NVL(zat_data_do, sysdate))\n" +
                        ")\n" +
                        "where frm_id in (300319,300000)\n" +
                        "group by frm_nazwa, typ_umowy, zat_typ_umowy, frm_id\n" +
                        "order by frm_nazwa, typ_umowy";
        List<Object[]> result =  em.createNativeQuery(sql).getResultList();

        result.forEach( item -> {
            WorkStatisticDTO s = new WorkStatisticDTO();
            s.setFrmName((String) item[0]);
            s.setTypeOfAgreement((String) item[1]);
            s.setWorkersSum((BigDecimal) item[2]);
            workers.add(s);
        });

        return workers;
    }

}

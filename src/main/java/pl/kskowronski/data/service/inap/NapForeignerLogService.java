package pl.kskowronski.data.service.inap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import pl.kskowronski.data.entity.egeria.ek.Worker;
import pl.kskowronski.data.entity.egeria.ek.WorkerDTO;
import pl.kskowronski.data.entity.inap.NapForeignerLog;
import pl.kskowronski.data.entity.inap.NapForeignerLogDTO;
import pl.kskowronski.data.service.MapperDate;
import pl.kskowronski.data.service.egeria.ek.WorkerRepo;
import pl.kskowronski.data.service.egeria.ek.WorkerService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NapForeignerLogService extends CrudService<NapForeignerLog, BigDecimal> {

    private NapForeignerLogRepo repo;

    public NapForeignerLogService(NapForeignerLogRepo repo, ProcessInstanceRepo processInstanceRepo) {
        this.repo = repo;
    }

    @Override
    protected NapForeignerLogRepo getRepository() {
        return repo;
    }

    @Autowired
    WorkerRepo workerRepo;

    MapperDate mapperDate = new MapperDate();

    public void save(NapForeignerLog napForeignerLog){ repo.save(napForeignerLog);}

    public Optional<List<NapForeignerLogDTO>> findAll(int page, int pageSize){
        Optional<List<NapForeignerLogDTO>> foreignersDTO = Optional.of(new ArrayList<>());
        List<NapForeignerLog> foreigners = repo.findAll(PageRequest.of(page, pageSize)).stream().collect(Collectors.toList());
        foreigners.stream().forEach( item -> foreignersDTO.get().add( mapperNapForeignerLog(item)));
        return foreignersDTO;
    };


    public Optional<List<NapForeignerLogDTO>> findAllAcceptAndDelForPeriod(String period /*, int page, int pageSize*/) throws ParseException {
        Optional<List<NapForeignerLogDTO>> foreignersDTO = Optional.of(new ArrayList<>());
        LocalDate ldLastDay = LocalDate.parse( period+"-01", mapperDate.ldYYYYMMDD).with(TemporalAdjusters.lastDayOfMonth());
        Optional<List<NapForeignerLog>> foreigners = repo.findAllAcceptAndDelForPeriod(mapperDate.dtYYYYMMDD.parse(period+"-01")
                , Date.from(ldLastDay.atStartOfDay(ZoneId.systemDefault()).plusHours(23).plusMinutes(59).toInstant())
               // , PageRequest.of(page, pageSize)
        );
        if (foreigners.isPresent()) {
            foreigners.get().stream().forEach(item -> foreignersDTO.get().add(mapperNapForeignerLog(item)));
        }
        return foreignersDTO;
    };

    public Optional<List<NapForeignerLogDTO>> findAllSuspendedForPeriod(String period) throws Exception {
        Optional<List<NapForeignerLogDTO>> foreignersDTO = Optional.of(new ArrayList<>());
        LocalDate ldLastDay = LocalDate.parse( period+"-01", mapperDate.ldYYYYMMDD).with(TemporalAdjusters.lastDayOfMonth());
        Optional<List<NapForeignerLog>> foreigners = repo.findAllSuspendedForPeriod(mapperDate.dtYYYYMMDD.parse(period+"-01")
                , Date.from(ldLastDay.atStartOfDay(ZoneId.systemDefault()).toInstant()) );
        if (foreigners.isPresent()) {
            foreigners.get().stream().forEach(item -> foreignersDTO.get().add(mapperNapForeignerLog(item)));
        }
        return foreignersDTO;
    };


    private NapForeignerLogDTO mapperNapForeignerLog(NapForeignerLog f){
        NapForeignerLogDTO foreigner = new NapForeignerLogDTO();
        Optional<Worker> worker = workerRepo.findById(f.getPrcId());
        foreigner.setPrcId(f.getPrcId());
        foreigner.setId(f.getId());
        foreigner.setDescription(f.getDescription());
        foreigner.setStatus(f.getStatus());
        foreigner.setWhenDecided(f.getWhenDecided());
        foreigner.setWhoDecided(f.getWhoDecided());
        foreigner.setProcessId(f.getProcessId());
        foreigner.setRefresh(f.getRefresh());
        foreigner.setWhoRunInInap( f.getWhoRunInInap() );
        foreigner.setSkForApplication( f.getSkForApplication() );
        if (worker.isPresent()){
            foreigner.setPrcNumber(worker.get().getPrcNumer());
            foreigner.setPrcName(worker.get().getPrcImie());
            foreigner.setPrcSurname(worker.get().getPrcNazwisko());
        } else {
            foreigner.setPrcNumber(BigDecimal.ZERO);
            foreigner.setPrcName("-");
            foreigner.setPrcSurname("-");
        }
        return foreigner;
    }

}

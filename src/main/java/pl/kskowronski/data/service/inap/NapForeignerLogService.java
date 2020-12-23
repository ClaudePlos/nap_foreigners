package pl.kskowronski.data.service.inap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import pl.kskowronski.data.entity.egeria.ek.Worker;
import pl.kskowronski.data.entity.egeria.ek.WorkerDTO;
import pl.kskowronski.data.entity.inap.NapForeignerLog;
import pl.kskowronski.data.entity.inap.NapForeignerLogDTO;
import pl.kskowronski.data.service.MapperDate;
import pl.kskowronski.data.service.egeria.ek.WorkerRepo;
import pl.kskowronski.data.service.egeria.ek.WorkerService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NapForeignerLogService extends CrudService<NapForeignerLog, BigDecimal> {

    private NapForeignerLogRepo repo;

    public NapForeignerLogService(@Autowired NapForeignerLogRepo repo) {
        this.repo = repo;
    }

    @Override
    protected NapForeignerLogRepo getRepository() {
        return repo;
    }

    @Autowired
    WorkerRepo workerRepo;

    MapperDate mapperDate = new MapperDate();

    public void save(NapForeignerLog napForeignerLog){ repo.save(napForeignerLog);};

    public Optional<List<NapForeignerLogDTO>> findAll(){
        Optional<List<NapForeignerLogDTO>> foreignersDTO = Optional.of(new ArrayList<>());
        List<NapForeignerLog> foreigners = repo.findAll();
        foreigners.stream().forEach( item -> foreignersDTO.get().add( mapperNapForeignerLog(item)));
        return foreignersDTO;
    };


    public Optional<List<NapForeignerLogDTO>> findAllAcceptAndDelForPeriod(String period) throws Exception {
        Optional<List<NapForeignerLogDTO>> foreignersDTO = Optional.of(new ArrayList<>());
        LocalDate ldLastDay = LocalDate.parse( period+"-01", mapperDate.ldYYYYMMDD).with(TemporalAdjusters.lastDayOfMonth());
        Optional<List<NapForeignerLog>> foreigners = repo.findAllAcceptAndDelForPeriod(mapperDate.dtYYYYMMDD.parse(period+"-01")
                , Date.from(ldLastDay.atStartOfDay(ZoneId.systemDefault()).toInstant()) );
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
        if (worker.isPresent()){
            foreigner.setPrcNumber(worker.get().getPrcNumer());
            foreigner.setPrcName(worker.get().getPrcImie());
            foreigner.setPrcSurname(worker.get().getPrcNazwisko());
        }
        return foreigner;
    }

}

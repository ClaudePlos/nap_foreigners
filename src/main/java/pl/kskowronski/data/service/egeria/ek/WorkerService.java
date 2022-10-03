package pl.kskowronski.data.service.egeria.ek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.artur.helpers.CrudService;
import pl.kskowronski.data.entity.egeria.ek.Foreigner;
import pl.kskowronski.data.entity.egeria.ek.Worker;
import pl.kskowronski.data.entity.egeria.ek.WorkerDTO;
import pl.kskowronski.data.service.global.ConsolidationService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerService extends CrudService<Worker, BigDecimal> {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ConsolidationService consolidationService;

    @Autowired
    ForeignerRepo foreignerRepo;

    private WorkerRepo repo;

    private List<Foreigner> foreigners;

    public WorkerService(@Autowired WorkerRepo repo) {
        this.repo = repo;
    }

    @Override
    protected WorkerRepo getRepository() {
        return repo;
    }

    public Optional<List<WorkerDTO>> findByPrcDgKodEk(String prcDgKodEk, String nationality){
        Optional<List<WorkerDTO>> workersDTO = Optional.of(new ArrayList<>());
        Optional<List<Worker>> workers = repo.findByPrcDgKodEkAAndPrcObywatelstwoNotLike(prcDgKodEk, nationality);
        if (workers.isPresent()){
            workers.get().stream().forEach( item -> workersDTO.get().add( mapperWorker(item)));
        }
        return workersDTO;
    }

    public Date getWorkerBirthDate(BigDecimal prcId){ return repo.getByPrcDataUrForPrcId(prcId); }

    public Optional<List<WorkerDTO>> listWorkersToAccept(Boolean polishNationality) {
        consolidationService.setConsolidateCompany();
        Optional<List<WorkerDTO>> workersDTO = Optional.of(new ArrayList<>());
        Optional<List<Worker>> workers;
        if (polishNationality){
            workers = repo.listWorkersToAcceptOnlyPolishNationality();
        } else {
            workers = repo.listWorkersToAccept();
        }

        if (workers.isPresent()){
            foreigners = foreignerRepo.findAll();
            workers.get().stream().forEach( item -> workersDTO.get().add( mapperWorker(item)));
        }
        return workersDTO;
    };


    @Transactional
    public void acceptForeignerApplication(String text, BigDecimal processId) {
        this.em.createNativeQuery("update naprzod2.nzap_zapotrzebowania set opis_zadania = '" + text +
                        " [data i godz.: " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]' " +
                        " where proces_id = " + processId)
                .executeUpdate();
    }


    private WorkerDTO mapperWorker(Worker w){
        WorkerDTO worker = new WorkerDTO();
        worker.setPrcId(w.getPrcId());
        worker.setPrcPesel(w.getPrcPesel());
        worker.setPrcDowodOsob(w.getPrcDowodOsob());
        worker.setPrcImie(w.getPrcImie());
        worker.setPrcNazwisko(w.getPrcNazwisko());
        worker.setPrcDgKodEk(w.getPrcDgKodEk());
        worker.setPrcNumer(w.getPrcNumer());
        worker.setPrcObywatelstwo(w.getPrcObywatelstwo());
        worker.setPrcPlec(w.getPrcPlec());
        Foreigner foreigner = foreigners.stream()
                .filter( item -> item.getPrcId().toString().equals(w.getPrcId().toString()) )
                .findAny()
                .orElse(null);
        if (foreigner != null){
            worker.setProcesId(foreigner.getProcesId());
            worker.setSk(foreigner.getSK());
            worker.setRunProcess(foreigner.getRunProcess());
            worker.setRunDate(foreigner.getRunDate());
            worker.setStatus(foreigner.getStatus());
        }

        return worker;
    }




}

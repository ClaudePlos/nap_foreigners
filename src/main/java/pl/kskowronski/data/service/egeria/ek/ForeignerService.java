package pl.kskowronski.data.service.egeria.ek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import pl.kskowronski.data.entity.egeria.ek.Foreigner;
import pl.kskowronski.data.entity.egeria.ek.WorkerDTO;
import pl.kskowronski.data.service.global.ConsolidationService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ForeignerService extends CrudService<Foreigner, BigDecimal> {

    private ForeignerRepo repo;

    public ForeignerService(@Autowired ForeignerRepo repo) {
        this.repo = repo;
    }

    @Override
    protected ForeignerRepo getRepository() {
        return repo;
    }

    @Autowired
    ConsolidationService consolidationService;


    public Optional<List<WorkerDTO>> findAll(){
        consolidationService.setConsolidateCompany();
        List<Foreigner> foreigners = repo.findAll(Sort.by("runDate").descending());
        Optional<List<WorkerDTO>> workersDTO = Optional.of(new ArrayList<>());
        foreigners.stream()
                //.sorted(Comparator.comparing(Foreigner::getRunDate))
                .forEach(item -> workersDTO.get().add( mapperWorker(item)));
        return workersDTO;
    }



    private WorkerDTO mapperWorker(Foreigner f) {
        WorkerDTO worker = new WorkerDTO();
        worker.setPrcId(f.getPrcId());
        worker.setPrcImie(f.getPrcImie());
        worker.setPrcNazwisko(f.getPrcNazwisko());
        worker.setPrcNumer(f.getPrcNumer());
        worker.setPrcObywatelstwo(f.getPrcObywatelstwo());
        worker.setProcesId(f.getProcesId());
        worker.setSk(f.getSK());
        worker.setRunProcess(f.getRunProcess());
        worker.setRunDate(f.getRunDate());
        worker.setStatus(f.getStatus());
        worker.setTypeOfAgreement(f.getTypeOfAgreement());
        worker.setPlatform(f.getPlatform());
        return worker;
    }

}

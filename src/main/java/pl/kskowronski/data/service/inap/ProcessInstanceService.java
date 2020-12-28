package pl.kskowronski.data.service.inap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import pl.kskowronski.data.entity.inap.ProcessInstance;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProcessInstanceService extends CrudService<ProcessInstance, BigDecimal> {

    private ProcessInstanceRepo repo;

    public ProcessInstanceService(@Autowired ProcessInstanceRepo repo) {
        this.repo = repo;
    }

    @Override
    protected ProcessInstanceRepo getRepository() {
        return repo;
    }

    public Optional<ProcessInstance> getProcessInstance(BigDecimal id){ return repo.findById(id); }

}

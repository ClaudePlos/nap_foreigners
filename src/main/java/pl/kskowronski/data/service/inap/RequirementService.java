package pl.kskowronski.data.service.inap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import pl.kskowronski.data.entity.inap.Requirement;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class RequirementService extends CrudService<Requirement, BigDecimal> {

    private RequirementRepo repo;

    public RequirementService(@Autowired RequirementRepo repo) {
        this.repo = repo;
    }

    @Override
    protected RequirementRepo getRepository() {
        return repo;
    }


    public Optional<Requirement> getRequirementForProcess(BigDecimal processID){
        return repo.getByProcesId(processID);
    }

}

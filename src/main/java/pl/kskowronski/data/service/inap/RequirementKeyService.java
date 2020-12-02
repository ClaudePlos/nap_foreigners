package pl.kskowronski.data.service.inap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import pl.kskowronski.data.entity.inap.RequirementKey;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RequirementKeyService extends CrudService<RequirementKey, BigDecimal>  {

    private RequirementKeyRepo repo;

    public RequirementKeyService(@Autowired RequirementKeyRepo repo) {
        this.repo = repo;
    }

    @Override
    protected RequirementKeyRepo getRepository() {
        return repo;
    }

    public Optional<List<RequirementKey>> getRequirementForProcess(BigDecimal processID){
        return repo.getRequirementForProcess(processID);
    }

}

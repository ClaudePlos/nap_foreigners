package pl.kskowronski.data.service.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import pl.kskowronski.data.entity.global.EatFirma;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EatFirmaService extends CrudService<EatFirma, BigDecimal> {

    private EatFirmaRepo repo;

    public EatFirmaService(@Autowired EatFirmaRepo repo) {
        this.repo = repo;
    }

    @Override
    protected EatFirmaRepo getRepository() {
        return repo;
    }

    public Optional<EatFirma> findById(BigDecimal frmId){ return repo.findById(frmId);}

    public Optional<EatFirma> findByCompanyName(String frmName){ return repo.findByFrmNazwa(frmName);}

}

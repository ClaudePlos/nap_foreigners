package pl.kskowronski.data.service.egeria.css;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import pl.kskowronski.data.entity.egeria.css.Dictionary;

import java.util.Optional;

@Service
public class DictionaryService extends CrudService<Dictionary, String> {

    private DictionaryRepo repo;

    public DictionaryService(@Autowired DictionaryRepo repo) {
        this.repo = repo;
    }

    @Override
    protected DictionaryRepo getRepository() {
        return repo;
    }

    public Optional<String> getSubjectOfTheContract(String value ){
        return repo.getSubjectOfTheContract(value);
    }

    public Optional<String> getTypeOfContract(String value ){
        return repo.getTypeOfContract(value);
    }

}

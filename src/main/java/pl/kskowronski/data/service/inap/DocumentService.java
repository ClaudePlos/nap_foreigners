package pl.kskowronski.data.service.inap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import pl.kskowronski.data.entity.egeria.ek.Worker;
import pl.kskowronski.data.entity.inap.Document;
import pl.kskowronski.data.entity.inap.DocumentDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService extends CrudService<Document, BigDecimal> {

    private DocumentRepo repo;

    public DocumentService(@Autowired DocumentRepo repo) {
        this.repo = repo;
    }

    @Override
    protected DocumentRepo getRepository() {
        return repo;
    }

    public Optional<List<DocumentDTO>> getDocumentForPrc(BigDecimal prcId){
        Optional<List<DocumentDTO>> documentsDTO = Optional.of(new ArrayList<>());
        Optional<List<Document>> documents = repo.getDocumentForPrc(prcId);
        if (documents.isPresent()){
            documents.get().stream().forEach( item -> documentsDTO.get().add(mapperDocument(item)));
        }
        return documentsDTO;
    };

    private DocumentDTO mapperDocument( Document doc){
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setId(doc.getId());
        docDTO.setFrmId(doc.getFrmId());
        docDTO.setNazwa(doc.getNazwa());
        docDTO.setOpis(doc.getOpis());
        return docDTO;
    }

    public String generateUrlForPDF(BigDecimal dokId){
       return "https://i.naprzod.pl/i/dok?action=getpdf&dokId=" + dokId;
    }
}
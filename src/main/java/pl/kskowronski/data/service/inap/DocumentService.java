package pl.kskowronski.data.service.inap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import pl.kskowronski.data.entity.egeria.ek.Worker;
import pl.kskowronski.data.entity.inap.Document;
import pl.kskowronski.data.entity.inap.DocumentDTO;
import pl.kskowronski.data.service.global.EatFirmaRepo;

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

    @Autowired
    private EatFirmaRepo eatFirmaRepo;

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
        docDTO.setPlatform("i.nap");
        if (doc.getFrmId() != null ){
            docDTO.setFrmName( eatFirmaRepo.findById(doc.getFrmId()).get().getFrmNazwa() );
        }

        return docDTO;
    }

    public String generateUrlForPDF(BigDecimal dokId, String platform, String path){
        if (platform.equals("i.nap") || platform == null) {
            return "https://i.naprzod.pl/i/dok?action=getpdf&dokId=" + dokId;
        } else {
            return "http://fs1/ftp/" + path.replace("/home/plusworkflow/Dokumenty","");
        }
    }
}

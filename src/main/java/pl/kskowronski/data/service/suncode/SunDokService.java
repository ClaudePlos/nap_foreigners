package pl.kskowronski.data.service.suncode;

import org.springframework.stereotype.Service;
import pl.kskowronski.data.entity.inap.Document;
import pl.kskowronski.data.entity.inap.DocumentDTO;
import pl.kskowronski.data.entity.suncode.SunDok;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SunDokService {

    private SunDokRepo repo;

    public SunDokService(SunDokRepo repo) {
        this.repo = repo;
    }


    public Optional<List<DocumentDTO>> getDocumentForPrc(BigDecimal prcId){
        Optional<List<DocumentDTO>> documentsDTO = Optional.of(new ArrayList<>());
        Optional<List<SunDok>> documents = repo.getDocumentForPrc(prcId);
        if (documents.isPresent()){
            documents.get().stream().forEach( item -> documentsDTO.get().add(mapperDocument(item)));
        }
        return documentsDTO;
    }

    private DocumentDTO mapperDocument( SunDok doc){
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setId(doc.getId());
        docDTO.setNazwa(doc.getRodzajDok());
        docDTO.setOpis("file://fs1/ftp/dev/" + doc.getSciezka().replace("/home/plusworkflow/Dokumenty",""));
        docDTO.setPath(doc.getSciezka());
        docDTO.setPlatform("suncode");
        return docDTO;
    }

}

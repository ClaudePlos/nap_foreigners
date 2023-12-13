package pl.kskowronski.data.service.suncode;

import org.springframework.stereotype.Service;
import pl.kskowronski.data.entity.suncode.SunEgZatForeigner;

import java.math.BigDecimal;

@Service
public class SunEgZatForeignerService {

    private SunEgZatForeignerRepo repo;

    public SunEgZatForeignerService(SunEgZatForeignerRepo repo) {
        this.repo = repo;
    }

    public SunEgZatForeigner findBySunZatId(BigDecimal sunZatId){
        return repo.findBySunZatId(sunZatId).get();
    }

}

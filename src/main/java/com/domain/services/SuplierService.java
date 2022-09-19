package com.domain.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.models.entities.Suplier;
import com.domain.models.repos.SuplierRepo;

@Service
@Transactional
public class SuplierService {
    
    @Autowired
    private SuplierRepo suplierRepo;

    public Suplier save(Suplier suplier) {
        return suplierRepo.save(suplier);
    }

    public Suplier findOne(Long id) {
        Optional<Suplier> suplier = suplierRepo.findById(id);
        if(!suplier.isPresent()) {
            return null;
        }
        return suplier.get();
    }

    public Iterable<Suplier> findAll() {
        return suplierRepo.findAll();
    }

    public void removeOne(Long id) {
        suplierRepo.deleteById(id);
    }
}

package com.domain.models.repos;

import org.springframework.data.repository.CrudRepository;

import com.domain.models.entities.Suplier;

public interface SuplierRepo extends CrudRepository<Suplier, Long> {
    
}

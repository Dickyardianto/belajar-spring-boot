package com.domain.models.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.domain.models.entities.Suplier;

public interface SuplierRepo extends CrudRepository<Suplier, Long> {
    
    Suplier findByEmail(String email); //email adalah field yang ada di kelas entity Supplier

    List<Suplier> findByNameContainsOrderByIdDesc(String name); // list digunakan untuk menampilkan beberapa data

    List<Suplier> findByNameStartingWith(String name);

    List<Suplier> findByNameContainsOrEmailContains(String name, String email);
}

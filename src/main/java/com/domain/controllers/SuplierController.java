package com.domain.controllers;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.dto.ResponseData;
import com.domain.dto.SearchDTO;
import com.domain.dto.SuplierDTO;
import com.domain.models.entities.Suplier;
import com.domain.services.SuplierService;

@RestController
@RequestMapping("/api/supliers")
public class SuplierController {
    
    @Autowired
    private SuplierService suplierService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Suplier>> create(@Valid @RequestBody SuplierDTO suplierDTO, Errors errors) {
        ResponseData<Suplier> responseData = new ResponseData<>();
        if(errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Suplier suplier = modelMapper.map(suplierDTO, Suplier.class);

        responseData.setStatus(true);
        responseData.setData(suplierService.save(suplier));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public Iterable<Suplier> findAll() {
        return suplierService.findAll();
    }

    @GetMapping("/{id}")
    public Suplier findOne(@PathVariable("id") Long id) {
        return suplierService.findOne(id);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Suplier>> update(@Valid @RequestBody SuplierDTO suplierDTO, Errors errors) {
        ResponseData<Suplier> responseData = new ResponseData<>();
        if(errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Suplier suplier = modelMapper.map(suplierDTO, Suplier.class);

        responseData.setStatus(true);
        responseData.setData(suplierService.save(suplier));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/byemail")
    public ResponseEntity<ResponseData<Suplier>> findByEmail(@RequestBody SearchDTO searchDTO, Errors errors) {
        ResponseData<Suplier> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setData(suplierService.findByEmail(searchDTO.getSearchKey()));
        return ResponseEntity.ok(responseData);

        // return suplierService.findByEmail(searchDTO.getSearchKey());
    }

    @PostMapping("/search/byName")
    public List<Suplier> findByName(@RequestBody SearchDTO searchDTO) {
        return suplierService.findByName(searchDTO.getSearchKey());
    }

    @PostMapping("/search/byNameStartWith")
    public List<Suplier> findByNameStartWith(@RequestBody SearchDTO searchDTO) {
        return suplierService.findByNameStartWith(searchDTO.getSearchKey());
    }

    @PostMapping("/search/byNameOrEmail")
    public List<Suplier> findByNameOrEmail(@RequestBody SearchDTO searchDTO) {
        return suplierService.findByNameOrEmail(searchDTO.getSearchKey(), searchDTO.getOtherSearchKey());
    }
}

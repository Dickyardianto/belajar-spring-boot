package com.domain.controllers;

import java.util.Arrays;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.domain.dto.CategoryDTO;
import com.domain.dto.ResponseData;
import com.domain.dto.SearchDTO;
import com.domain.models.entities.Category;
import com.domain.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Category>> create(@Valid @RequestBody CategoryDTO categoryDTO, Errors errors) {
        ResponseData<Category> responseData = new ResponseData<>();
        if(errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setData(null);
            System.out.println(errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
        responseData.setStatus(true);
        responseData.setData(categoryService.save(category));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public Iterable<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findOne(@PathVariable("id") Long id) {
        return categoryService.findOne(id);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Category>> update(@Valid @RequestBody CategoryDTO categoryDTO, Errors errors) {
        ResponseData<Category> responseData = new ResponseData<>();
        if(errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
        responseData.setStatus(true);
        responseData.setData(categoryService.save(category));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/{size}/{page}")
    public Iterable<Category> findByNameContains(@RequestBody SearchDTO searchDTO, @PathVariable("size") int size, @PathVariable("page") int page) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryService.findByName(searchDTO.getSearchKey(), pageable);
    }

    @PostMapping("/search/{size}/{page}/{sort}")
    public Iterable<Category> findByNameContainsSort(@RequestBody SearchDTO searchDTO, 
    @PathVariable("size") int size, 
    @PathVariable("page") int page,
    @PathVariable("sort") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        if(sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("id").descending());
        }
        return categoryService.findByName(searchDTO.getSearchKey(), pageable);
    }

    @PostMapping("/batch")
    public ResponseEntity<ResponseData<Iterable<Category>>> createBatch(@RequestBody Category[] categories) {
        ResponseData<Iterable<Category>> responseData = new ResponseData<>();
        responseData.setData(categoryService.saveBatch(Arrays.asList(categories)));
        responseData.setStatus(true);
        return ResponseEntity.ok(responseData);
    }
}

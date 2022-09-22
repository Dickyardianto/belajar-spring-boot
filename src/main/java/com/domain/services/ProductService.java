package com.domain.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.models.entities.Product;
import com.domain.models.entities.Suplier;
import com.domain.models.repos.ProductRepo;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepo productRepo;

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public Product findOne(Long id){
        Optional<Product> product = productRepo.findById(id);
        if(!product.isPresent()) {
            return null;
        }else {
            return productRepo.findById(id).get();
        }
    }

    public Iterable<Product> findAll() {
        return productRepo.findAll();
    }

    public void removeOne(Long id) {
        productRepo.deleteById(id);
    }

    public List<Product> findByName(Product product) {
        return productRepo.findByNameContains(product.getName());
    }

    public void addSuplier(Suplier suplier, Long productId) {
        Product product = findOne(productId);
        if(product == null) {
            throw new RuntimeException("Product with ID " + productId + "Not Found");
        }
        product.getSupliers().add(suplier);
        save(product);
    }
}

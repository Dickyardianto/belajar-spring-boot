package com.domain.models.repos;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.domain.models.entities.Product;
import com.domain.models.entities.Suplier;

public interface ProductRepo extends CrudRepository<Product, Long>{
    
    List<Product> findByNameContains(String name);

    @Query("SELECT n FROM Product n WHERE n.name = :name") //adalah nama nama yang ada di kelas entity
    public Product findProductByName(@PathParam("name") String name);

    @Query("SELECT n FROM Product n WHERE n.name LIKE :name")
    public List<Product> findProductByNameLike(@PathParam("name") String name);

    @Query("SELECT c FROM Product c WHERE c.category.id = :categoryId")
    public List<Product> findProductByCategory(@PathParam("categoryId") Long categoryId);

    @Query("SELECT s FROM Product s WHERE :supliers MEMBER OF s.supliers")
    public List<Product> findProductBySupliers(@PathParam("supliers") Suplier supliers);
}

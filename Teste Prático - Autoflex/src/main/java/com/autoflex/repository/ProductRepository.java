package com.autoflex.repository;


import com.autoflex.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p ORDER BY p.value DESC")
    List<Product> findAllOrderByValueDesc();
}


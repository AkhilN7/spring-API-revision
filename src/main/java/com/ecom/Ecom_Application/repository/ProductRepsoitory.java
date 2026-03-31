package com.ecom.Ecom_Application.repository;

import com.ecom.Ecom_Application.dto.ProductResponse;
import com.ecom.Ecom_Application.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepsoitory extends JpaRepository<Product,Long> {


    public List<Product> findByActiveTrue();

    @Query("SELECT p FROM products p " +
            "WHERE p.active = true " +
            "AND p.stockQuantity > 0 " +
            "AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(String keyword);
}

package com.ecom.Ecom_Application.repository;

import com.ecom.Ecom_Application.model.CartItem;
import com.ecom.Ecom_Application.model.Product;
import com.ecom.Ecom_Application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);   // ← was String, now User

    void deleteByUser(User user);
}
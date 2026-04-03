package com.ecom.Ecom_Application.service;

import com.ecom.Ecom_Application.dto.CartItemRequest;
import com.ecom.Ecom_Application.model.CartItem;
import com.ecom.Ecom_Application.model.Product;
import com.ecom.Ecom_Application.model.User;
import com.ecom.Ecom_Application.repository.CartItemRepository;
import com.ecom.Ecom_Application.repository.ProductRepsoitory;
import com.ecom.Ecom_Application.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepsoitory productRepsoitory;

    @Autowired
    private UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOptional = productRepsoitory.findById(request.getProductId());
        if(productOptional.isEmpty()) return false;

        Product product = productOptional.get();
        if(product.getStockQuantity()< request.getQuantity())  return false;

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()) return false;

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user,product);

        if (existingCartItem != null) {

            int newQuantity = existingCartItem.getQuantity() + request.getQuantity();

            existingCartItem.setQuantity(newQuantity);

            existingCartItem.setPrice(
                    product.getPrice().multiply(BigDecimal.valueOf(newQuantity))
            );
            cartItemRepository.save(existingCartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;


    }

    public boolean deleteFromCart(String userId, Long productId) {
        Optional<Product> productOptional = productRepsoitory.findById(productId);
        if (productOptional.isEmpty()) return false;

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()) return false;

        Product product = productOptional.get();
        User user = userOpt.get();

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);

        if (cartItem == null) {
            return false; //
        }

        cartItemRepository.delete(cartItem);
        return true;
    }

    public List<CartItem> findItems(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(cartItemRepository::findByUser)
                .orElse(List.of());
    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId))
                .ifPresent(user -> cartItemRepository.deleteByUser(user));
    }
}

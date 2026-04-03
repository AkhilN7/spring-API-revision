package com.ecom.Ecom_Application.controller;

import com.ecom.Ecom_Application.dto.CartItemRequest;
import com.ecom.Ecom_Application.model.CartItem;
import com.ecom.Ecom_Application.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Boolean> addToCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequest request){
        if(cartService.addToCart(userId,request))
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Boolean> removeFromCart(@RequestHeader("X-User-Id") String userId, @PathVariable Long productId){
        boolean deleteed = cartService.deleteFromCart(userId,productId);
        return deleteed? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-User-Id") String userId){
        return new ResponseEntity<>(cartService.findItems(userId),HttpStatus.OK);
    }

}

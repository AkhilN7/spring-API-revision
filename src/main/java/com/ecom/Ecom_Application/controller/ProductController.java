package com.ecom.Ecom_Application.controller;

import com.ecom.Ecom_Application.dto.AddressDTO;
import com.ecom.Ecom_Application.dto.ProductRequest;
import com.ecom.Ecom_Application.dto.ProductResponse;
import com.ecom.Ecom_Application.model.Product;
import com.ecom.Ecom_Application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> createProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        return new ResponseEntity<>(productService.updateProduct(id, productRequest), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        boolean flag = productService.deleteProduct(id);
        return flag ? ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyWord){
        return new ResponseEntity<>(productService.searchProducts(keyWord), HttpStatus.OK);
    }

}

package com.ecom.Ecom_Application.service;

import com.ecom.Ecom_Application.dto.ProductRequest;
import com.ecom.Ecom_Application.dto.ProductResponse;
import com.ecom.Ecom_Application.model.Product;
import com.ecom.Ecom_Application.repository.ProductRepsoitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepsoitory productRepsoitory;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest( product,productRequest);
        Product savedProduct = productRepsoitory.save(product);
        return mapToProductResponse(savedProduct);
    }

    public ProductResponse mapToProductResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setActive(product.getActive());
        productResponse.setCategory(product.getCategory());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setStockQuantity(product.getStockQuantity());
        return productResponse;
    }

    public void updateProductFromRequest(Product product,ProductRequest productRequest){
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Optional<Product> optionalProduct = productRepsoitory.findById(id);
        if(optionalProduct.isEmpty()) return null;
//        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            updateProductFromRequest(product,productRequest);
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setDescription(product.getDescription());
            response.setPrice(product.getPrice());
            response.setCategory(product.getCategory());
            response.setImageUrl(product.getImageUrl());
            response.setActive(product.getActive());
            response.setStockQuantity(product.getStockQuantity());
            productRepsoitory.save(product);
            return response;
//        }
//



    }

    public List<ProductResponse> getAllProducts( ) {
        List<Product> products = productRepsoitory.findByActiveTrue();
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product p:products){
            ProductResponse pr = mapToProductResponse(p);
            productResponses.add(pr);
        }
        return productResponses;
    }

    public boolean  deleteProduct(Long id) {
        Optional<Product> oproduct = productRepsoitory.findById(id);
        if(oproduct.isPresent()){
            Product p = oproduct.get();
            p.setActive(false);
            productRepsoitory.save(p);
            return true;
        }
        //productRepsoit    ory.deleteById(id);
        return false;
    }

    public List<Product> searchProducts(String keyword) {
        return productRepsoitory.searchProducts(keyword);
    }
}

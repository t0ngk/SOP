package com.t0ng.lab07;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Boolean addProduct(Product product){
        try {
            Product oldProduct = productRepository.findByProductName(product.getProductName());
            if (oldProduct != null) return false;
            productRepository.save(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @CachePut(value = "product", key = "#product.get_id()")
    public Boolean updateProduct(Product product){
        try {
            Product oldProduct = productRepository.findById(product.get_id()).orElse(null);
            if (oldProduct == null) return false;
            productRepository.save(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @CacheEvict(value = "product", key = "#productName")
    public Boolean deleteProduct(String productName){
        try {
            productRepository.delete(productRepository.findByProductName(productName));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Cacheable(value = "product", key = "#productName")
    public List<Product> getAllProduct(){
        try {
            return productRepository.findAll();
        } catch (Exception e){
            return null;
        }
    }

    @Cacheable(value = "product", key = "#productName")
    public Product getProductByName(String productName){
        try {
            return productRepository.findByProductName(productName);
        } catch (Exception e){
            return null;
        }
    }
}

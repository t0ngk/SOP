package com.t0ng.lab07;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "AddProductQueue")
    public boolean serviceAddProduct(Product product) {
        try {
            return productService.addProduct(product);
        } catch (Exception e) {
            return false;
        }
    }

    @RabbitListener(queues = "UpdateProductQueue")
    public boolean serviceUpdateProduct(Product product) {
        try {
            return productService.updateProduct(product);
        } catch (Exception e) {
            return false;
        }
    }

    @RabbitListener(queues = "DeleteProductQueue")
    public boolean serviceDeleteProduct(String productName) {
        try {
            return productService.deleteProduct(productName);
        } catch (Exception e) {
            return false;
        }
    }

    @RabbitListener(queues = "GetAllProductQueue")
    public List<Product> serviceGetAllProduct() {
        try {
            return productService.getAllProduct();
        } catch (Exception e) {
            return null;
        }
    }

    @RabbitListener(queues = "GetNameProductQueue")
    public Product serviceGetProductByName(String productName) {
        try {
            return productService.getProductByName(productName);
        } catch (Exception e) {
            return null;
        }
    }
}

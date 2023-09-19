package com.example.lab72.controller;

import com.example.lab72.pojo.Product;
import com.example.lab72.pojo.Products;
import com.example.lab72.repository.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ProductController {

    @Autowired
    private ProductService pdService;

    @RabbitListener(queues = "AddProductQueue")
    public boolean serviceAddProduct(Product pd) {
        return this.pdService.addProduct(pd);
    }

    @RabbitListener(queues = "UpdateProductQueue")
    public boolean serviceUpdateProduct(Product pd) {
        return this.pdService.updateProduct(pd);
    }

    @RabbitListener(queues = "DeleteProductQueue")
    public boolean serviceDeleteProduct(Product pd) {
        return this.pdService.deleteProduct(pd);
    }

    @RabbitListener(queues = "GetNameProductQueue")
    public Product serviceGetProductName(String pdName) {
        System.out.println(pdName);
        return this.pdService.getProductByName(pdName);
    }

    @RabbitListener(queues = "GetAllProductQueue")
    public Products serviceGetAllProduct() {
        return this.pdService.getAllProduct();
    }
}

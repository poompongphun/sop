package com.example.lab72.repository;

import com.example.lab72.pojo.Product;
import com.example.lab72.pojo.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {
    @Autowired
    private ProductRepository pdRepository;
    public boolean addProduct(Product pd) {
        System.out.println(this.pdRepository.insert(pd));
        return true;
    }

    public boolean updateProduct(Product pd) {
        this.pdRepository.save(pd);
        return true;
    }

    public boolean deleteProduct(Product pd) {
        this.pdRepository.delete(pd);
        return true;
    }

    public Products getAllProduct() {
        Products products = new Products();
        products.products = (ArrayList<Product>) this.pdRepository.findAll();
        return products;
    }

    public Product getProductByName(String pdName) {
        return this.pdRepository.findByName(pdName);
    }
}

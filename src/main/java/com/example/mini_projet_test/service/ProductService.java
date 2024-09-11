package com.example.mini_projet_test.service;

import com.example.mini_projet_test.entity.Product;
import com.example.mini_projet_test.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getOneProductById(Long id) {
        return productRepository.findById(id);
    }

    public void addOneProduct(Product product) {
        Product productToAdd = Product.builder()
                .product_title(product.getProduct_title())
                .product_description(product.getProduct_description())
                .product_price(product.getProduct_price())
                .build();
        productRepository.save(productToAdd);

    }

    public boolean updateOneProduct(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return false;
        }
        Product productToUpdate = optionalProduct.get();
        productToUpdate.setProduct_description(product.getProduct_description());
        productToUpdate.setProduct_title(product.getProduct_title());
        productToUpdate.setProduct_price(product.getProduct_price());
        productRepository.save(productToUpdate);
        return true;
    }

    public void deleteOneProduct(Long id) {
        productRepository.deleteById(id);
    }
}

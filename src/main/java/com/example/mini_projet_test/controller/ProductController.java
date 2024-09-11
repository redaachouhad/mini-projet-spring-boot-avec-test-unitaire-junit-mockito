package com.example.mini_projet_test.controller;


import com.example.mini_projet_test.entity.Product;
import com.example.mini_projet_test.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/getOneProductById/{id}")
    public ResponseEntity<Product> getOneProductById(@PathVariable("id") Long id) {
        Optional<Product> productRes = productService.getOneProductById(id);
        return productRes.map(product -> ResponseEntity.status(HttpStatus.OK).body(product)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/addOneProduct")
    public ResponseEntity<String> addOneProduct(@RequestBody Product product) {
        productService.addOneProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body("Product " + product + " is added successfully");
    }

    @PutMapping("/updateOneProduct/{id}")
    public ResponseEntity<String> updateOneProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        boolean updated = productService.updateOneProduct(id, product);
        if (updated) {
            return ResponseEntity.status(HttpStatus.OK).body("The product with { id : " + id + " }  is updated");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The product is not found");
    }

    @DeleteMapping("/deleteOneProduct/{id}")
    public ResponseEntity<String> deleteOneProduct(@PathVariable("id") Long id) {
        productService.deleteOneProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("The product with { id : " + id + " } is delete");

    }


}

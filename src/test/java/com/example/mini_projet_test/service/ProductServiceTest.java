package com.example.mini_projet_test.service;

import com.example.mini_projet_test.entity.Product;
import com.example.mini_projet_test.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;
    private Product productUpdate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = Product.builder()
                .product_id(1L)
                .product_title("Iphone")
                .product_description("this phone is modern")
                .product_price(200.5)
                .build();
        productUpdate = Product.builder()
                .product_id(2L)
                .product_title("TV")
                .product_description("this TV is big")
                .product_price(1000.5)
                .build();
    }

    @Test
    void testGetAllProducts() {

        // Preparation the mock of productRepository
        Mockito.when(productRepository.findAll()).thenReturn(List.of(product));
        // Appel de la méthode à tester
        List<Product> products = productService.getAllProducts();
        // Assertion de non-nullité
        assertNotNull(products);
        // Assertion d'égalité de id, title, description, price de produit
        assertEquals(1, products.size());
        assertEquals(1L, products.get(0).getProduct_id());
        assertEquals("Iphone", products.get(0).getProduct_title());
        assertEquals("this phone is modern", products.get(0).getProduct_description());
        assertEquals(200.5, products.get(0).getProduct_price());
        // Vérification de l'appel du Mock
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetOneProductById_Found() {
//        Cas non null
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);
        Optional<Product> productOptional1 = productService.getOneProductById(1L);
        Product product1 = null;
        if (productOptional1.isPresent()) {
            product1 = productOptional1.get();
        }
        assertNotNull(product1);
        assertEquals(1L, product1.getProduct_id());
        assertEquals("Iphone", product1.getProduct_title());
        assertEquals("this phone is modern", product1.getProduct_description());
        assertEquals(200.5, product1.getProduct_price());
        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testGetOneProductById_NOT_Found() {

        Mockito.when(productRepository.findById(1L)).thenReturn(null);
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);
        Optional<Product> productOptional1 = productService.getOneProductById(1L);
        assertNull(productOptional1);
        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testAddOneProduct() {
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);
        productService.addOneProduct(product);
        Mockito.verify(productRepository, Mockito.times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateOneProduct_Found() {
// case true:
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        boolean bol = productService.updateOneProduct(1L, productUpdate);
        assertTrue(bol);
        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testUpdateOneProduct_NOT_Found() {
// case false:
        Mockito.when(productRepository.findById(2L)).thenReturn(Optional.of(product));
        boolean bol = productService.updateOneProduct(1L, productUpdate);
        assertFalse(bol);
        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testDeleteOneProduct() {
        Mockito.doNothing().when(productRepository).deleteById(1L);
        productService.deleteOneProduct(1L);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1L);
    }
}
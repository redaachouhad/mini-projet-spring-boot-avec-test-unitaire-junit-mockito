package com.example.mini_projet_test.controller;


import com.example.mini_projet_test.entity.Product;
import com.example.mini_projet_test.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    private Product product;
    private final String baseUrl = "/product";

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1L, "Iphone", "it is an amazing phone", 1000.5);
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(product));
        mockMvc.perform(get(baseUrl + "/getAllProducts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].product_id").value(1L))
                .andExpect(jsonPath("$[0].product_title").value("Iphone"))
                .andExpect(jsonPath("$[0].product_description").value("it is an amazing phone"))
                .andExpect(jsonPath("$[0].product_price").value(1000.5))
        ;
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetOneProductById_FOUND_product() throws Exception {
        when(productService.getOneProductById(product.getProduct_id())).thenReturn(Optional.ofNullable(product));
        mockMvc.perform(get(baseUrl + "/getOneProductById/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.product_id").value(1L))
                .andExpect(jsonPath("$.product_title").value("Iphone"))
                .andExpect(jsonPath("$.product_description").value("it is an amazing phone"))
                .andExpect(jsonPath("$.product_price").value(1000.5))
        ;
        verify(productService, times(1)).getOneProductById(product.getProduct_id());
    }

    @Test
    void testGetOneProductById_NOT_FOUND_product() throws Exception {
        when(productService.getOneProductById(product.getProduct_id())).thenReturn(Optional.empty());
        mockMvc.perform(get(baseUrl + "/getOneProductById/1"))
                .andExpect(status().isNotFound())
        ;
        verify(productService, times(1)).getOneProductById(product.getProduct_id());
    }

    //
    @Test
    void testAddOneProduct() throws Exception {
        doNothing().when(productService).addOneProduct(product);
        mockMvc.perform(post(baseUrl + "/addOneProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))
                )

                .andExpect(status().isCreated())
                .andExpect(content().string("Product is added successfully"));
        verify(productService, times(1)).addOneProduct(product);
    }

    //
    @Test
    void testUpdateOneProduct_FOUND_product() throws Exception {
        Product product1 = Product.builder()
                .product_title("TV")
                .product_description("Big TV")
                .product_price(400.23)
                .build();
        when(productService.updateOneProduct(product.getProduct_id(), product1)).thenReturn(true);

        mockMvc.perform(put(baseUrl + "/updateOneProduct/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product1))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("The product is updated"));
        verify(productService, times(1)).updateOneProduct(product.getProduct_id(), product1);

    }

    @Test
    void testUpdateOneProduct_NOT_FOUND_product() throws Exception {
        Product product1 = Product.builder()
                .product_title("TV")
                .product_description("Big TV")
                .product_price(400.23)
                .build();
        when(productService.updateOneProduct(product.getProduct_id(), product1)).thenReturn(false);

        mockMvc.perform(put(baseUrl + "/updateOneProduct/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product1))
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string("The product is not found"));
        verify(productService, times(1)).updateOneProduct(product.getProduct_id(), product1);

    }
//
    @Test
    void testDeleteOneProduct() throws Exception {
        doNothing().when(productService).deleteOneProduct(product.getProduct_id());

        mockMvc.perform(delete(baseUrl+"/deleteOneProduct/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("The product is deleted"));

        verify(productService, times(1)).deleteOneProduct(product.getProduct_id());
    }
}
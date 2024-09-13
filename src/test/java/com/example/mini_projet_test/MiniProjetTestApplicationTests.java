package com.example.mini_projet_test;

import com.example.mini_projet_test.controller.ProductController;
import com.example.mini_projet_test.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MiniProjetTestApplicationTests {

	@Autowired
	private ProductController productController;

	@Autowired
	private ProductService productService;

	@Test
	void testApplicationContext() {
		Assertions.assertNotNull(productController);
		Assertions.assertNotNull(productService);
	}

}

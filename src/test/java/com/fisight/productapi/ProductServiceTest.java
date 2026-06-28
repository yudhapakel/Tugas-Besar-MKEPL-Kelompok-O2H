package com.fisight.productapi;

import com.fisight.productapi.model.Product;
import com.fisight.productapi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    // Test valid product creation and retrieval
    @Test
    void testSaveAndGetProduct() {
        Product product = new Product("Ikan Mujair", 25000.0, 50);
        Product saved = productService.saveProduct(product);

        assertNotNull(saved.getId());
        assertEquals("Ikan Mujair", saved.getName());

        List<Product> products = productService.getAllProducts();
        assertFalse(products.isEmpty());
    }

    @Test
    void testSaveProductWithEmptyNameShouldThrowException() {
        Product product = new Product("", 10000.0, 10);
        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(product));
    }

    @Test
    void testSaveProductWithNegativePriceShouldThrowException() {
        Product product = new Product("Ikan Gurame", -5000.0, 10);
        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(product));
    }

    @Test
    void testGetProductById() {
        Product product = new Product("Ikan Nila", 15000.0, 30);
        Product saved = productService.saveProduct(product);

        java.util.Optional<Product> found = productService.getProductById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Ikan Nila", found.get().getName());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product("Ikan Patin", 20000.0, 20);
        Product saved = productService.saveProduct(product);

        productService.deleteProduct(saved.getId());
        java.util.Optional<Product> found = productService.getProductById(saved.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void testGetAllProductsShouldNotReturnNull() {
        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
    }
}

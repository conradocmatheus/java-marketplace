package app.marketplace.service;

import app.marketplace.entity.Product;
import app.marketplace.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    private List<Product> products;

    @BeforeEach
    void setup() {
        // Initialize test data
        products = new ArrayList<>();
        products.add(new Product(1L, "Product A", 100.0, Collections.emptyList()));
        products.add(new Product(2L, "Product B", 200.0, Collections.emptyList()));
        products.add(new Product(3L, "Product C", 300.0, Collections.emptyList()));

        // Mock repository methods
        when(productRepository.findAll()).thenReturn(products);
        when(productRepository.findById(1L)).thenReturn(Optional.of(products.get(0)));
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        when(productRepository.findByNameStartingWith("Product A")).thenReturn(List.of(products.get(0)));
        when(productRepository.findByNameStartingWith("NonExisting")).thenReturn(Collections.emptyList());
        when(productRepository.findByPriceGreaterThanEqual(150.0)).thenReturn(List.of(products.get(1), products.get(2)));
        when(productRepository.findTopProducts()).thenReturn(products);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.existsById(999L)).thenReturn(false);
    }

    @Test
    @DisplayName("Save a Product")
    void saveProduct() {
        // Test saving a new product
        Product product = new Product(null, "New Product", 150.0, Collections.emptyList());
        when(productRepository.save(any(Product.class))).thenReturn(product);
        String result = productService.save(product);
        assertEquals("Product: New Product successfully saved", result);
    }

    @Test
    @DisplayName("Update a Product")
    void updateProduct() {
        // Test updating an existing product
        Product updatedProduct = new Product(1L, "Updated Product", 100.0, Collections.emptyList());
        when(productRepository.existsById(1L)).thenReturn(true);
        String result = productService.update(updatedProduct, 1L);
        assertEquals("Updated Product successfully updated", result);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Update a Non-Existing Product")
    void updateNonExistingProduct() {
        // Test updating a non-existing product
        Product updatedProduct = new Product(999L, "Non Existing Product", 150.0, Collections.emptyList());
        when(productRepository.existsById(999L)).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.update(updatedProduct, 999L);
        });
        assertEquals("Product with ID: 999 not found", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Delete an Existing Product")
    void deleteProductSuccessfully() {
        // Test deleting an existing product
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);
        String result = productService.delete(productId);
        assertEquals("Product with id: 1 deleted", result);
        verify(productRepository).deleteById(productId);
    }

    @Test
    @DisplayName("Delete a Non-Existing Product")
    void deleteNonExistingProduct() {
        // Test deleting a non-existing product
        Long productId = 999L;
        when(productRepository.existsById(productId)).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.delete(productId);
        });
        assertEquals("Product with ID: 999 not found", exception.getMessage());
        verify(productRepository, never()).deleteById(productId);
    }

    @Test
    @DisplayName("List All Products")
    void listAllProducts() {
        // Test listing all products
        List<Product> result = productService.listAll();
        assertNotNull(result);
        assertEquals(products, result);
    }

    @Test
    @DisplayName("Find Product by ID")
    void findProductByIdSuccess() {
        // Test finding a product by a valid ID
        Long productId = 1L;
        Product result = productService.findById(productId);
        assertNotNull(result);
        assertEquals("Product A", result.getName());
    }

    @Test
    @DisplayName("Find Product by Non-Existing ID")
    void findProductByIdNotFound() {
        // Test finding a product by a non-existing ID
        Long productId = 999L;
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.findById(productId);
        });
        assertEquals("Product with ID: 999 not found", exception.getMessage());
    }

    @Test
    @DisplayName("Find Products by Name Starting With")
    void findProductsByNameStartingWith() {
        // Test finding products by a valid name prefix
        String namePrefix = "Product A";
        List<Product> result = productService.findNameStartingWith(namePrefix);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Product A", result.get(0).getName());
    }

    @Test
    @DisplayName("Find Products by Non-Existing Name Prefix")
    void findProductsByNonExistingNameStartingWith() {
        // Test finding products by a non-existing name prefix
        String namePrefix = "NonExisting";
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.findNameStartingWith(namePrefix);
        });
        assertEquals("No products found with name: NonExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Find Products by Price Greater Than or Equal")
    void findProductsByPriceGreaterThanOrEqual() {
        // Test finding products with price greater than or equal to a value
        Double price = 150.0;
        List<Product> result = productService.findByPriceGreaterThanOrEqual(price);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.getPrice() >= price));
    }

    @Test
    @DisplayName("Find Most Sold Products")
    void findMostSoldProducts() {
        // Test finding top sold products
        List<Product> result = productService.findMostSoldProducts();
        assertNotNull(result);
        assertEquals(products, result);
    }
}

package app.marketplace.service;

import app.marketplace.entity.Customer;
import app.marketplace.entity.Employee;
import app.marketplace.entity.Product;
import app.marketplace.entity.Sale;
import app.marketplace.repository.CustomerRepository;
import app.marketplace.repository.EmployeeRepository;
import app.marketplace.repository.ProductRepository;
import app.marketplace.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
public class SaleServiceTest {

    @Autowired
    private SaleService saleService;

    @MockBean
    private SaleRepository saleRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private ProductRepository productRepository;

    private List<Sale> sales;
    private List<Product> products;
    private List<Customer> customers;
    private List<Employee> employees;

    @BeforeEach
    void setup() {
        // Initialize test data
        products = new ArrayList<>();
        products.add(new Product(1L, "Product A", 100.0, Collections.emptyList()));
        products.add(new Product(2L, "Product B", 200.0, Collections.emptyList()));

        customers = new ArrayList<>();
        customers.add(new Customer(1L, "Customer A", "12345678900", 30, "(12) 34567-8901", Collections.emptyList()));

        employees = new ArrayList<>();
        employees.add(new Employee(1L, "Employee A", "1234", Collections.emptyList()));

        sales = new ArrayList<>();
        sales.add(new Sale(1L, "Address 1", 300.0, customers.get(0), employees.get(0), products));

        // Mock repository methods
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(employeeRepository.existsById(1L)).thenReturn(true);
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.existsById(2L)).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(products.get(0)));
        when(productRepository.findById(2L)).thenReturn(Optional.of(products.get(1)));
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        when(saleRepository.findAll()).thenReturn(sales);
        when(saleRepository.findById(1L)).thenReturn(Optional.of(sales.get(0)));
        when(saleRepository.findById(999L)).thenReturn(Optional.empty());
        when(saleRepository.findByShippingAddressStartingWith("Address 1")).thenReturn(sales);
        when(saleRepository.findByCustomerId(1L)).thenReturn(sales);
        when(saleRepository.findSalesByEmployeeId(1L)).thenReturn(sales);
        when(saleRepository.save(any(Sale.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(saleRepository.existsById(1L)).thenReturn(true);
        when(saleRepository.existsById(999L)).thenReturn(false);
    }

    @Test
    @DisplayName("Save a Sale")
    void saveSale() {
        // Test saving a new sale
        Sale sale = new Sale(null, "New Address", 0.0, customers.get(0), employees.get(0), products);
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(products.get(0)));
        String result = saleService.save(sale);
        assertEquals("Sale successfully saved with ID: " + sale.getId(), result);
    }

    @Test
    @DisplayName("Save Sale with Non-Existing Customer")
    void saveSaleWithNonExistingCustomer() {
        // Test saving a sale with a non-existing customer
        Sale sale = new Sale(null, "New Address", 0.0, new Customer(999L, "Non-Existing", "00000000000",19, "(34) 12345-6789", Collections.emptyList()), employees.get(0), products);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            saleService.save(sale);
        });
        assertEquals("Customer not found with ID: 999", exception.getMessage());
    }

    @Test
    @DisplayName("Delete an Existing Sale")
    void deleteSale() {
        // Test deleting an existing sale
        Long saleId = 1L;
        String result = saleService.delete(saleId);
        assertEquals("Sale with id: 1 deleted", result);
        verify(saleRepository).deleteById(saleId);
    }

    @Test
    @DisplayName("Delete a Non-Existing Sale")
    void deleteNonExistingSale() {
        // Test deleting a non-existing sale
        Long saleId = 999L;
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            saleService.delete(saleId);
        });
        assertEquals("Sale with ID: 999 not found", exception.getMessage());
        verify(saleRepository, never()).deleteById(saleId);
    }

    @Test
    @DisplayName("List All Sales")
    void listAllSales() {
        // Test listing all sales
        List<Sale> result = saleService.listAll();
        assertNotNull(result);
        assertEquals(sales, result);
    }

    @Test
    @DisplayName("Find Sale by ID")
    void findSaleByIdSuccess() {
        // Test finding a sale by a valid ID
        Long saleId = 1L;
        Sale result = saleService.findById(saleId);
        assertNotNull(result);
        assertEquals("Address 1", result.getShippingAddress());
    }

    @Test
    @DisplayName("Find Sale by Non-Existing ID")
    void findSaleByIdNotFound() {
        // Test finding a sale by a non-existing ID
        Long saleId = 999L;
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            saleService.findById(saleId);
        });
        assertEquals("Sale with ID: 999 not found", exception.getMessage());
    }

    @Test
    @DisplayName("Find Sales by Shipping Address")
    void findSalesByShippingAddress() {
        // Test finding sales by a valid shipping address
        String address = "Address 1";
        List<Sale> result = saleService.findByShippingAddressStartingWith(address);
        assertNotNull(result);
        assertEquals(sales, result);
    }

    @Test
    @DisplayName("Find Sales by Empty Shipping Address")
    void findSalesByEmptyShippingAddress() {
        // Test finding sales by an empty shipping address
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            saleService.findByShippingAddressStartingWith("");
        });
        assertEquals("Shipping address cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Find Sales by Employee ID")
    void findSalesByEmployeeId() {
        // Test finding sales by a valid employee ID
        Long employeeId = 1L;
        List<Sale> result = saleService.findSalesByEmployeeId(employeeId);
        assertNotNull(result);
        assertEquals(sales, result);
    }

    @Test
    @DisplayName("Find Sales by Customer ID")
    void findSalesByCustomerId() {
        // Test finding sales by a valid customer ID
        Long customerId = 1L;
        List<Sale> result = saleService.findSalesByCustomerId(customerId);
        assertNotNull(result);
        assertEquals(sales, result);
    }

    @Test
    @DisplayName("Check Sale Existence by ID")
    void existsById() {
        // Test checking if a sale exists by ID
        Long saleId = 1L;
        boolean exists = saleService.existsById(saleId);
        assertTrue(exists);
    }

    @Test
    @DisplayName("Check Non-Existing Sale Existence by ID")
    void existsByNonExistingId() {
        // Test checking if a non-existing sale exists by ID
        Long saleId = 999L;
        boolean exists = saleService.existsById(saleId);
        assertFalse(exists);
    }
}

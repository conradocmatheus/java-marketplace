package app.marketplace.service;

import app.marketplace.entity.Customer;
import app.marketplace.repository.CustomerRepository;
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
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;

    private List<Customer> customers;

    @BeforeEach
    void setup() {
        // Initialize a list of customers
        customers = new ArrayList<>();
        customers.add(new Customer(1L, "João Silva", "12345678909", 25, "45991234567", Collections.emptyList()));
        customers.add(new Customer(2L, "Maria Oliveira", "23456789012", 30, "45992345678", Collections.emptyList()));
        customers.add(new Customer(3L, "Pedro Santos", "34567890123", 40, "45993456789", Collections.emptyList()));
        customers.add(new Customer(4L, "Ana Souza", "45678901234", 22, "45994567890", Collections.emptyList()));
        customers.add(new Customer(5L, "Carlos Pereira", "56789012345", 35, "45995678901", Collections.emptyList()));

        // Mock repository responses
        when(customerRepository.findAll()).thenReturn(customers);
        when(customerRepository.findById(3L)).thenReturn(Optional.of(customers.get(2)));
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.existsById(999L)).thenReturn(false);
    }

    @Test
    @DisplayName("Save a customer")
    void saveCustomer() {
        // Create a new Customer
        Customer customer = new Customer(1L, "Joao pe", "15570528911", 19, "45991214710", Collections.emptyList());

        // Act
        Customer result = customerService.save(customer);

        // Assert
        assertEquals(customer, result);
    }

    @Test
    @DisplayName("Update an existing customer")
    void updateCustomer() {
        // Prepare updated customer details
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(3L); // Existing client ID
        updatedCustomer.setName("Pedro Santos Updated");
        updatedCustomer.setCpf("34567890123");
        updatedCustomer.setAge(41);
        updatedCustomer.setPhoneNumber("45993456789");
        updatedCustomer.setSales(Collections.emptyList());

        // Act
        String resultMessage = customerService.update(updatedCustomer, 3L);

        // Assert
        assertEquals("Customer successfully updated", resultMessage);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Update a non-existing customer")
    void updateNonExistingCustomer() {
        // Prepare updated customer details
        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Non Existing Customer");
        updatedCustomer.setCpf("00000000000");
        updatedCustomer.setAge(30);
        updatedCustomer.setPhoneNumber("0000000000");
        updatedCustomer.setSales(Collections.emptyList());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            customerService.update(updatedCustomer, 999L);
        });
        assertEquals("Customer not found", exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("List all customers")
    void listCustomers() {
        // Act
        List<Customer> result = customerService.listAll();

        // Assert
        assertEquals(customers, result);
    }

    @Test
    @DisplayName("Check if customer exists by ID")
    void customerExistsById() {
        // Act
        boolean result = customerService.existsById(1L);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Delete an existing customer")
    void deleteCustomerSuccessfully() {
        // Act
        String result = customerService.delete(1L);

        // Assert
        assertEquals("Customer with id: 1 deleted", result);
        verify(customerRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Delete a non-existing customer")
    void deleteNonExistingCustomer() {
        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            customerService.delete(999L);
        });
        assertEquals("Customer with ID: 999 not found", exception.getMessage());
        verify(customerRepository, never()).deleteById(999L);
    }
}

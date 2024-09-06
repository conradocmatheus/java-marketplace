package app.marketplace.service;

import app.marketplace.entity.Customer;
import app.marketplace.repository.CustomerRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;

    private List<Customer> customers;

    @BeforeEach
    void setup() {
        customers = new ArrayList<>();

        customers.add(new Customer(1L, "João Silva", "12345678909", 25, "45991234567", Collections.emptyList()));
        customers.add(new Customer(2L, "Maria Oliveira", "23456789012", 30, "45992345678", Collections.emptyList()));
        customers.add(new Customer(3L, "Pedro Santos", "34567890123", 40, "45993456789", Collections.emptyList()));
        customers.add(new Customer(4L, "Ana Souza", "45678901234", 22, "45994567890", Collections.emptyList()));
        customers.add(new Customer(5L, "Carlos Pereira", "56789012345", 35, "45995678901", Collections.emptyList()));

        when(customerRepository.findAll()).thenReturn(customers);

        when(customerRepository.findById(3L)).thenReturn(Optional.of(customers.get(2)));

        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @DisplayName("Saving a customer")
    void saveCustomerTest() {

        // Creates a new Customer
        Customer customer = new Customer(1L, "Joao pe", "15570528911", 19, "45991214710", Collections.emptyList());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        var result = customerService.save(customer);
        assertEquals(customer, result);
    }

    @Test
    @DisplayName("Update a customer")
    void updateCustomerTest() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(3L); // Existing client ID
        updatedCustomer.setName("Pedro Santos Updated");
        updatedCustomer.setCpf("34567890123");
        updatedCustomer.setAge(41); // New age
        updatedCustomer.setPhoneNumber("45993456789");
        updatedCustomer.setSales(Collections.emptyList());

        // Calls update method from service and assigns it to resulMessage
        String resultMessage = customerService.update(updatedCustomer, 3L);

        // Verifies if the message is the same as expected
        assertEquals("Customer successfully updated", resultMessage);

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("List all customers")
    void listCustomersTest() {
        List<Customer> result = customerService.listAll();
        assertEquals(customers, result);
    }
}


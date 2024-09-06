package app.marketplace.service;

import app.marketplace.entity.Customer;
import app.marketplace.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;

    @BeforeEach
    void setup(){

    }

    @Test
    void saveCustomerTest(){

        Customer customer = new Customer();

        when(customerRepository.save(customer)).thenReturn(customer);

        customer.setId(1L);
        customer.setName("Joao pe");
        customer.setCpf("15570528911");
        customer.setAge(19);
        customer.setPhoneNumber("45991214710");
        customer.setSales(Collections.emptyList());

        var result = customerService.save(customer);

        assertEquals(customer, result);
    }

    @Test
    void listCustomersTest(){
        List<Customer> customers = new ArrayList<>();

        Customer customer1 = new Customer(1L, "Jota pe", "15570528911", 19, "45991214710", Collections.emptyList());
        Customer customer2 = new Customer(2L, "Jota Pedro", "15570528911", 19, "45991214710", Collections.emptyList());

        when(customerRepository.findAll()).thenReturn(customers);


    }
}

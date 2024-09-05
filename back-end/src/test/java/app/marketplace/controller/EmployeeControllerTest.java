package app.marketplace.controller;

import app.marketplace.entity.Employee;
import app.marketplace.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    void setup() {
        // Creates the Employee object
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Matheus");
        employee.setRegistrationNumber("505176");
        employee.setSales(Collections.emptyList());

        // FIND BY ID
        when(employeeService.findById(1L)).thenReturn(employee);
    }

    @Test
    void testFindById() {
        // Calls the controller method
        ResponseEntity<?> response = employeeController.findById(1L);

        // Verifies the response (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifies the response body
        Employee employee = (Employee) response.getBody();
        assertEquals("Matheus", employee.getName());
        assertEquals("505176", employee.getRegistrationNumber());

        // Verifies if the id is correct
        assertEquals(1L, employee.getId());
    }
}

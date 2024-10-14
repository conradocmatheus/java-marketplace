package app.marketplace.service;

import app.marketplace.entity.Employee;
import app.marketplace.repository.EmployeeRepository;
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
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    private List<Employee> employees;

    @BeforeEach
    void setup() {
        employees = new ArrayList<>();
        employees.add(new Employee(1L, "John Doe", "REG123", Collections.emptyList()));
        employees.add(new Employee(2L, "Jane Smith", "REG456", Collections.emptyList()));
        employees.add(new Employee(3L, "Emily Davis", "REG789", Collections.emptyList()));

        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employees.get(0)));
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());
        when(employeeRepository.findByRegistrationNumber("REG123")).thenReturn(employees.get(0));
        when(employeeRepository.findByRegistrationNumber("NON_EXISTING")).thenReturn(null);
        when(employeeRepository.findByNameStartingWith("John")).thenReturn(List.of(employees.get(0)));
        when(employeeRepository.findByNameStartingWith("NonExisting")).thenReturn(Collections.emptyList());
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(employeeRepository.existsById(1L)).thenReturn(true);
        when(employeeRepository.existsById(999L)).thenReturn(false);
        when(employeeRepository.findTopEmployeesBySalesCount()).thenReturn(employees);
    }

    @Test
    @DisplayName("Save an Employee")
    void saveEmployee() {
        Employee employee = new Employee(null, "New Employee", "REG999", Collections.emptyList());
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        String result = employeeService.save(employee);
        assertEquals("Employee: New Employee, successfully saved", result);
    }

    @Test
    @DisplayName("Update an Employee")
    void updateEmployee() {
        Employee updatedEmployee = new Employee(1L, "Updated Name", "REG123", Collections.emptyList());
        when(employeeRepository.existsById(1L)).thenReturn(true);
        String result = employeeService.update(updatedEmployee, 1L);
        assertEquals("Updated Name successfully updated", result);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    @DisplayName("Update a Non-Existing Employee")
    void updateNonExistingEmployee() {
        Employee updatedEmployee = new Employee(999L, "Non Existing", "NON_EXISTING", Collections.emptyList());
        when(employeeRepository.existsById(999L)).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            employeeService.update(updatedEmployee, 999L);
        });
        assertEquals("Employee with ID: 999 not found", exception.getMessage());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Delete an Existing Employee")
    void deleteEmployeeSuccessfully() {
        Long employeeId = 1L;
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        String result = employeeService.delete(employeeId);
        assertEquals("Employee with id: 1 deleted", result);
        verify(employeeRepository).deleteById(employeeId);
    }

    @Test
    @DisplayName("Delete a Non-Existing Employee")
    void deleteNonExistingEmployee() {
        Long employeeId = 999L;
        when(employeeRepository.existsById(employeeId)).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            employeeService.delete(employeeId);
        });
        assertEquals("Employee with ID: 999 not found", exception.getMessage());
        verify(employeeRepository, never()).deleteById(employeeId);
    }

    @Test
    @DisplayName("List All Employees")
    void listAllEmployees() {
        List<Employee> result = employeeService.listAll();
        assertNotNull(result);
        assertEquals(employees, result);
    }

    @Test
    @DisplayName("Find Employee by ID")
    void findEmployeeByIdSuccess() {
        Long employeeId = 1L;
        Employee result = employeeService.findById(employeeId);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    @DisplayName("Find Employee by Non-Existing ID")
    void findEmployeeByIdNotFound() {
        Long employeeId = 999L;
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            employeeService.findById(employeeId);
        });
        assertEquals("Employee with ID: 999 not found", exception.getMessage());
    }

    @Test
    @DisplayName("Find Employees by First Name")
    void findEmployeesByFirstName() {
        String firstName = "John";
        List<Employee> result = employeeService.findAllByFirstName(firstName);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    @DisplayName("Find Employees by Non-Existing First Name")
    void findEmployeesByNonExistingFirstName() {
        String firstName = "NonExisting";
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            employeeService.findAllByFirstName(firstName);
        });
        assertEquals("No employees found with first name: " + firstName, exception.getMessage());
    }

    @Test
    @DisplayName("Find Employee by Registration Number")
    void findEmployeeByRegistrationNumber() {
        String registrationNumber = "REG123";
        Employee result = employeeService.findByRegistrationNumber(registrationNumber);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    @DisplayName("Find Employee by Non-Existing Registration Number")
    void findEmployeeByNonExistingRegistrationNumber() {
        String registrationNumber = "NON_EXISTING";
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            employeeService.findByRegistrationNumber(registrationNumber);
        });
        assertEquals("Employee with Registration Number: NON_EXISTING not found", exception.getMessage());
    }

    @Test
    @DisplayName("Find Top Employees by Sales Count")
    void findTopEmployeesBySalesCount() {
        List<Employee> result = employeeService.findTopEmployeesBySalesCount();
        assertNotNull(result);
        assertEquals(employees, result);
    }
}

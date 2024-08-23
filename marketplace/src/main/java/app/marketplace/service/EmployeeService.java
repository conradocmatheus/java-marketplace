package app.marketplace.service;

import app.marketplace.entity.Employee;
import app.marketplace.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // POST
    // Save an Employee
    public String save(Employee employee){
        this.employeeRepository.save(employee);
        return "Employee: " + employee.getName() + ", successfully saved";
    }

    // PUT
    // Update an Employee
    public String update(Employee employee, Long id){
        if (employeeRepository.existsById(id)){
            employee.setId(id);
            employeeRepository.save(employee);
            return employee.getName() + " successfully updated";
        } else {
            throw new EntityNotFoundException("Employee with ID: " + id + " not found");
        }
    }

    // DELETE
    //  an Employee
    public String delete(Long id){
        if (employeeRepository.existsById(id)) {
            this.employeeRepository.deleteById(id);
            return "Employee with id: " + id + " deleted";
        } else {
            throw new EntityNotFoundException("Employee with ID: " + id + " not found");
        }
    }

    // GET
    // List all Employees
    public List<Employee> listAll(){
        return this.employeeRepository.findAll();
    }

    // GET
    // Find Employee by ID
    public Employee findById(Long id){
        if (employeeRepository.existsById(id)){
            return this.employeeRepository.findById(id).get();
        } else {
            throw new EntityNotFoundException("Employee with ID: " + id + " not found");
        }
    }

    // Find Employee by Name
    public List<Employee> findAllByFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        List<Employee> employees = employeeRepository.findByNameStartingWith(firstName);
        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No employees found with first name: " + firstName);
        }
        return employees;
    }

    // Find Employee by Registration Number
    public Employee findByRegistrationNumber(String registrationNumber){
        if (registrationNumber == null || registrationNumber.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        Employee employee = employeeRepository.findByRegistrationNumber(registrationNumber);
        if (employee == null) {
            throw new EntityNotFoundException("Employee with Registration Number: " + registrationNumber + " not found");
        }
        return employee;
    }

    public List<Employee> findTopEmployeesBySalesCount() {
        return employeeRepository.findTopEmployeesBySalesCount();
    }

    // Verify Employee existence by ID
    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }
}

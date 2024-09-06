package app.marketplace.controller;

import app.marketplace.entity.Employee;
import app.marketplace.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody Employee employee) {
        try {
            String message = this.employeeService.save(employee);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody Employee employee, @PathVariable Long id) {
        try {
            String message = employeeService.update(employee, id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String message = this.employeeService.delete(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Employee>> listAll() {
        try {
            List<Employee> list = this.employeeService.listAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Employee employee = employeeService.findById(id);
            return ResponseEntity.ok(employee);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/by-first-name")
    public ResponseEntity<?> findAllByFirstName(@RequestParam String firstName) {
        try {
            List<Employee> employees = employeeService.findAllByFirstName(firstName);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByRegistrationNumber/{registrationNumber}")
    public ResponseEntity<?> findByRegistrationNumber(@PathVariable String registrationNumber) {
        try {
            Employee employee = employeeService.findByRegistrationNumber(registrationNumber);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/top-by-sales")
    public ResponseEntity<List<Employee>> getTopEmployeesBySalesCount() {
        List<Employee> employees = employeeService.findTopEmployeesBySalesCount();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}

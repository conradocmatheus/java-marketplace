package app.clotheStore.service;

import app.clotheStore.entity.Employee;
import app.clotheStore.repository.EmployeeRepository;
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
        employee.setId(id);
        employeeRepository.save(employee);
        return employee.getName() + " successfully updated";
    }

    // DELETE
    //  an Employee
    public String delete(Long id){
        this.employeeRepository.deleteById(id);
        return "Employee with id: " + id + " deleted";
    }

    // GET
    // List all Employees
    public List<Employee> listAll(){
        return this.employeeRepository.findAll();
    }

    // GET
    // Find Employee by ID
    public Employee findById(Long id){
        return this.employeeRepository.findById(id).get();
    }
}

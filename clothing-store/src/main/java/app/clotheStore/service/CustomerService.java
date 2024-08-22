package app.clotheStore.service;

import app.clotheStore.entity.Customer;
import app.clotheStore.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // POST
    // Save a Customer
    public String save(Customer customer){
        this.customerRepository.save(customer);
        return "Customer: " + customer.getName() + ", successfully saved";
    }

    // PUT
    // Update a Customer
    public String update(Customer customer, Long id){
        if (customerRepository.existsById(id)){
            customer.setId(id);
            customerRepository.save(customer);
            return (customer.getName() + " successfully updated");
        } else {
            throw new EntityNotFoundException("Customer with ID: " + id + " not found");
        }
    }

    // DELETE
    //  a Customer
    public String delete(Long id){
        if (customerRepository.existsById(id)){
            this.customerRepository.deleteById(id);
            return "Customer with id: " + id + " deleted";
        } else {
            throw new EntityNotFoundException("Customer with ID: " + id + " not found");
        }
    }

    // GET
    // List all Clients
    public List<Customer> listAll(){
        return this.customerRepository.findAll();
    }

    // GET
    // Find Customer by ID
    public Customer findById(Long id){
        if (customerRepository.existsById(id)){
            return this.customerRepository.findById(id).get();
        } else {
            throw new EntityNotFoundException("Customer with ID: " + id + " not found");
        }
    }

    // Find Customer by Name
    public List<Customer> findAllByFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        List<Customer> customers = customerRepository.findByNameStartingWith(firstName);
        if (customers.isEmpty()) {
            throw new EntityNotFoundException("No customers found with first name: " + firstName);
        }
        return customers;
    }

    // Find Customer by CPF
    public Customer findByCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF cannot be null or empty");
        }
        Customer customer = customerRepository.findByCpf(cpf);
        if (customer == null) {
            throw new EntityNotFoundException("Customer with CPF: " + cpf + " not found");
        }
        return customer;
    }

    // Verify Customer existence by ID
    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

}

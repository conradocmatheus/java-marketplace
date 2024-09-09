package app.marketplace.service;

import app.marketplace.entity.Customer;
import app.marketplace.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // POST
    // Save a Customer
    public Customer save(Customer customer) {
            return customerRepository.save(customer);
    }

    // PUT
    // Update a Customer
    public String update(Customer updatedCustomer, Long id) {
        // Verifies customer existence
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // Update customer details
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setCpf(updatedCustomer.getCpf());
        existingCustomer.setAge(updatedCustomer.getAge());
        existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        existingCustomer.setSales(updatedCustomer.getSales());

        // Saves the updated customer
        customerRepository.save(existingCustomer);
        return "Customer successfully updated";
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
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID: " + id + " not found"));
    }

    // GET
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

    // GET
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

    // GET
    // Find top Customer by PurchaseCount
    public List<Customer> findTopCustomersByPurchaseCount() {
        return customerRepository.findTopCustomersByPurchaseCount();
    }

    // Verify Customer existence by ID
    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

}

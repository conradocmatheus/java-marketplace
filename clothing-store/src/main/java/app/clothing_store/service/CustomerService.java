package app.clothing_store.service;

import app.clothing_store.entity.Customer;
import app.clothing_store.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        customer.setId(id);
        customerRepository.save(customer);
        return (customer.getName() + " successfully updated");
    }

    // DELETE
    //  a Customer
    public String delete(Long id){
        this.customerRepository.deleteById(id);
        return "Customer with id: " + id + " deleted";
    }

    // GET
    // List all Clients
    public List<Customer> listAll(){
        return this.customerRepository.findAll();
    }

    // GET
    // Find Customer by ID
    public Customer findById(Long id){
        return this.customerRepository.findById(id).get();
    }
}

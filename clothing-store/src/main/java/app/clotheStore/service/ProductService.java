package app.clotheStore.service;

import app.clotheStore.entity.Product;
import app.clotheStore.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // POST
    // Save a Product
    public String save(Product product){
        this.productRepository.save(product);
        return "Product: " + product.getName() + " successfully saved";
    }

    // PUT
    // Update a Product
    public String update(Product product, Long id){
        if (productRepository.existsById(id)){
            product.setId(id);
            productRepository.save(product);
            return (product.getName() + " successfully updated");
        } else {
            throw new EntityNotFoundException("Product with ID: " + id + " not found");
        }
    }

    // DELETE
    //  a Product
    public String delete(Long id){
        if (productRepository.existsById(id)){
            this.productRepository.deleteById(id);
            return "Product with id: " + id + " deleted";
        } else {
            throw new EntityNotFoundException("Product with ID: " + id + " not found");
        }
    }

    // GET
    // List all Products
    public List<Product> listAll(){
        return this.productRepository.findAll();
    }

    // GET
    // Find Product by ID
    public Product findById(Long id){
        if (productRepository.existsById(id)){
            return this.productRepository.findById(id).get();
        } else {
            throw new EntityNotFoundException("Product with ID: " + id + " not found");
        }
    }

    // Verify Product existence by ID
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }
}

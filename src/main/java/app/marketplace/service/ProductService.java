package app.marketplace.service;

import app.marketplace.entity.Product;
import app.marketplace.repository.ProductRepository;
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

    public List<Product> findNameStartingWith(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        List<Product> products = productRepository.findByNameStartingWith(name);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No products found with name: " + name);
        }
        return products;
    }

    public List<Product> findByPriceGreaterThanOrEqual(Double price) {
        return productRepository.findByPriceGreaterThanEqual(price);
    }

    public List<Product> findMostSoldProducts() {
        return productRepository.findTopProducts();
    }

    // Verify Product existence by ID
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }
}

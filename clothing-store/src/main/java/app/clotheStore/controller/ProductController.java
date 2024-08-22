package app.clotheStore.controller;

import app.clotheStore.entity.Employee;
import app.clotheStore.entity.Product;
import app.clotheStore.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Product product) {
        try {
            String message = this.productService.save(product);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Product product, @PathVariable Long id) {
        try {
            String message = productService.update(product, id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            String message = this.productService.delete(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Product>> listAll() {
        try {
            List<Product> list = this.productService.listAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            Product product = this.productService.findById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by-name")
    public ResponseEntity<?> findNameStartingWith(@RequestParam String name) {
        try {
            List<Product> products = productService.findNameStartingWith(name);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/by-price-greater-than-or-equal")
    public ResponseEntity<List<Product>> findByPriceGreaterThanOrEqual(@RequestParam Double price) {
        List<Product> products = productService.findByPriceGreaterThanOrEqual(price);
        return ResponseEntity.ok(products);
    }
}

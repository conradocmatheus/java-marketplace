package app.marketplace.controller;

import app.marketplace.entity.Sale;
import app.marketplace.service.SaleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody Sale sale) {
        try {
            String message = this.saleService.save(sale);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            String message = this.saleService.delete(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Sale>> listAll() {
        try {
            List<Sale> list = this.saleService.listAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            Sale sale = this.saleService.findById(id);
            return new ResponseEntity<>(sale, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by-shipping-address")
    public ResponseEntity<?>  findByShippingAddressStartingWith(@RequestParam String shippingAddress) {
        try {
            List<Sale> sales = saleService.findByShippingAddressStartingWith(shippingAddress);
            return ResponseEntity.ok(sales);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-employee-id/{employeeId}")
    public ResponseEntity<List<Sale>> findSalesByEmployeeId(@PathVariable Long employeeId) {
        List<Sale> sales = saleService.findSalesByEmployeeId(employeeId);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/by-customer-id/{customerId}")
    public ResponseEntity<?> findSalesByCustomerId(@PathVariable Long customerId) {
        try {
            List<Sale> sales = saleService.findSalesByCustomerId(customerId);
            return ResponseEntity.ok(sales);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

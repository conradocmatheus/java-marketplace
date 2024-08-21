package app.clotheStore.service;

import app.clotheStore.entity.Sale;
import app.clotheStore.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    // POST
    // Save a Sale
    public String save(Sale sale){
        this.saleRepository.save(sale);
        return "Sale successfully saved with ID: " + sale.getId();
    }

    // DELETE
    //  a Sale
    public String delete(Long id){
        if (saleRepository.existsById(id)){
            this.saleRepository.deleteById(id);
            return "Customer with id: " + id + " deleted";
        } else {
            throw new EntityNotFoundException("Customer with ID: " + id + " not found");
        }
    }

    // GET
    // List all Sales
    public List<Sale> listAll(){
        return this.saleRepository.findAll();
    }

    // GET
    // Find Sale by ID
    public Sale findById(Long id){
        if (saleRepository.existsById(id)){
            return this.saleRepository.findById(id).get();
        } else {
            throw new EntityNotFoundException("Sale with ID: " + id + " not found");
        }
    }

    // Verify Sale existence by ID
    public boolean existsById(Long id) {
        return saleRepository.existsById(id);
    }
}

package app.clotheStore.service;

import app.clotheStore.entity.Sale;
import app.clotheStore.repository.SaleRepository;
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

    // PUT
    // Update a Sale
    public String update(Sale sale, Long id){
        sale.setId(id);
        saleRepository.save(sale);
        return "Sale with ID: " + id + " successfully updated";
    }

    // DELETE
    //  a Sale
    public String delete(Long id){
        this.saleRepository.deleteById(id);
        return "Sale with ID: " + id + " deleted";
    }

    // GET
    // List all Sales
    public List<Sale> listAll(){
        return this.saleRepository.findAll();
    }

    // GET
    // Find Sale by ID
    public Sale findById(Long id){
        return this.saleRepository.findById(id).get();
    }
}

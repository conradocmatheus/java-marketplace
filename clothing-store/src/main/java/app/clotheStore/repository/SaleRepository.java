package app.clotheStore.repository;

import app.clotheStore.entity.Customer;
import app.clotheStore.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByShippingAddressStartingWith(String shippingAddress);

    // Personalized Method - Find customer by total spent
    @Query("SELECT s.customer FROM Sale s GROUP BY s.customer ORDER BY SUM(s.totalValue) DESC")
    Customer findTopCustomerByTotalSpent();
}

package app.clotheStore.repository;

import app.clotheStore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Personalized Method - Find customer by total spent
    @Query("SELECT s.customer FROM Sale s GROUP BY s.customer ORDER BY SUM(s.totalValue) DESC")
    Customer findTopCustomerByTotalSpent();
}

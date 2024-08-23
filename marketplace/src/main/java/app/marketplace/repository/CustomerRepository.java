package app.marketplace.repository;

import app.marketplace.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByCpf(String cpf);

    List<Customer> findByNameStartingWith(String firstName);

    @Query("SELECT c FROM Customer c JOIN c.sales s GROUP BY c ORDER BY COUNT(s) DESC")
    List<Customer> findTopCustomersByPurchaseCount();
}

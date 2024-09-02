package app.marketplace.repository;

import app.marketplace.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameStartingWith(String name);

    List<Product> findByPriceGreaterThanEqual(Double price);

    @Query("SELECT p FROM Product p JOIN p.sales s GROUP BY p ORDER BY COUNT(s) DESC")
    List<Product> findTopProducts();
}

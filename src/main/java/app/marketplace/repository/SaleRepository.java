package app.marketplace.repository;

import app.marketplace.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByShippingAddressStartingWith(String shippingAddress);

    @Query("SELECT s FROM Sale s WHERE s.employee.id = :employeeId")
    List<Sale> findSalesByEmployeeId(@Param("employeeId") Long employeeId);
}

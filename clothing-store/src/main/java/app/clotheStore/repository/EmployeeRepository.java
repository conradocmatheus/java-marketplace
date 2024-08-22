package app.clotheStore.repository;

import app.clotheStore.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameStartingWith(String firstName);

    Employee findByRegistrationNumber(String registrationNumber);

    @Query("SELECT e FROM Employee e JOIN e.sales s GROUP BY e ORDER BY COUNT(s) DESC")
    List<Employee> findTopEmployeesBySalesCount();
}

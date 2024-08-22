package app.clotheStore.repository;

import app.clotheStore.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameStartingWith(String firstName);

    Employee findByRegistrationNumber(String registrationNumber);

    // Falta uma query personalizada
}

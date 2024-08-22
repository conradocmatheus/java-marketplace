package app.clotheStore.repository;

import app.clotheStore.entity.Customer;
import app.clotheStore.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Customer findByName(String name);

    Customer findByRegistrationNumber(String registrationNumber);

    // Falta uma query personalizada
}

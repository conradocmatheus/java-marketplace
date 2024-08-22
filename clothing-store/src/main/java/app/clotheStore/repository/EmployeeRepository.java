package app.clotheStore.repository;

import app.clotheStore.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByName(String name);

    Employee findByRegistrationNumber(String registrationNumber);

    // Falta uma query personalizada
}

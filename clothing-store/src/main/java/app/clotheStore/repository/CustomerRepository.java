package app.clotheStore.repository;

import app.clotheStore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByCpf(String cpf);

    List<Customer> findByNameStartingWith(String firstName);

    // falta uma query personalizada
}

package app.clotheStore.repository;

import app.clotheStore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByCpf(String cpf);

    Customer findByName(String name);

    // falta uma query personalizada
}

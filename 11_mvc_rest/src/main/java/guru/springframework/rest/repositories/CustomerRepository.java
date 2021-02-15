package guru.springframework.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.rest.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

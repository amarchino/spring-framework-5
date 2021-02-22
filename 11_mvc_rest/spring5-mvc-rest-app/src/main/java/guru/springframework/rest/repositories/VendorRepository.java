package guru.springframework.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.rest.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

}

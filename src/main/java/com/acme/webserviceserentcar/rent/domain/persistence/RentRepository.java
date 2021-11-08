package com.acme.webserviceserentcar.rent.domain.persistence;

import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
}

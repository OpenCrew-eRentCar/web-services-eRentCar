package com.acme.webserviceserentcar.domain.persistence;

import com.acme.webserviceserentcar.domain.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}

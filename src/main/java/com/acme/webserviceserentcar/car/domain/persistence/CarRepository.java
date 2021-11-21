package com.acme.webserviceserentcar.car.domain.persistence;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByClientId(Long clientId, Pageable pageable);
}

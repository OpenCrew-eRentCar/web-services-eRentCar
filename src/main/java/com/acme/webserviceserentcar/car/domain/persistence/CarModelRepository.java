package com.acme.webserviceserentcar.car.domain.persistence;

import com.acme.webserviceserentcar.car.domain.model.entity.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
}

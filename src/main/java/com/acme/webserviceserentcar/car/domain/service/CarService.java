package com.acme.webserviceserentcar.car.domain.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarService {
    List<Car> getAll();
    Page<Car> getAll(Pageable pageable);
    //Page<Car> getAllCarsByClientId(Long clientId, Pageable pageable);
    Car getById(Long carId);
    Car create(Long clientId, Long carModelId, Car request);
    Car update(Long carId, Long carModelId, Car request);
    Car updateRate(Long carId, int rate);
    ResponseEntity<?> delete(Long carId);
}

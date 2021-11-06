package com.acme.webserviceserentcar.domain.service;

import com.acme.webserviceserentcar.domain.model.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarService {
    List<Car> getAll();
    Page<Car> getAll(Pageable pageable);
    Car getById(Long clientId);
    Car create(Car request);
    Car update(Long clientId, Car request);
    ResponseEntity<?> delete(Long clientId);

}

package com.acme.webserviceserentcar.car.domain.service;

import com.acme.webserviceserentcar.car.domain.model.entity.CarBrand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarBrandService {
    List<CarBrand> getAll();
    Page<CarBrand> getAll(Pageable pageable);
    CarBrand getById(Long carBrandId);
    CarBrand create(CarBrand request);
    CarBrand update(Long carBrandId, CarBrand request);
    ResponseEntity<?> delete(Long carBrandId);
}

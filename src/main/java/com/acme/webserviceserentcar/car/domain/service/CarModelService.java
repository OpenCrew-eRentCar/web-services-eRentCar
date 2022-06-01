package com.acme.webserviceserentcar.car.domain.service;

import com.acme.webserviceserentcar.car.domain.model.entity.CarModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarModelService {
    List<CarModel> getAll();
    Page<CarModel> getAll(Pageable pageable);
    CarModel getById(Long carModelId);
    CarModel create(Long carBrandId, CarModel request);
    CarModel update(Long carModelId, Long carBrandId, CarModel request);
    ResponseEntity<?> delete(Long carModelId);
}

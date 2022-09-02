package com.acme.webserviceserentcar.car.domain.service;

import com.acme.webserviceserentcar.car.domain.model.entity.CarModel;
import com.acme.webserviceserentcar.car.resource.create.CreateCarModelResource;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarModelResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarModelService {
    void seed();
    List<CarModel> getAll();
    Page<CarModel> getAll(Pageable pageable);
    CarModel getById(Long carModelId);
    CarModel create(CreateCarModelResource request);
    CarModel update(Long carModelId, UpdateCarModelResource request);
    ResponseEntity<?> delete(Long carModelId);
}

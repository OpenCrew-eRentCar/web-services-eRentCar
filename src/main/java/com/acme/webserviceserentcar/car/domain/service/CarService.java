package com.acme.webserviceserentcar.car.domain.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.resource.create.CreateCarResource;
import com.acme.webserviceserentcar.car.resource.searchFilters.SearchCarFilters;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarService {
    List<Car> getAll();
    Page<Car> getAll(Pageable pageable);
    List<Car> getAllByClient();
    Car getById(Long carId);
    List<Car> searchByFilters(SearchCarFilters searchCarFilters);
    Car create(CreateCarResource request);
    Car update(Long carId, UpdateCarResource request);
    Car updateRate(Long carId, int rate);
    ResponseEntity<?> delete(Long carId);
}

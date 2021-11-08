package com.acme.webserviceserentcar.service;

import com.acme.webserviceserentcar.domain.model.entity.Car;
import com.acme.webserviceserentcar.domain.model.entity.Client;
import com.acme.webserviceserentcar.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.domain.service.CarService;
import com.acme.webserviceserentcar.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class CarServiceImpl implements CarService {
    private static final String ENTITY = "Car";
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final Validator validator;


    public CarServiceImpl(CarRepository carRepository, ClientRepository clientRepository, Validator validator) {
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
        this.validator = validator;
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Page<Car> getAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    @Override
    public Car getById(Long clientId) {
        return carRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }

    @Override
    public Car create(Car request) {
        Set<ConstraintViolation<Car>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return carRepository.save(request);
    }

    @Override
    public Car update(Long clientId, Car request) {
        Set<ConstraintViolation<Car>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);
        return carRepository.findById(clientId).map(car ->
                carRepository.save(car.withAddress(request.getAddress())
                        .withBrand(request.getBrand())
                        .withClient(request.getClient())
                        .withCarValueInDollars(request.getCarValueInDollars())
                        .withExtraInformation(request.getExtraInformation())
                        .withMileage(request.getMileage())
                        .withModel(request.getModel())
                        .withRate(request.getRate())
                        .withRentAmountDay(request.getRentAmountDay())
                        .withSeating(request.getSeating())
                        .withYear(request.getYear()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));

    }

    @Override
    public ResponseEntity<?> delete(Long clientId) {
        return carRepository.findById(clientId).map(car -> {
            carRepository.delete(car);
            return ResponseEntity.ok().build();
        } ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }


}

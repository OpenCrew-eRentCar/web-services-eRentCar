package com.acme.webserviceserentcar.car.service;

import com.acme.webserviceserentcar.car.domain.model.entity.CarBrand;
import com.acme.webserviceserentcar.car.domain.persistence.CarBrandRepository;
import com.acme.webserviceserentcar.car.domain.service.CarBrandService;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class CarBrandServiceImpl implements CarBrandService {
    private static final String ENTITY = "Car Brand";
    private final CarBrandRepository carBrandRepository;
    private final Validator validator;

    public CarBrandServiceImpl(CarBrandRepository carBrandRepository, Validator validator) {
        this.carBrandRepository = carBrandRepository;
        this.validator = validator;
    }

    @Override
    public List<CarBrand> getAll() {
        return carBrandRepository.findAll();
    }

    @Override
    public Page<CarBrand> getAll(Pageable pageable) {
        return carBrandRepository.findAll(pageable);
    }

    @Override
    public CarBrand getById(Long carBrandId) {
        return carBrandRepository.findById(carBrandId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, carBrandId));
    }

    @Override
    public CarBrand create(CarBrand request) {
        Set<ConstraintViolation<CarBrand>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return carBrandRepository.save(request);
    }

    @Override
    public CarBrand update(Long carBrandId, CarBrand request) {
        Set<ConstraintViolation<CarBrand>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return carBrandRepository.findById(carBrandId).map(carBrand ->
                carBrandRepository.save(carBrand.withId(request.getId())
                                .withName(request.getName())
                                .withImagePath(request.getImagePath()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, carBrandId));
    }

    @Override
    public ResponseEntity<?> delete(Long carBrandId) {
        return carBrandRepository.findById(carBrandId).map(carBrand -> {
            carBrandRepository.delete(carBrand);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, carBrandId));
    }
}

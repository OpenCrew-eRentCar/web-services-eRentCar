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
import java.util.HashSet;
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
    public void seed() {
        Set<CarBrand> carBrands = new HashSet<>();
        carBrands.add(new CarBrand().withName("Nissan").withImagePath("https://upload.wikimedia.org/wikipedia/commons/thumb/2/23/Nissan_2020_logo.svg/512px-Nissan_2020_logo.svg.png"));
        carBrands.add(new CarBrand().withName("Chevrolet").withImagePath("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOHjQ1gS-vCo804qs2ShhijIt3LeF_w3L0H4GLXVmATLf0QFY9FFmOKvuPT5WT4fE1AXE&usqp=CAU"));

        carBrands.forEach(carBrand -> {
            if (!carBrandRepository.existsByName(carBrand.getName())) {
                carBrandRepository.save(carBrand);
            }
        });
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

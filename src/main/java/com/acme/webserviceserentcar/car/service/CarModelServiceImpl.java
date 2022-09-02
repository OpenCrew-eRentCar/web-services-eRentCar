package com.acme.webserviceserentcar.car.service;

import com.acme.webserviceserentcar.car.domain.model.entity.CarBrand;
import com.acme.webserviceserentcar.car.domain.model.entity.CarModel;
import com.acme.webserviceserentcar.car.domain.persistence.CarBrandRepository;
import com.acme.webserviceserentcar.car.domain.persistence.CarModelRepository;
import com.acme.webserviceserentcar.car.domain.service.CarModelService;
import com.acme.webserviceserentcar.car.mapping.CarModelMapper;
import com.acme.webserviceserentcar.car.resource.create.CreateCarModelResource;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarModelResource;
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
public class CarModelServiceImpl implements CarModelService {
    private static final String ENTITY = "Car Model";
    private final CarModelRepository carModelRepository;
    private final CarBrandRepository carBrandRepository;
    private final Validator validator;
    private final CarModelMapper carModelMapper;

    public CarModelServiceImpl(CarModelRepository carModelRepository,
                               CarBrandRepository carBrandRepository,
                               Validator validator, CarModelMapper carModelMapper) {
        this.carModelRepository = carModelRepository;
        this.carBrandRepository = carBrandRepository;
        this.validator = validator;
        this.carModelMapper = carModelMapper;
    }

    @Override
    public void seed() {
        Set<CarModel> carModels = new HashSet<>();
        carModels.add(new CarModel()
                .withName("Versa")
                .withImagePath("https://www.nissan-cdn.net/content/dam/Nissan/pe/vehicles/Versa_MY20/MY22/Sense%20MT.png.ximg.l_12_m.smart.png")
                .withCarBrand(carBrandRepository.findByName("Nissan").orElseThrow(() -> new ResourceNotFoundException("Car brand: Nissan"))));

        carModels.add(new CarModel()
                .withName("Cruze")
                .withImagePath("https://fotos.perfil.com/2019/12/17/chevrolet-actualizo-la-version-lt-del-cruze-824280.jpg")
                .withCarBrand(carBrandRepository.findByName("Chevrolet").orElseThrow(() -> new ResourceNotFoundException("Car brand: Chevrolet"))));

        carModels.forEach(carModel -> {
            if (!carModelRepository.existsByName(carModel.getName())) {
                carModelRepository.save(carModel);
            }
        });
    }

    @Override
    public List<CarModel> getAll() {
        return carModelRepository.findAll();
    }

    @Override
    public Page<CarModel> getAll(Pageable pageable) {
        return carModelRepository.findAll(pageable);
    }

    @Override
    public CarModel getById(Long carModelId) {
        return carModelRepository.findById(carModelId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, carModelId));
    }

    @Override
    public CarModel create(CreateCarModelResource request) {
        Set<ConstraintViolation<CreateCarModelResource>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        CarBrand carBrand = carBrandRepository.findById(request.getCardBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Car Brand", request.getCardBrandId()));

        CarModel carModel = carModelMapper.toModel(request);
        carModel.setCarBrand(carBrand);

        return carModelRepository.save(carModel);
    }

    @Override
    public CarModel update(Long carModelId, UpdateCarModelResource request) {
        Set<ConstraintViolation<UpdateCarModelResource>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return carModelRepository.findById(carModelId).map(carModel -> {
            CarBrand carBrand = carBrandRepository.findById(request.getCardBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Car Brand", request.getCardBrandId()));

            return carModelRepository.save(carModel.withName(request.getName())
                    .withImagePath(request.getImagePath())
                    .withCarBrand(carBrand));
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, carModelId));
    }

    @Override
    public ResponseEntity<?> delete(Long carModelId) {
        return carModelRepository.findById(carModelId).map(carModel -> {
            carModelRepository.delete(carModel);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, carModelId));
    }
}

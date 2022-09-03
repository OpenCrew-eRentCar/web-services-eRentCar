package com.acme.webserviceserentcar.rent.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.domain.persistence.RentRepository;
import com.acme.webserviceserentcar.rent.domain.service.RentService;
import com.acme.webserviceserentcar.rent.mapping.RentMapper;
import com.acme.webserviceserentcar.rent.resource.create.CreateRentResource;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class RentServiceImpl implements RentService {
    private static final String ENTITY = "Rent";
    private final RentRepository rentRepository;
    private final Validator validator;
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final RentMapper rentMapper;

    public RentServiceImpl(RentRepository rentRepository, Validator validator, CarRepository carRepository,
                           ClientRepository clientRepository, ClientService clientService, RentMapper rentMapper) {
        this.rentRepository = rentRepository;
        this.validator = validator;
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.rentMapper = rentMapper;
    }

    @Override
    public List<Rent> getAll() {
        Long clientId = clientService.getByToken().getId();
        return rentRepository.findAllByClientId(clientId);
    }

    @Override
    public Rent getById(Long rentId) {
        return rentRepository.findById(rentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ENTITY,
                        rentId
                ));
    }

    @Override
    public Rent create(CreateRentResource request) {
        Set<ConstraintViolation<CreateRentResource>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        Long clientId = clientService.getByToken().getId();

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client", clientId));

        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car", request.getCarId()));

        Rent rent = rentMapper.toModel(request);
        rent.setClient(client);
        rent.setCar(car);

        return rentRepository.save(rent);
    }

    @Override
    public Rent update(Long rentId, Rent request) {
        Set<ConstraintViolation<Rent>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return rentRepository.findById(rentId).map(rent ->
                rentRepository.save(rent.withStartDate(request.getStartDate())
                        .withFinishDate(request.getFinishDate())
                        .withAmount(request.getAmount())
                        .withRate(request.getRate()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, rentId));
    }

    @Override
    public ResponseEntity<?> delete(Long rentId) {
        return rentRepository.findById(rentId).map(rent -> {
            rentRepository.delete(rent);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, rentId));
    }
}

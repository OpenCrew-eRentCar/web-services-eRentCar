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
import com.acme.webserviceserentcar.rent.resource.update.UpdateRentResource;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    public boolean isFinishedRent(Date finishDate) throws ParseException {
        LocalDateTime now = LocalDateTime.now();
        Date today = Date.from(now.toInstant(ZoneOffset.UTC));
        return today.after(finishDate) || today.equals(finishDate);
    }

    public Rent updateRateClient(Long rentId, UpdateRentResource request) throws ParseException {
        Set<ConstraintViolation<UpdateRentResource>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        Rent rent = rentRepository.findById(rentId)
                .orElseThrow(() -> new ResourceNotFoundException("Rent", rentId));

        boolean result = isFinishedRent(rent.getFinishDate());

        if (!result)
            throw new ResourceValidationException(ENTITY, "Rent is not over.");

        rent.setRate(request.getRate());

        return rentRepository.save(rent);
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

        if (!rent.isRentPerDay()) clientService.updateAccumulatedKilometers(rent.getKilometers() / 10L);

        return rentRepository.save(rent);
    }

    @Override
    public Rent update(Long rentId, Rent request) {
        Set<ConstraintViolation<Rent>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return rentRepository.findById(rentId).map(rent ->
                rentRepository.save(rent.withRentPerDay(request.isRentPerDay())
                        .withStartDate(request.getStartDate())
                        .withFinishDate(request.getFinishDate())
                        .withKilometers(request.getKilometers())
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

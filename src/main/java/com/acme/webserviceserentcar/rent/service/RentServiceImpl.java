package com.acme.webserviceserentcar.rent.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.domain.persistence.RentRepository;
import com.acme.webserviceserentcar.rent.domain.service.RentService;
import com.acme.webserviceserentcar.reservations.domain.model.entity.Reservation;
import com.acme.webserviceserentcar.reservations.domain.persistence.ReservationRepository;
import com.acme.webserviceserentcar.reservations.domain.service.ReservationService;
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
public class RentServiceImpl implements RentService {
    private static final String ENTITY = "Rent";
    private final RentRepository rentRepository;
    private final Validator validator;
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final ReservationService reservationService;

    public RentServiceImpl(RentRepository rentRepository, Validator validator, CarRepository carRepository,
                           ClientRepository clientRepository, ReservationService reservationService) {
        this.rentRepository = rentRepository;
        this.validator = validator;
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
        this.reservationService = reservationService;
    }

    @Override
    public List<Rent> getAll() {
        return rentRepository.findAll();
    }

    @Override
    public Page<Rent> getAll(Pageable pageable) {
        return rentRepository.findAll(pageable);
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
    public Rent create(Long clientId, Long carId, Rent request) {
        Set<ConstraintViolation<Rent>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client", clientId));

        request.setClient(client);

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", carId));

        request.setCar(car);

        Rent rent = rentRepository.save(request);

        Reservation reservation = reservationService.create(car.getClient().getId(), rent.getId());

        rent.setReservation(reservation);

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

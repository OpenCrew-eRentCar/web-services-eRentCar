package com.acme.webserviceserentcar.reservations.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.domain.persistence.RentRepository;
import com.acme.webserviceserentcar.reservations.domain.model.entity.Reservation;
import com.acme.webserviceserentcar.reservations.domain.persistence.ReservationRepository;
import com.acme.webserviceserentcar.reservations.domain.service.ReservationService;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {
    private static final String ENTITY = "Reservation";
    private final ReservationRepository reservationRepository;
    private final RentRepository rentRepository;
    private final ClientRepository clientRepository;
    private final Validator validator;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RentRepository rentRepository, ClientRepository clientRepository, Validator validator) {
        this.reservationRepository = reservationRepository;
        this.rentRepository = rentRepository;
        this.clientRepository = clientRepository;
        this.validator = validator;
    }

    @Override
    public Reservation getById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, reservationId));
    }

    @Override
    public List<Reservation> getAllByClientId(Long clientId) {
        return reservationRepository.findByClientId(clientId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }

    @Override
    public Reservation create(Long clientId, Long rentId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client", clientId));
        Rent rent = rentRepository.findById(rentId).orElseThrow(() -> new ResourceNotFoundException("Rent", rentId));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setRent(rent);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return reservationRepository.save(reservation);
    }

    @Override
    public ResponseEntity<?> delete(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            reservationRepository.delete(reservation);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, reservationId));
    }
}

package com.acme.webserviceserentcar.reservations.domain.service;

import com.acme.webserviceserentcar.reservations.domain.model.entity.Reservation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationService {
    Reservation getById(Long reservationId);
    List<Reservation> getAllByClientId(Long clientId);
    Reservation create(Long clientId, Long rentId);
    ResponseEntity<?> delete(Long reservationId);
}

package com.acme.webserviceserentcar.reservations.domain.service;

import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;

import java.util.List;

public interface ReservationService {
    List<Rent> getAll();
}

package com.acme.webserviceserentcar.reservations.service;

import com.acme.webserviceserentcar.client.domain.service.ClientService;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.domain.persistence.RentRepository;
import com.acme.webserviceserentcar.reservations.domain.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private static final String ENTITY = "Reservation";
    private final RentRepository rentRepository;
    private final ClientService clientService;

    public ReservationServiceImpl(RentRepository rentRepository, ClientService clientService) {
        this.rentRepository = rentRepository;
        this.clientService = clientService;
    }

    @Override
    public List<Rent> getAll() {
        Long clientId = clientService.getByToken().getId();
        return rentRepository.findAllByCarOwner(clientId);
    }
}

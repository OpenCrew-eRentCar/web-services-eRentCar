package com.acme.webserviceserentcar.rent.domain.service;

import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.resource.create.CreateRentResource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RentService {
    List<Rent> getAll();
    Rent getById(Long rentId);
    Rent create(CreateRentResource request);
    Rent update(Long rentId, Rent request);
    ResponseEntity<?> delete(Long rentId);
}

package com.acme.webserviceserentcar.rent.domain.service;

import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RentService {
    List<Rent> getAll();
    Page<Rent> getAll(Pageable pageable);
    Rent getById(Long rentId);
    Rent create(Long clientId, Long carId, Rent request);
    Rent update(Long rentId, Rent request);
    ResponseEntity<?> delete(Long rentId);
}

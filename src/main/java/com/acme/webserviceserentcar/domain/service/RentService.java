package com.acme.webserviceserentcar.domain.service;

import com.acme.webserviceserentcar.domain.model.entity.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RentService {
    List<Rent> getAll();
    Page<Rent> getAll(Pageable pageable);
    Rent getById(Long rentId);
    Rent create(Rent request);
    Rent update(Long rentId, Rent request);
    ResponseEntity<?> delete(Long rentId);
}

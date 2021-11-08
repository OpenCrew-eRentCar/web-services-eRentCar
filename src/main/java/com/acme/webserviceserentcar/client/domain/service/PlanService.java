package com.acme.webserviceserentcar.client.domain.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlanService {
    List<Plan> getAll();
    Page<Plan> getAll(Pageable pageable);
    Plan getById(Long planId);
    Plan create(Plan request);
    Plan update(Long planId, Plan request);
    ResponseEntity<?> delete(Long planId);
}

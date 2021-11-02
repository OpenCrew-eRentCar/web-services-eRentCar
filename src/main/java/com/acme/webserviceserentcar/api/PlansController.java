package com.acme.webserviceserentcar.api;

import com.acme.webserviceserentcar.domain.service.PlanService;
import com.acme.webserviceserentcar.mapping.PlanMapper;
import com.acme.webserviceserentcar.resource.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/plans")
public class PlansController {
    private final PlanService planService;
    private final PlanMapper mapper;

    public PlansController(PlanService planService, PlanMapper mapper) {
        this.planService = planService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<PlanResource> getAllPlans(Pageable pageable) {
        return mapper.modelListToPage(planService.getAll(), pageable);
    }

    @GetMapping("{planId}")
    public PlanResource getPlanById(@PathVariable Long planId) {
        return mapper.toResource(planService.getById(planId));
    }

    @PostMapping
    public PlanResource createPlan(@Valid @RequestBody CreatePlanResource request) {
        return mapper.toResource(planService.create(mapper.toModel(request)));
    }

    @PutMapping("{planId}")
    public PlanResource updatePlan(@PathVariable Long planId, @Valid @RequestBody UpdatePlanResource request) {
        return mapper.toResource(planService.update(planId, mapper.toModel(request)));
    }

    @DeleteMapping("{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable Long planId) { return planService.delete(planId); }
}

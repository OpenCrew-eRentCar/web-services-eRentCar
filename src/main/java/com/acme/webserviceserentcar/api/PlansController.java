package com.acme.webserviceserentcar.api;

import com.acme.webserviceserentcar.domain.service.PlanService;
import com.acme.webserviceserentcar.mapping.PlanMapper;
import com.acme.webserviceserentcar.resource.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get All Plans", description = "Get All Plans on pages", tags = {"Plans"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Plans returned", content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public Page<PlanResource> getAllPlans(Pageable pageable) {
        return mapper.modelListToPage(planService.getAll(), pageable);
    }

    @Operation(summary = "Get Plan by Id", description = "Get Plan by Id", tags = {"Plans"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan returned", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("{planId}")
    public PlanResource getPlanById(@PathVariable Long planId) {
        return mapper.toResource(planService.getById(planId));
    }

    @Operation(summary = "Create Plan", description = "Create Plan", tags = {"Plans"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan created", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public PlanResource createPlan(@Valid @RequestBody CreatePlanResource request) {
        return mapper.toResource(planService.create(mapper.toModel(request)));
    }

    @Operation(summary = "Update Plan", description = "Update Plan", tags = {"Plans"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan updated", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("{planId}")
    public PlanResource updatePlan(@PathVariable Long planId, @Valid @RequestBody UpdatePlanResource request) {
        return mapper.toResource(planService.update(planId, mapper.toModel(request)));
    }

    @Operation(summary = "Delete Plan", description = "Delete Plan", tags = {"Plans"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan deleted", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable Long planId) { return planService.delete(planId); }
}

package com.acme.webserviceserentcar.client.api;

import com.acme.webserviceserentcar.client.domain.service.PlanService;
import com.acme.webserviceserentcar.client.mapping.PlanMapper;
import com.acme.webserviceserentcar.client.resource.create.CreatePlanResource;
import com.acme.webserviceserentcar.client.resource.PlanResource;
import com.acme.webserviceserentcar.client.resource.update.UpdatePlanResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/plans")
@CrossOrigin
public class PlansController {
    private final PlanService planService;
    private final PlanMapper mapper;

    public PlansController(PlanService planService, PlanMapper mapper) {
        this.planService = planService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get All Plans", description = "Get All Plans on pages", tags = {"Plans"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Plans returned",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlanResource.class))
                    ))
    })
    @GetMapping
    public Page<PlanResource> getAllPlans(Pageable pageable) {
        return mapper.modelListToPage(planService.getAll(), pageable);
    }

    @Operation(summary = "Get Plan by Id", description = "Get Plan by Id", tags = {"Plans"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanResource.class)
                    ))
    })
    @GetMapping("{planId}")
    public PlanResource getPlanById(@PathVariable Long planId) {
        return mapper.toResource(planService.getById(planId));
    }

    @Operation(summary = "Create Plan", description = "Create Plan", tags = {"Plans"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanResource.class)
                    ))
    })
    @PostMapping
    public PlanResource createPlan(@Valid @RequestBody CreatePlanResource request) {
        return mapper.toResource(planService.create(mapper.toModel(request)));
    }

    @Operation(summary = "Update Plan", description = "Update Plan", tags = {"Plans"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanResource.class)
                    ))
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

package com.acme.webserviceserentcar.api;

import com.acme.webserviceserentcar.domain.service.CarService;
import com.acme.webserviceserentcar.mapping.CarMapper;
import com.acme.webserviceserentcar.resource.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
    private final CarService carService;
    private final CarMapper mapper;


    public CarController(CarService carService, CarMapper mapper) {
        this.carService = carService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get All Cars", description = "Get All Free Cars", tags = {"Cars"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Cars returned",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CarResource.class))
                    ))
    })
    @GetMapping
    public Page<CarResource> getAllCars(Pageable pageable) {
        return mapper.modelListToPage(carService.getAll(), pageable);
    }

    @Operation(summary = "Update Car", description = "Updating Car", tags = {"Cars"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CarResource.class)
                    ))
    })
    @PutMapping("{carId}")
    public CarResource updateCar(@PathVariable Long carId, @Valid @RequestBody UpdateCarResource request) {
        return mapper.toResource(carService.update(carId, mapper.toModel(request)));
    }
}

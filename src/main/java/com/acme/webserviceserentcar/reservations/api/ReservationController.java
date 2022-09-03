package com.acme.webserviceserentcar.reservations.api;

import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.mapping.RentMapper;
import com.acme.webserviceserentcar.rent.resource.RentResource;
import com.acme.webserviceserentcar.reservations.domain.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin
public class ReservationController {
    private final ReservationService reservationService;
    private final RentMapper rentMapper;

    public ReservationController(ReservationService reservationService, RentMapper rentMapper) {
        this.reservationService = reservationService;
        this.rentMapper = rentMapper;
    }

    @Operation(summary = "Get Reservation by Car Owner", description = "Get Reservation by Car Owner", tags = {"Reservations"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Rent.class)
                    ))
    })
    @GetMapping()
    public Page<RentResource> getReservationByClientId(Pageable pageable) {
        return rentMapper.modelListToPage(reservationService.getAll(), pageable);
    }
}

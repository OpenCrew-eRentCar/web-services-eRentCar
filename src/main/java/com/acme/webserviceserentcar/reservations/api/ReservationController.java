package com.acme.webserviceserentcar.reservations.api;

import com.acme.webserviceserentcar.reservations.domain.model.entity.Reservation;
import com.acme.webserviceserentcar.reservations.domain.service.ReservationService;
import com.acme.webserviceserentcar.reservations.mapping.ReservationMapper;
import com.acme.webserviceserentcar.reservations.resource.ReservationResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    public ReservationController(ReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    @Operation(summary = "Get Reservation by Id", description = "Get Reservation by Id", tags = {"Reservations"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Reservation.class)
                    ))
    })
    @GetMapping("{reservationId}")
    public ReservationResource getReservationById(@PathVariable Long reservationId) {
        return reservationMapper.toResource(reservationService.getById(reservationId));
    }

    @Operation(summary = "Get Reservation by Client Id", description = "Get Reservation by Client Id", tags = {"Reservations"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Reservation.class)
                    ))
    })
    @GetMapping("/client/{clientId}")
    public List<ReservationResource> getReservationByClientId(@PathVariable Long clientId) {
        return reservationMapper.toResources(reservationService.getAllByClientId(clientId));
    }

    @Operation(summary = "Delete Reservation", description = "Delete Reservation", tags = {"Reservations"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation deleted", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("{reservationId}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long reservationId) {
        return reservationService.delete(reservationId);
    }
}

package com.acme.webserviceserentcar.reservations.resource;

import com.acme.webserviceserentcar.rent.resource.RentResource;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class ReservationResource {
    private Long id;
    private Long clientId;
    private RentResource rent;
}
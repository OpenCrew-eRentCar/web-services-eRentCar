package com.acme.webserviceserentcar.rent.resource;

import com.acme.webserviceserentcar.car.resource.CarResource;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class RentResource {
    private Long id;
    private boolean isRentPerDay;
    private Date startDate;
    private Date finishDate;
    private Long kilometers;
    private int amount;
    private double rate;
    private Long clientId;
    private CarResource car;
}

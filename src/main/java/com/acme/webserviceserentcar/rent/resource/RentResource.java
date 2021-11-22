package com.acme.webserviceserentcar.rent.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentResource {
    private Long id;
    private String startDate;
    private String finishDate;
    private int amount;
    private double rate;
    private Long clientId;
    private Long carId;
}

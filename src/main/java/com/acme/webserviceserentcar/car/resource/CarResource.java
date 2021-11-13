package com.acme.webserviceserentcar.car.resource;

import com.acme.webserviceserentcar.car.domain.model.entity.CategoryOfCar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResource {
    private Long id;
    private String address;
    private int year;
    private int mileage;
    private int seating;
    private boolean manual;
    private int carValueInDollars;
    private String extraInformation;
    private int rate;
    private int rentAmountDay;
    private CategoryOfCar category;
    private Long clientId;
}

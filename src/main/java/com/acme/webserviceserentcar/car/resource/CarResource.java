package com.acme.webserviceserentcar.car.resource;

import com.acme.webserviceserentcar.car.domain.model.enums.CategoryOfCar;
import com.acme.webserviceserentcar.car.domain.model.enums.MechanicConditions;
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
    private MechanicConditions mechanicConditions;
    private Long clientId;
    private Long carModelId;
}

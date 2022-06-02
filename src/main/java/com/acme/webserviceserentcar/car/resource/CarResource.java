package com.acme.webserviceserentcar.car.resource;

import lombok.*;

import com.acme.webserviceserentcar.car.domain.model.enums.CategoryOfCar;
import com.acme.webserviceserentcar.car.domain.model.enums.MechanicConditions;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
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
    private String imagePath;
    private CategoryOfCar category;
    private MechanicConditions mechanicConditions;
    private CarModelResource carModel;
    private Long clientId;
}

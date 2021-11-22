package com.acme.webserviceserentcar.car.resource;



import lombok.*;

import com.acme.webserviceserentcar.car.domain.model.enums.CategoryOfCar;
import com.acme.webserviceserentcar.car.domain.model.enums.MechanicConditions;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class CreateCarResource {

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String address;

    @NotNull
    @NotBlank
    @Size(max = 15)
    private String brand;

    @NotNull
    private int year;

    @NotNull
    @NotBlank
    @Size(max = 15)
    private String model;

    @NotNull
    private int mileage;

    @NotNull
    private int seating;

    @NotNull
    private boolean manual;

    @NotNull
    private int carValueInDollars;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String extraInformation;

    @NotNull
    private int rate;

    @NotNull
    private int rentAmountDay;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String imagePath;

    @NotNull
    private CategoryOfCar category;

    @NotNull
    private MechanicConditions mechanicCondition;
}

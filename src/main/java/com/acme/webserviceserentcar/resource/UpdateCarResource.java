package com.acme.webserviceserentcar.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateCarResource {

    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String address;

    @NotNull
    @NotBlank
    @Size(max = 10)
    private String brand;

    @NotNull
    private int year;

    @NotNull
    @NotBlank
    @Size(max = 10)
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
}

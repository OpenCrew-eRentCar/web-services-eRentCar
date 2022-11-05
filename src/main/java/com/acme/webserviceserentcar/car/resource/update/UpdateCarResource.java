package com.acme.webserviceserentcar.car.resource.update;

import com.acme.webserviceserentcar.car.domain.model.enums.CategoryOfCar;
import com.acme.webserviceserentcar.car.domain.model.enums.MechanicConditions;
import com.acme.webserviceserentcar.shared.domain.model.converter.StringListConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UpdateCarResource {
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String address;

    @NotNull
    private int year;

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
    @Convert(converter = StringListConverter.class)
    private List<String> imagePath;

    @NotNull
    private CategoryOfCar category;

    @NotNull
    private MechanicConditions mechanicCondition;

    @NotNull
    private boolean active;

    @NotNull
    private Long carModelId;
}

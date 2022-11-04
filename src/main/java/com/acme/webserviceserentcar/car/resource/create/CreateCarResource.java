package com.acme.webserviceserentcar.car.resource.create;



import com.acme.webserviceserentcar.car.domain.model.enums.InsuranceType;
import com.acme.webserviceserentcar.shared.domain.model.converter.StringListConverter;
import lombok.*;

import com.acme.webserviceserentcar.car.domain.model.enums.CategoryOfCar;
import com.acme.webserviceserentcar.car.domain.model.enums.MechanicConditions;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    private int year;

    @NotNull
    private int mileage;

    @NotNull
    private int seating;

    @NotNull
    private boolean manual;

    @NotNull
    private String licensePlate;

    @NotNull
    private InsuranceType insuranceType;

    @NotNull
    private int carValueInDollars;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String extraInformation;

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
    private Long carModelId;
}

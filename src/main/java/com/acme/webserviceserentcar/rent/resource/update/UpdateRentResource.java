package com.acme.webserviceserentcar.rent.resource.update;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class UpdateRentResource {
    @NotNull
    private Date startDate;

    @NotNull
    private Date finishDate;

    @NotNull
    private int amount;

    @NotNull
    private int rate;
}
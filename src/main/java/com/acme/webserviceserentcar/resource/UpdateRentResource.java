package com.acme.webserviceserentcar.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateRentResource {
    private long id;

    @NotNull
    @NotBlank
    @Size(max = 8)
    private String startDate;

    @NotNull
    @NotBlank
    @Size(max = 8)
    private String finishDate;

    @NotNull
    @NotBlank
    private int amount;
}

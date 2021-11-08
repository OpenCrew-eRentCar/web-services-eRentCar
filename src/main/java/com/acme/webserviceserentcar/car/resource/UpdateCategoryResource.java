package com.acme.webserviceserentcar.car.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateCategoryResource {
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String name;
}

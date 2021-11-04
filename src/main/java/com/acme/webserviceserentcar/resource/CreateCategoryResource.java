package com.acme.webserviceserentcar.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateCategoryResource {
    @NotNull
    @NotBlank
    @Size(max = 30)
    private String name;
}

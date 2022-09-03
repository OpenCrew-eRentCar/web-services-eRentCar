package com.acme.webserviceserentcar.favourite.resource.create;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateFavouriteResource {
    @NotNull
    private Long carId;
}

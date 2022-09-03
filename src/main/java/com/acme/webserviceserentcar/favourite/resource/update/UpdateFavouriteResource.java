package com.acme.webserviceserentcar.favourite.resource.update;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateFavouriteResource {
    @NotNull
    private Long carId;
}

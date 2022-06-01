package com.acme.webserviceserentcar.favourite.resource;

import com.acme.webserviceserentcar.car.resource.CarResource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouriteResource {
    private Long id;
    private Long clientId;
    private CarResource car;
}

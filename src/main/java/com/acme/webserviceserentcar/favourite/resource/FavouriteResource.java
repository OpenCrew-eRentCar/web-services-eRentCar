package com.acme.webserviceserentcar.favourite.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouriteResource {
    private Long id;
    private Long clientId;
    private Long carId;
}

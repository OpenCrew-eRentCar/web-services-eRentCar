package com.acme.webserviceserentcar.client.resource;

import com.acme.webserviceserentcar.car.resource.CarResource;
import com.acme.webserviceserentcar.favourite.resource.FavouriteResource;
import com.acme.webserviceserentcar.rent.resource.RentResource;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class ClientResource {
    private Long id;
    private String names;
    private String lastNames;
    private String address;
    private Long cellphoneNumber;
    private int averageResponsibility;
    private int responseTime;
    private double rate;
    private String imagePath;
    private Long planId;
    private Long UserId;
    private Set<CarResource> cars;
    private Set<FavouriteResource> favourites;
    private Set<RentResource> rents;
}

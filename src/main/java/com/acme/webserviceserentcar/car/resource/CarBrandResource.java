package com.acme.webserviceserentcar.car.resource;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class CarBrandResource {
    private Long id;
    private String name;
    private String imagePath;
    private Set<CarBrandCarModelResource> carModels;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
class CarBrandCarModelResource {
    private Long id;
    private String name;
    private String imagePath;
}

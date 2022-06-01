package com.acme.webserviceserentcar.car.resource;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class CarModelResource {
    private Long id;
    private String name;
    private String imagePath;
    private CarModelCarBrandResource carBrand;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
class CarModelCarBrandResource {
    private Long id;
    private String name;
    private String imagePath;
}

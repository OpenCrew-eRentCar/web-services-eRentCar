package com.acme.webserviceserentcar.car.resource.searchFilters;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class PriceRange {
    private int min;
    private int max;
}

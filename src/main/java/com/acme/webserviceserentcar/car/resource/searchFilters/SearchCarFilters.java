package com.acme.webserviceserentcar.car.resource.searchFilters;

import com.acme.webserviceserentcar.car.domain.model.enums.CategoryOfCar;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class SearchCarFilters {
    private Set<PriceRange> priceRanges;
    private Set<CategoryOfCar> categories;
}

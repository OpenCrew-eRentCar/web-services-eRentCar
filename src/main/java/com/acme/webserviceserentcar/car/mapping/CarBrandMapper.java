package com.acme.webserviceserentcar.car.mapping;

import com.acme.webserviceserentcar.car.domain.model.entity.CarBrand;
import com.acme.webserviceserentcar.car.resource.*;
import com.acme.webserviceserentcar.car.resource.create.CreateCarBrandResource;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarBrandResource;
import com.acme.webserviceserentcar.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class CarBrandMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    //Object Mapping

    public CarBrandResource toResource(CarBrand model) {
        return mapper.map(model, CarBrandResource.class);
    }

    public Page<CarBrandResource> modelListToPage(List<CarBrand> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, CarBrandResource.class), pageable, modelList.size());
    }

    public CarBrand toModel(CreateCarBrandResource resource) {
        return mapper.map(resource, CarBrand.class);
    }
    public CarBrand toModel(UpdateCarBrandResource resource) {
        return mapper.map(resource, CarBrand.class);
    }
}

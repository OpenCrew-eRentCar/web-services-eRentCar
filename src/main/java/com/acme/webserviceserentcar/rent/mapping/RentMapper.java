package com.acme.webserviceserentcar.rent.mapping;

import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.resource.create.CreateRentResource;
import com.acme.webserviceserentcar.rent.resource.RentResource;
import com.acme.webserviceserentcar.rent.resource.update.UpdateRentResource;
import com.acme.webserviceserentcar.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class RentMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    //Object Mapping
    public RentResource toResource(Rent model){
        return mapper.map(model, RentResource.class);
    }

    public Page<RentResource> modelListToPage
            (List<Rent> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, RentResource.class),
                pageable,
                modelList.size());
    }

    public Rent toModel(CreateRentResource resource){
        return mapper.map(resource, Rent.class);
    }

    public Rent toModel(UpdateRentResource resource){
        return mapper.map(resource, Rent.class);
    }
}

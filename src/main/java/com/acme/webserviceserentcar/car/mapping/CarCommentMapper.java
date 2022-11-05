package com.acme.webserviceserentcar.car.mapping;

import com.acme.webserviceserentcar.car.domain.model.entity.CarComment;
import com.acme.webserviceserentcar.car.resource.create.CreateCarCommentResource;
import com.acme.webserviceserentcar.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class CarCommentMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    //Object Mapping

    public CarComment toModel(CreateCarCommentResource resource) {
        return mapper.map(resource, CarComment.class);
    }
}

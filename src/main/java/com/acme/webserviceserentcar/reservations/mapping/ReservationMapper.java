package com.acme.webserviceserentcar.reservations.mapping;

import com.acme.webserviceserentcar.reservations.domain.model.entity.Reservation;
import com.acme.webserviceserentcar.reservations.resource.ReservationResource;
import com.acme.webserviceserentcar.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class ReservationMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    //Object Mapping
    public ReservationResource toResource(Reservation model) {
        return mapper.map(model, ReservationResource.class);
    }

    public List<ReservationResource> toResources(List<Reservation> modelList) {
        return mapper.mapList(modelList, ReservationResource.class);
    }

    public Page<ReservationResource> modelListToPage
            (List<Reservation> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, ReservationResource.class),
                pageable,
                modelList.size());
    }

    /*public Rent toModel(CreateRentResource resource){
        return mapper.map(resource, Rent.class);
    }

    public Rent toModel(UpdateRentResource resource){
        return mapper.map(resource, Rent.class);
    }*/
}

package com.acme.webserviceserentcar.favourite.mapping;

import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import com.acme.webserviceserentcar.favourite.resource.create.CreateFavouriteResource;
import com.acme.webserviceserentcar.favourite.resource.FavouriteResource;
import com.acme.webserviceserentcar.rent.resource.update.UpdateRentResource;
import com.acme.webserviceserentcar.shared.mapping.EnhancedModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class FavouriteMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    //Object Mapping
    public FavouriteResource toResource(Favourite model){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper.map(model, FavouriteResource.class);
    }

    public Page<FavouriteResource> modelListToPage
            (List<Favourite> modelList, Pageable pageable) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return new PageImpl<>(mapper.mapList(modelList, FavouriteResource.class),
                pageable,
                modelList.size());
    }

    public Favourite toModel(CreateFavouriteResource resource){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(resource, Favourite.class);
    }

    public Favourite toModel(UpdateRentResource resource){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(resource, Favourite.class);
    }
}

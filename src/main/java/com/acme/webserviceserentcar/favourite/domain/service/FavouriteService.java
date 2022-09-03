package com.acme.webserviceserentcar.favourite.domain.service;

import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import com.acme.webserviceserentcar.favourite.resource.create.CreateFavouriteResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FavouriteService {
    List<Favourite> getAll();
    Favourite getById(Long favouriteId);
    Favourite create(CreateFavouriteResource request);
    ResponseEntity<?> delete(Long favouriteId);
}

package com.acme.webserviceserentcar.favourite.domain.service;

import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FavouriteService {
    List<Favourite> getAll();
    Page<Favourite> getAll(Pageable pageable);
    //Page<Favourite> getAllFavouritesByClientId(Long clientId, Pageable pageable);
    Favourite getById(Long favouriteId);
    Favourite create(Long clientId, Long carId, Favourite request);
    ResponseEntity<?> delete(Long favouriteId);
}

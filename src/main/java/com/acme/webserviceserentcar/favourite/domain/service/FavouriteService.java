package com.acme.webserviceserentcar.favourite.domain.service;

import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface FavouriteService {
    List<Favourite> getAll();
    Page<Favourite> getAll(Pageable pageable);
    Favourite getById(Long favouriteId);
    Favourite create(Favourite request);
    Favourite update(Long favouriteId, Favourite request);
    ResponseEntity<?> delete(Long favouriteId);
}

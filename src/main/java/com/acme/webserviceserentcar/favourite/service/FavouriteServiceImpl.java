package com.acme.webserviceserentcar.favourite.service;

import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import com.acme.webserviceserentcar.favourite.domain.persistence.FavouriteRepository;
import com.acme.webserviceserentcar.favourite.domain.service.FavouriteService;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

public class FavouriteServiceImpl implements FavouriteService {
    private static final String ENTITY = "Favourite";
    private final FavouriteRepository favouriteRepository;
    private final Validator validator;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository, Validator validator) {
        this.favouriteRepository = favouriteRepository;
        this.validator = validator;
    }

    @Override
    public List<Favourite> getAll() {
        return favouriteRepository.findAll();
    }

    @Override
    public Page<Favourite> getAll(Pageable pageable) {
        return favouriteRepository.findAll(pageable);
    }

    @Override
    public Favourite getById(Long favouriteId) {
        return favouriteRepository.findById(favouriteId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        ENTITY,
                        favouriteId
                ));
    }

    @Override
    public Favourite create(Favourite request) {
        Set<ConstraintViolation<Favourite>> violations = validator.validate(request);
        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);
        return favouriteRepository.save(request);
    }

    @Override
    public ResponseEntity<?> delete(Long favouriteId) {
        return favouriteRepository.findById(favouriteId).map(favourite -> {
            favouriteRepository.delete(favourite);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, favouriteId));
    }

}

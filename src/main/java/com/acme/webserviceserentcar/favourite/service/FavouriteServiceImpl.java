package com.acme.webserviceserentcar.favourite.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.service.CarService;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import com.acme.webserviceserentcar.favourite.domain.persistence.FavouriteRepository;
import com.acme.webserviceserentcar.favourite.domain.service.FavouriteService;
import com.acme.webserviceserentcar.favourite.mapping.FavouriteMapper;
import com.acme.webserviceserentcar.favourite.resource.create.CreateFavouriteResource;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class FavouriteServiceImpl implements FavouriteService {
    private static final String ENTITY = "Favourite";
    private final FavouriteRepository favouriteRepository;
    private final Validator validator;
    private final CarService carService;
    private final ClientService clientService;
    private final FavouriteMapper favouriteMapper;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository, Validator validator,
                                CarService carService, ClientService clientService, FavouriteMapper favouriteMapper) {
        this.favouriteRepository = favouriteRepository;
        this.validator = validator;
        this.carService = carService;
        this.clientService = clientService;
        this.favouriteMapper = favouriteMapper;
    }

    @Override
    public List<Favourite> getAll() {
        Long clientId = this.clientService.getByToken().getId();
        return favouriteRepository.findByClientId(clientId);
    }

    @Override
    public Favourite getById(Long favouriteId) {
        return favouriteRepository.findById(favouriteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ENTITY,
                        favouriteId
                ));
    }

    @Override
    public Favourite create(CreateFavouriteResource request) {
        Set<ConstraintViolation<CreateFavouriteResource>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            throw new ResourceValidationException(ENTITY, violations);
        }

        Long clientId = this.clientService.getByToken().getId();

        if (favouriteRepository.existsByCarIdAndClientId(request.getCarId(), clientId)) {
            throw new ResourceValidationException(ENTITY, "Car already in favourites");
        }

        Client client = clientService.getById(clientId);
        Car car = carService.getById(request.getCarId());

        if (car.getClient().getId() == clientId) {
            throw new ResourceValidationException(ENTITY, "You can't add your own car to favourites");
        }

        Favourite favourite = favouriteMapper.toModel(request);
        favourite.setClient(client);
        favourite.setCar(car);

        return favouriteRepository.save(favourite);
    }

    @Override
    public ResponseEntity<?> delete(Long favouriteId) {
        Long clientId = this.clientService.getByToken().getId();

        if (!favouriteRepository.existsByCarIdAndClientId(favouriteId, clientId)) {
            throw new ResourceNotFoundException(ENTITY, favouriteId);
        }

        return favouriteRepository.findById(favouriteId).map(favourite -> {
            favouriteRepository.delete(favourite);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, favouriteId));
    }
}

package com.acme.webserviceserentcar.favourite.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
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
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final FavouriteMapper favouriteMapper;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository, Validator validator,
                                CarRepository carRepository, ClientRepository clientRepository,
                                ClientService clientService, FavouriteMapper favouriteMapper) {
        this.favouriteRepository = favouriteRepository;
        this.validator = validator;
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
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

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client", clientId));

        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car", request.getCarId()));

        Favourite favourite = favouriteMapper.toModel(request);
        favourite.setClient(client);
        favourite.setCar(car);

        return favouriteRepository.save(favourite);
    }

    @Override
    public ResponseEntity<?> delete(Long favouriteId) {
        return favouriteRepository.findById(favouriteId).map(favourite -> {
            favouriteRepository.delete(favourite);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, favouriteId));
    }
}

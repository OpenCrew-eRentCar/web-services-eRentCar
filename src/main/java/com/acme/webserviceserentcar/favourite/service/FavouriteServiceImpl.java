package com.acme.webserviceserentcar.favourite.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import com.acme.webserviceserentcar.favourite.domain.persistence.FavouriteRepository;
import com.acme.webserviceserentcar.favourite.domain.service.FavouriteService;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository, Validator validator, CarRepository carRepository, ClientRepository clientRepository) {
        this.favouriteRepository = favouriteRepository;
        this.validator = validator;
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Favourite> getAll() {
        return favouriteRepository.findAll();
    }

    @Override
    public Page<Favourite> getAll(Pageable pageable) {
        return favouriteRepository.findAll(pageable);
    }

    /*@Override
    public Page<Favourite> getAllFavouritesByClientId(Long clientId, Pageable pageable) {
        return favouriteRepository.findByClientId(clientId, pageable);
    }*/

    @Override
    public Favourite getById(Long favouriteId) {
        return favouriteRepository.findById(favouriteId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        ENTITY,
                        favouriteId
                ));
    }

    @Override
    public Favourite create(Long clientId, Long carId, Favourite request) {
        Set<ConstraintViolation<Favourite>> violations = validator.validate(request);
        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client", clientId));

        request.setClient(client);

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", carId));

        request.setCar(car);

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

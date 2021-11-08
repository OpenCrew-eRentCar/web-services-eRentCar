package com.acme.webserviceserentcar.service;

import com.acme.webserviceserentcar.domain.model.entity.Rent;
import com.acme.webserviceserentcar.domain.persistence.RentRepository;
import com.acme.webserviceserentcar.domain.service.RentService;
import com.acme.webserviceserentcar.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class RentServiceImpl implements RentService {
    private static final String ENTITY = "Rent";
    private final RentRepository rentRepository;
    private final Validator validator;

    public RentServiceImpl(RentRepository rentRepository, Validator validator) {
        this.rentRepository = rentRepository;
        this.validator = validator;
    }

    @Override
    public List<Rent> getAll() {
        return rentRepository.findAll();
    }

    @Override
    public Page<Rent> getAll(Pageable pageable) {
        return rentRepository.findAll(pageable);
    }

    @Override
    public Rent getById(Long rentId) {
        return rentRepository.findById(rentId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        ENTITY,
                        rentId
                ));
    }

    @Override
    public Rent create(Rent request) {
        Set<ConstraintViolation<Rent>> violations = validator.validate(request);
        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);
        return rentRepository.save(request);
    }

    @Override
    public Rent update(Long rentId, Rent request) {
        Set<ConstraintViolation<Rent>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return rentRepository.findById(rentId).map(rent->
                rentRepository.save(rent.withStartDate(request.getStartDate()))
                        .withFinishDate(request.getFinishDate()))
                        .get().withAmount(request.getAmount());
    }

    @Override
    public ResponseEntity<?> delete(Long rentId) {
        return rentRepository.findById(rentId).map(rent -> {
            rentRepository.delete(rent);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, rentId));
    }
}

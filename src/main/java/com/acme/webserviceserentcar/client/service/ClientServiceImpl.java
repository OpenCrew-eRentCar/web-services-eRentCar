package com.acme.webserviceserentcar.client.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import com.acme.webserviceserentcar.client.domain.service.PlanService;
import com.acme.webserviceserentcar.security.domain.model.entity.User;
import com.acme.webserviceserentcar.security.domain.persistence.UserRepository;
import com.acme.webserviceserentcar.security.middleware.UserDetailsImpl;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {
    private static final String ENTITY = "Client";
    private final ClientRepository clientRepository;
    private final PlanService planService;
    private final UserRepository userRepository;
    private final Validator validator;

    public ClientServiceImpl(ClientRepository clientRepository, PlanService planService, UserRepository userRepository, Validator validator) {
        this.clientRepository = clientRepository;
        this.planService = planService;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    public String getUsernameFromAuthentication() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public Long getUserIdFromAuthentication() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    @Override
    public List<Client> getAll() { return clientRepository.findAll(); }

    @Override
    public Page<Client> getAll(Pageable pageable) { return clientRepository.findAll(pageable); }

    @Override
    public Client getById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }

    @Override
    public Client getByToken() {
        Long userId = this.getUserIdFromAuthentication();
        return clientRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, userId));
    }

    @Override
    public Client create(Client request) {
        Set<ConstraintViolation<Client>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        if (!isValidFullName(request.getNames(), request.getLastNames()))
            throw new IllegalArgumentException("Incorrect first name and/or last names.");

        if (!isValidDni(request.getDni()))
            throw new IllegalArgumentException("The DNI must have 8 numbers");


        /* User user = userRepository.findById(this.getUserIdFromAuthentication())
                .orElseThrow(() -> new ResourceNotFoundException("User", this.getUserIdFromAuthentication()));

        request.setUser(user);*/

        return clientRepository.save(request);
    }

    public boolean isValidFullName(String names, String lastName) {
        if (!names.trim().isEmpty() && names.length() >= 3 && names.length() <= 35)
            return !lastName.trim().isEmpty() && lastName.length() >= 2;
        return false;
    }

    public boolean isValidNickName(String name) {
        return !name.trim().isEmpty() && name.length() >= 4 && name.length() <= 12;
    }

    public boolean isValidDni(String dni) {
        boolean isNumeric = (dni != null && dni.matches("[0-9]+"));
        boolean isNumberOfDni = dni.length() == 8;
        return isNumeric && isNumberOfDni;
    }

    @Override
    public Client update(Client request) {
        Set<ConstraintViolation<Client>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        Long clientId = getByToken().getId();

        return clientRepository.findById(clientId).map(client ->
                clientRepository.save(client.withNames(request.getNames())
                        .withLastNames(request.getLastNames())
                        .withAddress(request.getAddress())
                        .withCellphoneNumber(request.getCellphoneNumber())
                        .withAverageResponsibility(request.getAverageResponsibility())
                        .withResponseTime(request.getResponseTime())
                        .withRate(request.getRate())
                        .withImagePath(request.getImagePath())
                        .withPlan(request.getPlan()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }

    @Override
    public Client updatePlan(Long planId) {
        Plan plan = planService.getById(planId);

        Long clientId = getByToken().getId();

        return clientRepository.findById(clientId).map(client ->
                clientRepository.save(client.withPlan(plan))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }

    @Override
    public ResponseEntity<?> delete(Long clientId) {
        return clientRepository.findById(clientId).map(client -> {
            clientRepository.delete(client);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }


    @Override
    public boolean validateRecord(Long clientId){
        double cmp = getById(clientId).getRecord();
        return  cmp<5;
    }
}

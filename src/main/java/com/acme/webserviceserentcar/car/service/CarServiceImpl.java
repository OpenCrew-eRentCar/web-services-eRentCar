package com.acme.webserviceserentcar.car.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.model.entity.CarModel;
import com.acme.webserviceserentcar.car.domain.model.enums.InsuranceType;
import com.acme.webserviceserentcar.car.domain.persistence.CarModelRepository;
import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.car.domain.service.CarService;
import com.acme.webserviceserentcar.car.mapping.CarMapper;
import com.acme.webserviceserentcar.car.persistence.CarRepositoryCustom;
import com.acme.webserviceserentcar.car.resource.create.CreateCarResource;
import com.acme.webserviceserentcar.car.resource.searchFilters.SearchCarFilters;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarResource;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
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
public class CarServiceImpl implements CarService {
    private static final String ENTITY = "Car";
    private final CarRepository carRepository;
    private final CarRepositoryCustom carRepositoryCustom;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final CarModelRepository carModelRepository;
    private final Validator validator;
    private final CarMapper carMapper;

    public CarServiceImpl(CarRepository carRepository,
                          CarRepositoryCustom carRepositoryCustom, ClientRepository clientRepository,
                          ClientService clientService, CarModelRepository carModelRepository,
                          Validator validator, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carRepositoryCustom = carRepositoryCustom;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.carModelRepository = carModelRepository;
        this.validator = validator;
        this.carMapper = carMapper;
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Page<Car> getAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    @Override
    public List<Car> getAllByClient() {
        Long clientId = this.clientService.getByToken().getId();
        return carRepository.findByClientId(clientId);
    }

    @Override
    public Car getById(Long carId) {
        return carRepository.findById(carId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, carId));
    }

    @Override
    public boolean existThisLicensePlate(String licencePlate) {
        Car car = carRepository.findByLicensePlate(licencePlate);
        if (car == null) {
            return false;
        } return true;
    }

    @Override
    public boolean isActiveSOAT(String licensePlate, InsuranceType insuranceType)
    {
        // Here will be the connection with the rest API to verify SOAT
        // It is required to be an organization with RUT to use the service.

        class Insurance {
            public String licensePlate;
            public InsuranceType insuranceType;
            public boolean isActive;

            Insurance(String licensePlate, InsuranceType insuranceType, boolean isActive) {
                this.licensePlate = licensePlate;
                this.insuranceType = insuranceType;
                this.isActive = isActive;
            }
        }

        Insurance[] arrInsurance = new Insurance[5];

        arrInsurance[0] = new Insurance("AKR-079", InsuranceType.RIMAC, true);
        arrInsurance[1] = new Insurance("AKR-080", InsuranceType.RIMAC, false);
        arrInsurance[2] = new Insurance("AKR-081", InsuranceType.PACIFICO, true);
        arrInsurance[3] = new Insurance("AKR-082", InsuranceType.PACIFICO, false);
        arrInsurance[4] = new Insurance("AKR-083", InsuranceType.RIMAC, true);

        boolean result = true;

        for (int i = 0; i < arrInsurance.length; i++) {
            if (arrInsurance[i].licensePlate == licensePlate && arrInsurance[i].insuranceType == insuranceType) {
                result = arrInsurance[i].isActive;
                break;
            }
        }
        return result;
    }

    @Override
    public List<Car> searchByFilters(SearchCarFilters searchCarFilters) {
        Long clientId = this.clientService.getByToken().getId();
        return carRepositoryCustom.findBySearchFilters(searchCarFilters, clientId);
    }

    @Override
    public Car create(CreateCarResource request) {
        Set<ConstraintViolation<CreateCarResource>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        Long clientId = this.clientService.getByToken().getId();

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client", clientId));

        CarModel carModel = carModelRepository.findById(request.getCarModelId())
                .orElseThrow(() -> new ResourceNotFoundException("Car Model", request.getCarModelId()));

        Car car = carMapper.toModel(request);
        car.setClient(client);
        car.setCarModel(carModel);
        car.setActive(true);

        return carRepository.save(car);
    }

    @Override
    public Car update(Long carId, UpdateCarResource request) {
        Set<ConstraintViolation<UpdateCarResource>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return carRepository.findById(carId).map(car -> {
            CarModel carModel = carModelRepository.findById(request.getCarModelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Car Model", request.getCarModelId()));

            return carRepository.save(car.withAddress(request.getAddress())
                    .withYear(request.getYear())
                    .withMileage(request.getMileage())
                    .withSeating(request.getSeating())
                    .withManual(request.isManual())
                    .withCarValueInDollars(request.getCarValueInDollars())
                    .withExtraInformation(request.getExtraInformation())
                    .withRentAmountDay(request.getRentAmountDay())
                    .withImagePath(request.getImagePath())
                    .withCategory(request.getCategory())
                    .withMechanicCondition(request.getMechanicCondition())
                    .withActive(request.isActive())
                    .withCarModel(carModel));
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, carId));
    }

    @Override
    public Car updateRate(Long carId, int rate) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, carId));

        if (car.getRate() == 0) {
            car.setRate(rate);
        }
        else {
            car.setRate((car.getRate() + rate) / 2);
        }

        carRepository.save(car);

        return car;
    }

    @Override
    public ResponseEntity<?> delete(Long carId) {
        return carRepository.findById(carId).map(car -> {
            carRepository.delete(car);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, carId));
    }
}

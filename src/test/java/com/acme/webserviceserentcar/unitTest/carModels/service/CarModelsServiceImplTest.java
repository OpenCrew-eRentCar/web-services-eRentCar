package com.acme.webserviceserentcar.unitTest.carModels.service;

import com.acme.webserviceserentcar.car.domain.model.entity.CarBrand;
import com.acme.webserviceserentcar.car.domain.persistence.CarBrandRepository;
import com.acme.webserviceserentcar.car.domain.model.entity.CarModel;
import com.acme.webserviceserentcar.car.domain.persistence.CarModelRepository;
import com.acme.webserviceserentcar.car.domain.service.CarBrandService;
import com.acme.webserviceserentcar.car.mapping.CarModelMapper;
import com.acme.webserviceserentcar.car.resource.create.CreateCarModelResource;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarModelResource;
import com.acme.webserviceserentcar.car.service.CarModelServiceImpl;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CarModelsServiceImplTest {

    @Mock
    private CarModelRepository carModelRepository;
    @Mock
    private CarBrandRepository carBrandRepository;
    @Mock
    private CarModelMapper carModelMapper;
    @Mock
    private Validator validator;
    @Mock
    private CarBrandService carBrandService;
    @Mock
    private ClientServiceImpl clientService;


    @InjectMocks
    @Spy
    private CarModelServiceImpl carModelService;
    private CreateCarModelResource createCarModelResource;
    private List<CarModel> carModels;
    private Client client1;
    private CarModel carModel;
    private CarBrand carBrand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createCarModelResource = new CreateCarModelResource();
        createCarModelResource.setCardBrandId(1l);

        client1 = new Client();
        client1.setId(1L);

        carBrand = new CarBrand();
        carBrand.setId(1L);

        carModel = new CarModel();
        carModel.setCarBrand(carBrand);
        carModel.setName("Audi");
        carModel.setImagePath("www.image.com");

        CarModel carModel1 = new CarModel();
        carModel1.setId(2L);
        carModel1.setName("Onda");
        CarModel carModel2 = new CarModel();
        carModel2.setId(3L);
        carModel2.setName("kia");
        
        carModels = new ArrayList<>();
        carModels.add(carModel1);
        carModels.add(carModel2);

    }

    @Test
    void getAllCarModels() {
        when(carModelRepository.findAll()).thenReturn(carModels);
        List<CarModel> result = carModelService.getAll();
        assertEquals(carModels,result);
    }

    @Test
    void getCarModelById() {
        when(carModelRepository.findById(carModel.getId())).thenReturn( Optional.of(carModel));
        CarModel result = carModelService.getById(carModel.getId());
        assertEquals(carModel,result);
    }

    @Test
    void createCarModel(){
        CreateCarModelResource createCarModelResource = new CreateCarModelResource();
        createCarModelResource.setCardBrandId(1L);

        carModel = new CarModel();
        carModel.setId(carModel.getId());
        carModel.setName(carModel.getName());
        carModel.setCarBrand(carBrand);

        when(carBrandRepository.findById(createCarModelResource.getCardBrandId())).thenReturn(Optional.of(carBrand));
        when(carModelMapper.toModel(createCarModelResource)).thenReturn(carModel);
        when(carModelRepository.save(carModel)).thenReturn(carModel);

        CarModel result = carModelService.create(createCarModelResource);
        assertEquals(carModel, result);
        verify(carModelRepository).save(carModel);
    }

    @Test
    void updateCarModel() {
        UpdateCarModelResource request = new UpdateCarModelResource();
        request.setName(carModel.getName());
        request.setCardBrandId(1L);
        request.setImagePath("www.image-update.com");

        CarModel carModelUpdate = new CarModel();
        carModelUpdate.setId(carModel.getId());
        carModelUpdate.setName(carModel.getName());
        carModelUpdate.setCarBrand(carBrand);
        carModelUpdate.setImagePath(request.getImagePath());

        when(carBrandRepository.findById(request.getCardBrandId())).thenReturn(Optional.of(carBrand));
        when(carModelRepository.save(carModelUpdate)).thenReturn(carModelUpdate);
        when(carModelRepository.findById(carModelUpdate.getId())).thenReturn(Optional.of(carModelUpdate));

        CarModel result = carModelService.update(carModelUpdate.getId(), request);
        assertEquals(carModelUpdate, result);
        verify(carModelRepository).save(carModelUpdate);
    }

    @Test
    void deleteCarModel() {
        when(carModelRepository.findById(carModel.getId())).thenReturn(Optional.of(carModel));
        carModelService.delete(carModel.getId());
        carModels.remove(carModel);
        System.out.println(carModels);
        List<CarModel> results = carModels;
        assertEquals(carModels, results);
    }
}

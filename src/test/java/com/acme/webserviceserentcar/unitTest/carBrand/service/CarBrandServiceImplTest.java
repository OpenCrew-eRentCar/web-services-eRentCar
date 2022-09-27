package com.acme.webserviceserentcar.unitTest.carBrand.service;

import com.acme.webserviceserentcar.car.domain.model.entity.CarBrand;
import com.acme.webserviceserentcar.car.domain.persistence.CarBrandRepository;
import com.acme.webserviceserentcar.car.service.CarBrandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CarBrandServiceImplTest {
    @Mock
    private CarBrandRepository carBrandRepository;
    @Mock
    private Validator validator;

    @InjectMocks
    private CarBrandServiceImpl carBrandService;

    private CarBrand carBrand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        carBrand = new CarBrand();
        carBrand.setId(1L);
        carBrand.setName("Audi");
        carBrand.setImagePath("www.image.com");
    }

    @Test
    void createCarBrand() {
        when(carBrandRepository.save(carBrand)).thenReturn(carBrand);
        CarBrand result = carBrandService.create(carBrand);
        assertEquals(carBrand, result);
        verify(carBrandRepository).save(carBrand);
    }

    @Test
    void updateCarBrand() {
        CarBrand request = new CarBrand();
        request.setId(1L);
        request.setName("Audi");
        request.setImagePath("www.image-update.com");

        when(carBrandRepository.findById(carBrand.getId())).thenReturn(Optional.of(request));
        when(carBrandRepository.save(request)).thenReturn(request);
        CarBrand result = carBrandService.update(carBrand.getId(), request);
        assertEquals(request, result);
        verify(carBrandRepository).save(request);
    }
}

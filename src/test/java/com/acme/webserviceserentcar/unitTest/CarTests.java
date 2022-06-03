package com.acme.webserviceserentcar.unitTest;

import com.acme.webserviceserentcar.car.domain.model.enums.CategoryOfCar;
import com.acme.webserviceserentcar.car.domain.model.enums.MechanicConditions;
import com.acme.webserviceserentcar.car.resource.create.CreateCarResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
public class CarTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectmapper;
    @Test
    void GetCars() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/cars").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
    @Test
    void GetCarById() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/cars/1").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
    @Test
    void CreateCar() throws Exception{
        String address="123ToyotaPrueba";
        int year=2012;
        int mileage=50;
        int seating=10;
        boolean manual=true;
        int carValueInDollars=3000;
        String extraInformation="aaa";
        int rentAmountDay=10;
        String imagePath="url";

        CreateCarResource createCarResource=new CreateCarResource(address, year, mileage,
                seating, manual, carValueInDollars, extraInformation, rentAmountDay,
                imagePath, CategoryOfCar.LARGE, MechanicConditions.GOOD);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.
                post("http://localhost:8080/api/v1/cars")
                .queryParam("clientId", "2")
                .queryParam("carModelId", "1")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(createCarResource))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        System. out.println(mvcResult.getResponse());
        Assertions.assertEquals(200, status);
    }
    @Test
    void DeleteCar() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/cars/1").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status=mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
}

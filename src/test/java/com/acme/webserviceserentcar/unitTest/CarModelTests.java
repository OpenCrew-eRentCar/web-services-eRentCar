package com.acme.webserviceserentcar.unitTest;

import com.acme.webserviceserentcar.car.resource.create.CreateCarModelResource;
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
public class CarModelTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectmapper;
    @Test
    void GetCarModel() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/car-models").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
    @Test
    void GetCarModelById() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/car-models/1").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
    @Test
    void CreateCarModel()throws Exception{
        String name="Yaris";
        String imagePath="url";
        CreateCarModelResource createCarModelResource=new CreateCarModelResource();
        createCarModelResource.setName(name);
        createCarModelResource.setImagePath(imagePath);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.
                post("http://localhost:8080/api/v1/car-models")
                .queryParam("carBrandId", "1")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(createCarModelResource))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        System. out.println(mvcResult.getResponse());
        Assertions.assertEquals(200, status);
    }
}
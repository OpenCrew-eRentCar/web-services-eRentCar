package com.acme.webserviceserentcar.unitTest;

import com.acme.webserviceserentcar.car.resource.create.CreateCarBrandResource;
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
public class CarBrandTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectmapper;
    @Test
    void GetCarBrand() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/car-brands").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
    @Test
    void GetCarBrandId() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/car-brands/1").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
    @Test
    void CreateCarBrand() throws Exception{
        String name="Toyota";
        String imagePath="url";
        CreateCarBrandResource createCarBrandResource=new CreateCarBrandResource(name,imagePath);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.
                post("http://localhost:8080/api/v1/car-brands").accept(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(createCarBrandResource))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        System. out.println(mvcResult.getResponse());
        Assertions.assertEquals(200, status);
    }
}

package com.acme.webserviceserentcar.unitTest;

import com.acme.webserviceserentcar.rent.resource.create.CreateRentResource;
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

import java.util.Calendar;
import java.util.Date;

@AutoConfigureMockMvc
@SpringBootTest
public class RentTests {
    @Autowired
    private MockMvc mockMvc;//esto ayuda para los test con url
    @Autowired
    ObjectMapper objectmapper;
    @Test
    void GetRents() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/rents").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
    @Test
    void GetRentsById() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8105/api/v1/rents/2").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
    @Test
    void CreateRent() throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2022);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 2);

        Date startDate = cal.getTime();
        Date finishDate = cal.getTime();
        int amount=50;
        int rate=4;
        CreateRentResource createRentResource=new CreateRentResource(startDate,finishDate,amount,rate);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.
                post("http://localhost:8080/api/v1/rents")
                    .queryParam("clientId", "2")
                    .queryParam("carId", "3")
                    .accept(MediaType.APPLICATION_JSON).content(objectmapper
                        .writeValueAsString(createRentResource)).contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        System. out.println(mvcResult.getResponse());
        Assertions.assertEquals(200, status);
    }
    @Test
    void DeleteRent() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/rents/2").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status=mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
}

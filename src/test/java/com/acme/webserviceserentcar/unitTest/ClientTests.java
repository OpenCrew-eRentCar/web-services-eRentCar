package com.acme.webserviceserentcar.unitTest;
import com.acme.webserviceserentcar.client.resource.create.CreateClientResource;
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

/*@WebMvcTest(value = ClientsController.class)
@WithMockUser*/
@AutoConfigureMockMvc
@SpringBootTest
public class ClientTests{
    @Autowired
    private MockMvc mockMvc;//esto ayuda para los test con url
    @Autowired
    ObjectMapper objectmapper;
    @Test
    void GetAllClients()throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/clients").
                accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
    @Test
    void GetClientById()throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/clients/3").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
    @Test
    void CreateClient() throws Exception{
        String names="Sergio653";
        String lastNames="Guanilo22";
        String address="San Miguel";
        Long cellphoneNumber=123456789L;
        int averageResponsibility=1;
        int responseTime=60;
        double rate=4.5;
        String imagePath="url";
        Long userId=4L;
        CreateClientResource createClientResource=new CreateClientResource(names,
                lastNames,address,cellphoneNumber,
                averageResponsibility,responseTime,rate,imagePath,userId);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:8080/api/v1/clients").accept(MediaType.APPLICATION_JSON).content(objectmapper.writeValueAsString(createClientResource)).contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        System. out.println(mvcResult.getResponse());
        Assertions.assertEquals(200, status);
    }
    @Test
    void DeleteClient() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/v1/clients/4").
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andReturn();
       /*String status = mvcResult.getResolvedException().getMessage();
        Assertions.assertEquals("Client with id 5 not found", status);*/
        int status=mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
}

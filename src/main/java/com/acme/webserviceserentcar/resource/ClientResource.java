package com.acme.webserviceserentcar.resource;

import com.acme.webserviceserentcar.domain.model.entity.Plan;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResource {
    private Long id;
    private String names;
    private String lastNames;
    private String address;
    private Long cellphoneNumber;
    private int averageResponsibility;
    private int responseTime;
    private double rate;
    private Plan plan;
    private String email;
    private String password;
}

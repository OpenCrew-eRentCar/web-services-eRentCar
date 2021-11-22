package com.acme.webserviceserentcar.client.resource;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class ClientResource {
    private Long id;
    private String names;
    private String lastNames;
    private String address;
    private Long cellphoneNumber;
    private int averageResponsibility;
    private int responseTime;
    private double rate;
    private String imagePath;
    private Long planId;
    private Long UserId;
}

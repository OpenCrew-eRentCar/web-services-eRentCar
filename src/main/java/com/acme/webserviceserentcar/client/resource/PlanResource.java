package com.acme.webserviceserentcar.client.resource;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class PlanResource {
    private Long id;
    private String name;
    private List<String> benefits;
    private int price;
}

package com.acme.webserviceserentcar.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlanResource {
    private Long id;
    private String name;
    private List<String> benefits;
    private int price;
}

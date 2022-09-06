package com.acme.webserviceserentcar.client.resource;

import com.acme.webserviceserentcar.client.domain.model.enums.PlanName;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class PlanResource {
    private Long id;
    private PlanName name;
    private List<String> benefits;
    private int price;
}

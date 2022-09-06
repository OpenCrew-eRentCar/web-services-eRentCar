package com.acme.webserviceserentcar.client.resource.create;

import com.acme.webserviceserentcar.client.domain.model.enums.PlanName;
import com.acme.webserviceserentcar.shared.converter.StringListConverter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class CreatePlanResource {
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private PlanName name;

    @NotNull
    @Convert(converter = StringListConverter.class)
    private List<String> benefits;

    @NotNull
    private int price;
}

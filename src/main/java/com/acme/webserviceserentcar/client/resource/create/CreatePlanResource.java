package com.acme.webserviceserentcar.client.resource.create;

import com.acme.webserviceserentcar.shared.converter.StringListConverter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class CreatePlanResource {
    @NotNull
    @NotBlank
    @Size(max = 30)
    private String name;

    @NotNull
    @Convert(converter = StringListConverter.class)
    private List<String> benefits;

    @NotNull
    private int price;
}

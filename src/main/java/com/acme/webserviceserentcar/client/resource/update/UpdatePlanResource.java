package com.acme.webserviceserentcar.client.resource.update;

import com.acme.webserviceserentcar.shared.converter.StringListConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UpdatePlanResource {
    private long id;

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

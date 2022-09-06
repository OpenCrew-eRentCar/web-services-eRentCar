package com.acme.webserviceserentcar.client.resource.update;

import com.acme.webserviceserentcar.shared.converter.StringListConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UpdatePlanResource {
    @NotNull
    @Convert(converter = StringListConverter.class)
    private List<String> benefits;

    @NotNull
    private int price;
}

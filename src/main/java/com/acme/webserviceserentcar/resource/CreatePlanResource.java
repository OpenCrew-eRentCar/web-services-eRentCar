package com.acme.webserviceserentcar.resource;

import com.acme.webserviceserentcar.converter.StringListConverter;
import com.acme.webserviceserentcar.domain.model.entity.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Client> clients;
}

package com.acme.webserviceserentcar.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateClientResource {
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String names;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String lastNames;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String address;

    @NotNull
    @NotBlank
    private Long cellphoneNumber;

    @NotNull
    @NotBlank
    private int averageResponsibility;

    @NotNull
    @NotBlank
    private int responseTime;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;
}

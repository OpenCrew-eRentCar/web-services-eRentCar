package com.acme.webserviceserentcar.resource;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class CreateClientResource {
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
    private Long cellphoneNumber;

    @NotNull
    private int averageResponsibility;

    @NotNull
    private int responseTime;

    @NotNull
    private double rate;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;
}

package com.acme.webserviceserentcar.resource;

import com.acme.webserviceserentcar.domain.model.entity.Plan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "plain_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Plan plan;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;
}

package com.acme.webserviceserentcar.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "cars")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String address;

    @NotNull
    @NotBlank
    @Size(max = 10)
    private String brand;

    @NotNull
    private int year;

    @NotNull
    @NotBlank
    @Size(max = 10)
    private String model;

    @NotNull
    private int mileage;

    @NotNull
    private int seating;

    @NotNull
    private boolean manual;

    @NotNull
    private int carValueInDollars;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String extraInformation;

    @NotNull
    private int rate;

    @NotNull
    private int rentAmountDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    @JsonIgnore
    private Client client;

}

package com.acme.webserviceserentcar.client.domain.model.entity;

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
@Table(name = "clients")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Long cellphoneNumber;

    @NotNull
    private int averageResponsibility;

    @NotNull
    private int responseTime;

    @NotNull
    private double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    @JsonIgnore
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_id", referencedColumnName = "id")
    @JsonIgnore
    private Rent rent;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;
}

package com.acme.webserviceserentcar.domain.model.entity;

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
@Table(name = "rents")
public class Rent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 8)
    private String startDate;

    @NotNull
    @NotBlank
    @Size(max = 8)
    private String finishDate;

    @NotNull
    @NotBlank
    private int amount;

    @NotNull
    @NotBlank
    private double rate;
}

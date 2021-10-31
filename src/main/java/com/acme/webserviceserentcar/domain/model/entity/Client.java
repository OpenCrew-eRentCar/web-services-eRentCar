package com.acme.webserviceserentcar.domain.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "clients")
public class Client {
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
    @NotBlank
    private Long cellphoneNumber;

    @NotNull
    @NotBlank
    private int averageResponsibility;

    @NotNull
    @NotBlank
    private int responseTime;


    /*
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id, referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Plan plan;
    */

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;
}

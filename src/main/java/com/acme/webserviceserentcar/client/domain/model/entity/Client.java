package com.acme.webserviceserentcar.client.domain.model.entity;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "clients")
public class  Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 30)
    private String names;

    @Size(max = 30)
    private String lastNames;

    @NotNull
    private String address;

    @NotNull
    private Long cellphoneNumber;

    private int averageResponsibility;

    private int responseTime;

    private double rate;

    @NotNull
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    @JsonIgnore
    private Plan plan;

    @NotNull
    private Long userId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(
            targetEntity = Car.class,
            mappedBy = "client",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<Car> cars;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(
            targetEntity = Favourite.class,
            mappedBy = "client",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<Favourite> favourites;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(
            targetEntity = Rent.class,
            mappedBy = "car",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Rent> rents;
}

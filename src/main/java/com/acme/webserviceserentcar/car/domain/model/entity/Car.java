package com.acme.webserviceserentcar.car.domain.model.entity;

import com.acme.webserviceserentcar.car.domain.model.enums.CategoryOfCar;
import com.acme.webserviceserentcar.car.domain.model.enums.InsuranceType;
import com.acme.webserviceserentcar.car.domain.model.enums.MechanicConditions;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.shared.domain.model.converter.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
    private int year;

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

    @NotNull
    private int rentAmountKilometer;

    @NotNull
    private String licensePlate;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private InsuranceType insuranceType;

    @NotNull
    @Convert(converter = StringListConverter.class)
    private List<String> imagePath;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private CategoryOfCar category;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private MechanicConditions mechanicCondition;

    @NotNull
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "client_id",
            referencedColumnName = "id",
            nullable = false
    )
    @JsonIgnore
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "car_model_id",
            referencedColumnName = "id",
            nullable = false
    )
    private CarModel carModel;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(
            targetEntity = Favourite.class,
            mappedBy = "car",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Set<Favourite> favourites;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(
            targetEntity = Rent.class,
            mappedBy = "car",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Set<Rent> rents;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(
            targetEntity = CarComment.class,
            mappedBy = "car",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<CarComment> comments;
}

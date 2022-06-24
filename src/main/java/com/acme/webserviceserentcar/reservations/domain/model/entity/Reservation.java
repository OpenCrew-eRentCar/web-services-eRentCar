package com.acme.webserviceserentcar.reservations.domain.model.entity;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "client_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Client client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "rent_id",
            referencedColumnName = "id",
            nullable = false
    )
    @JsonIgnore
    private Rent rent;
}

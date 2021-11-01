package com.acme.webserviceserentcar.domain.model.entity;

import com.acme.webserviceserentcar.converter.StringListConverter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String name;

    @NotNull
    @Convert(converter = StringListConverter.class)
    private List<String> benefits;

    @NotNull
    private int price;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Client> clients;
}

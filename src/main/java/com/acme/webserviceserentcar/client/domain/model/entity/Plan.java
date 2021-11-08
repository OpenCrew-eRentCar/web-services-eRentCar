package com.acme.webserviceserentcar.client.domain.model.entity;

import com.acme.webserviceserentcar.shared.converter.StringListConverter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "plans")
public class Plan implements Serializable {
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
}

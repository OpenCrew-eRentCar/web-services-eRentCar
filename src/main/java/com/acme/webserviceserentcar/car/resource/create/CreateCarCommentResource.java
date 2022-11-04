package com.acme.webserviceserentcar.car.resource.create;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class CreateCarCommentResource {
    @NotNull
    @NotBlank
    private String comment;
}

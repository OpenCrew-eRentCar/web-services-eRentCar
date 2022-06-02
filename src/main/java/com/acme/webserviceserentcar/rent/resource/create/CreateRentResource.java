package com.acme.webserviceserentcar.rent.resource.create;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class CreateRentResource {
    @NotNull
    private Date startDate;

    @NotNull
    private Date finishDate;

    @NotNull
    private int amount;

    @NotNull
    private int rate;
}

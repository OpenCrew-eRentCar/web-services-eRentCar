package com.acme.webserviceserentcar.payment.domain.service.communication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePaymentResponse {
    private String clientSecret;
}

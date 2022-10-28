package com.acme.webserviceserentcar.payment.domain.service;

import com.acme.webserviceserentcar.payment.resource.CreatePayment;
import com.acme.webserviceserentcar.payment.domain.service.communication.CreatePaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {
    CreatePaymentResponse createPaymentIntent(CreatePayment createPayment) throws StripeException;
}

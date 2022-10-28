package com.acme.webserviceserentcar.payment.service;

import com.acme.webserviceserentcar.payment.resource.CreatePayment;
import com.acme.webserviceserentcar.payment.domain.service.PaymentService;
import com.acme.webserviceserentcar.payment.domain.service.communication.CreatePaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public CreatePaymentResponse createPaymentIntent(CreatePayment createPayment) throws StripeException {
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("pen")
                .setAmount(createPayment.getAmount() * 100L)
                .build();

        PaymentIntent intent = PaymentIntent.create(createParams);
        return new CreatePaymentResponse(intent.getClientSecret());
    }
}

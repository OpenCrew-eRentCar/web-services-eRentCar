package com.acme.webserviceserentcar.payment.api;

import com.acme.webserviceserentcar.payment.resource.CreatePayment;
import com.acme.webserviceserentcar.payment.domain.service.PaymentService;
import com.acme.webserviceserentcar.payment.domain.service.communication.CreatePaymentResponse;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Create Payment Intent", description = "Create Payment Intent", tags = {"Payments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment Intent Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatePaymentResponse.class)
                    ))
    })
    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {
        return paymentService.createPaymentIntent(createPayment);
    }
}

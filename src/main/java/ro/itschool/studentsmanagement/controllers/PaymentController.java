package ro.itschool.studentsmanagement.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import ro.itschool.studentsmanagement.services.payment.AbstractPaymentService;

@RestController
public class PaymentController {

    private final AbstractPaymentService paymentService;

    public PaymentController(@Qualifier("payment") AbstractPaymentService paymentService) {
        this.paymentService = paymentService;
    }
}

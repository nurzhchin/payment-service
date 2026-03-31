package com.payment.controller;

import com.payment.model.Payment;
import com.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/payments")
    public ResponseEntity<?> createPayment(@Valid @RequestBody Payment payment) {
        Payment created = service.createPayment(payment);
        return ResponseEntity.ok(new PaymentResponse(created.getId().toString(), created.getStatus().name()));
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<?> getPayment(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getPayment(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Payment not found");
        }
    }

    @PostMapping("/payments/{id}/confirm")
    public ResponseEntity<?> confirmPayment(@PathVariable Long id) {
        try {
            Payment payment = service.confirmPayment(id);
            return ResponseEntity.ok(new PaymentResponse(payment.getId().toString(), payment.getStatus().name()));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("NOT_FOUND")) return ResponseEntity.status(404).body("Payment not found");
            return ResponseEntity.status(400).body("Payment cannot be confirmed");
        }
    }

    @PostMapping("/payments/{id}/cancel")
    public ResponseEntity<?> cancelPayment(@PathVariable Long id) {
        try {
            Payment payment = service.cancelPayment(id);
            return ResponseEntity.ok(new PaymentResponse(payment.getId().toString(), payment.getStatus().name()));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("NOT_FOUND")) return ResponseEntity.status(404).body("Payment not found");
            return ResponseEntity.status(400).body("Payment cannot be cancelled");
        }
    }

    @GetMapping("/clients/{clientId}/payments")
    public ResponseEntity<?> getClientPayments(@PathVariable String clientId) {
        try {
            return ResponseEntity.ok(service.getClientPayments(clientId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Client not found");
        }
    }

    record PaymentResponse(String paymentId, String status) {}
}
package com.payment;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment createPayment(Payment payment) {
        payment.setStatus("PENDING");
        return repository.save(payment);
    }

    public Payment getPayment(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND"));
    }

    public Payment confirmPayment(Long id) {
        Payment payment = getPayment(id);
        if (!payment.getStatus().equals("PENDING")) {
            throw new RuntimeException("BAD_REQUEST");
        }
        payment.setStatus("CONFIRMED");
        return repository.save(payment);
    }

    public Payment cancelPayment(Long id) {
        Payment payment = getPayment(id);
        if (!payment.getStatus().equals("PENDING")) {
            throw new RuntimeException("BAD_REQUEST");
        }
        payment.setStatus("CANCELED");
        return repository.save(payment);
    }

    public List<Payment> getClientPayments(String clientId) {
        List<Payment> payments = repository.findByClientId(clientId);
        if (payments.isEmpty()) {
            throw new RuntimeException("NOT_FOUND");
        }
        return payments;
    }
}
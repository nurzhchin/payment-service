package com.payment.service;

import com.payment.model.Payment;
import com.payment.model.PaymentStatus;
import com.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import com.payment.exception.PaymentNotFoundException;
import com.payment.exception.PaymentStateException;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment createPayment(Payment payment) {
        payment.setStatus(PaymentStatus.PENDING);
        return repository.save(payment);
    }

    public Payment getPayment(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));    }

    public Payment confirmPayment(Long id) {
        Payment payment = getPayment(id);
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new PaymentStateException(payment.getStatus().name());        }
        payment.setStatus(PaymentStatus.CONFIRMED);
        return repository.save(payment);
    }

    public Payment cancelPayment(Long id) {
        Payment payment = getPayment(id);
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new PaymentStateException(payment.getStatus().name());        }
        payment.setStatus(PaymentStatus.CANCELED);;
        return repository.save(payment);
    }

    public List<Payment> getClientPayments(String clientId) {
        List<Payment> payments = repository.findByClientId(clientId);
        if (payments.isEmpty()) {
            throw new RuntimeException("Client not found: " + clientId);       }
        return payments;
    }
}
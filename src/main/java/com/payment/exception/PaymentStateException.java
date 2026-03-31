package com.payment.exception;

public class PaymentStateException extends RuntimeException {
    public PaymentStateException(String status) {
        super("Payment cannot be changed from status: " + status);
    }
}
package com.payment;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Double amount;

    @NotNull
    @Pattern(regexp = "KZT|USD|EUR|RUB")
    private String currency;

    private String description;

    @NotNull
    private String clientId;

    private String status = "PENDING";

    public Long getId() { return id; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
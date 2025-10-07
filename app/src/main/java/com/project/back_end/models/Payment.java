package com.project.back_end.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "payments")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    @NotNull(message = "Appointment is required")
    @JsonProperty("appointment")
    private Appointment appointment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "Patient is required")
    @JsonProperty("patient")
    private Patient patient;
    
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.00", message = "Amount must be positive")
    @JsonProperty("amount")
    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    @NotNull(message = "Payment method is required")
    @JsonProperty("paymentMethod")
    private PaymentMethod paymentMethod;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    @JsonProperty("paymentStatus")
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    
    @Column(name = "transaction_id", unique = true, length = 100)
    @Size(max = 100, message = "Transaction ID must not exceed 100 characters")
    @JsonProperty("transactionId")
    private String transactionId;
    
    @CreationTimestamp
    @Column(name = "payment_date")
    @JsonProperty("paymentDate")
    private Timestamp paymentDate;
    
    @Column(name = "insurance_claim_number", length = 100)
    @Size(max = 100, message = "Insurance claim number must not exceed 100 characters")
    @JsonProperty("insuranceClaimNumber")
    private String insuranceClaimNumber;
    
    @Column(name = "copay_amount", precision = 10, scale = 2)
    @DecimalMin(value = "0.00", message = "Copay amount must be positive")
    @JsonProperty("copayAmount")
    private BigDecimal copayAmount = BigDecimal.ZERO;
    
    @Column(name = "insurance_coverage_amount", precision = 10, scale = 2)
    @DecimalMin(value = "0.00", message = "Insurance coverage amount must be positive")
    @JsonProperty("insuranceCoverageAmount")
    private BigDecimal insuranceCoverageAmount = BigDecimal.ZERO;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    @JsonProperty("notes")
    private String notes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by")
    @JsonProperty("processedBy")
    private Admin processedBy;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private Timestamp createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updatedAt")
    private Timestamp updatedAt;
    
    // Enums
    public enum PaymentMethod {
        CASH("Cash"),
        CREDIT_CARD("Credit Card"),
        DEBIT_CARD("Debit Card"),
        INSURANCE("Insurance"),
        ONLINE("Online");
        
        private final String displayName;
        
        PaymentMethod(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum PaymentStatus {
        PENDING("Pending"),
        COMPLETED("Completed"),
        FAILED("Failed"),
        REFUNDED("Refunded");
        
        private final String displayName;
        
        PaymentStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public Payment() {}
    
    public Payment(Appointment appointment, Patient patient, BigDecimal amount, PaymentMethod paymentMethod) {
        this.appointment = appointment;
        this.patient = patient;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Appointment getAppointment() {
        return appointment;
    }
    
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public Timestamp getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public String getInsuranceClaimNumber() {
        return insuranceClaimNumber;
    }
    
    public void setInsuranceClaimNumber(String insuranceClaimNumber) {
        this.insuranceClaimNumber = insuranceClaimNumber;
    }
    
    public BigDecimal getCopayAmount() {
        return copayAmount;
    }
    
    public void setCopayAmount(BigDecimal copayAmount) {
        this.copayAmount = copayAmount;
    }
    
    public BigDecimal getInsuranceCoverageAmount() {
        return insuranceCoverageAmount;
    }
    
    public void setInsuranceCoverageAmount(BigDecimal insuranceCoverageAmount) {
        this.insuranceCoverageAmount = insuranceCoverageAmount;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Admin getProcessedBy() {
        return processedBy;
    }
    
    public void setProcessedBy(Admin processedBy) {
        this.processedBy = processedBy;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Helper methods
    public BigDecimal getTotalPaidByPatient() {
        return copayAmount.add(amount.subtract(insuranceCoverageAmount));
    }
    
    public boolean isFullyPaid() {
        return paymentStatus == PaymentStatus.COMPLETED;
    }
    
    public boolean isInsuranceClaim() {
        return paymentMethod == PaymentMethod.INSURANCE;
    }
    
    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
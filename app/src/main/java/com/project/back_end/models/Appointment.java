package com.project.back_end.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"doctor_id", "appointment_date", "appointment_time"})
       })
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Doctor is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonProperty("doctor")
    private Doctor doctor;

    @NotNull(message = "Patient is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonProperty("patient")
    private Patient patient;

    @NotNull(message = "Clinic location is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_location_id", nullable = false)
    @JsonProperty("clinicLocation")
    private ClinicLocation clinicLocation;

    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment date must be in the future")
    @Column(name = "appointment_date", nullable = false)
    @JsonProperty("appointmentDate")
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required")
    @Column(name = "appointment_time", nullable = false)
    @JsonProperty("appointmentTime")
    private LocalTime appointmentTime;

    @NotNull(message = "Duration is required")
    @Min(value = 15, message = "Appointment duration must be at least 15 minutes")
    @Max(value = 480, message = "Appointment duration cannot exceed 8 hours")
    @Column(name = "duration_minutes", nullable = false)
    @JsonProperty("durationMinutes")
    private Integer durationMinutes = 60;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonProperty("status")
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @NotNull(message = "Appointment type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_type", nullable = false)
    @JsonProperty("appointmentType")
    private AppointmentType appointmentType;

    @Size(max = 500, message = "Chief complaint cannot exceed 500 characters")
    @Column(name = "chief_complaint", columnDefinition = "TEXT")
    @JsonProperty("chiefComplaint")
    private String chiefComplaint;

    @Size(max = 1000, message = "Preparation instructions cannot exceed 1000 characters")
    @Column(name = "preparation_instructions", columnDefinition = "TEXT")
    @JsonProperty("preparationInstructions")
    private String preparationInstructions;

    @Column(columnDefinition = "TEXT")
    @JsonProperty("notes")
    private String notes;

    @Column(columnDefinition = "TEXT")
    @JsonProperty("diagnosis")
    private String diagnosis;

    @Enumerated(EnumType.STRING)
    @Column(name = "booked_by")
    @JsonProperty("bookedBy")
    private BookedBy bookedBy = BookedBy.PATIENT;

    @Column(name = "confirmation_sent")
    @JsonProperty("confirmationSent")
    private Boolean confirmationSent = false;

    @Column(name = "reminder_sent")
    @JsonProperty("reminderSent")
    private Boolean reminderSent = false;

    @Column(name = "created_at", updatable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "cancelled_at")
    @JsonProperty("cancelledAt")
    private LocalDateTime cancelledAt;

    @Size(max = 500, message = "Cancellation reason cannot exceed 500 characters")
    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    @JsonProperty("cancellationReason")
    private String cancellationReason;

    // Enums
    public enum AppointmentStatus {
        SCHEDULED("Scheduled"),
        CONFIRMED("Confirmed"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled"),
        NO_SHOW("No-Show");

        private final String displayName;

        AppointmentStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum AppointmentType {
        CONSULTATION("Consultation"),
        FOLLOW_UP("Follow-up"),
        EMERGENCY("Emergency"),
        ROUTINE_CHECK_UP("Routine Check-up");

        private final String displayName;

        AppointmentType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum BookedBy {
        PATIENT("Patient"),
        ADMIN("Admin"),
        DOCTOR("Doctor");

        private final String displayName;

        BookedBy(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructors
    public Appointment() {
    }

    public Appointment(Doctor doctor, Patient patient, LocalDate appointmentDate, 
                      LocalTime appointmentTime, AppointmentType appointmentType) {
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentType = appointmentType;
    }

    // JPA lifecycle methods
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper methods
    public LocalDateTime getAppointmentDateTime() {
        if (appointmentDate != null && appointmentTime != null) {
            return LocalDateTime.of(appointmentDate, appointmentTime);
        }
        return null;
    }

    public LocalTime getEndTime() {
        if (appointmentTime != null && durationMinutes != null) {
            return appointmentTime.plusMinutes(durationMinutes);
        }
        return null;
    }

    public LocalDateTime getEndDateTime() {
        if (appointmentDate != null && appointmentTime != null && durationMinutes != null) {
            return LocalDateTime.of(appointmentDate, appointmentTime).plusMinutes(durationMinutes);
        }
        return null;
    }

    public boolean isUpcoming() {
        LocalDateTime appointmentDateTime = getAppointmentDateTime();
        return appointmentDateTime != null && appointmentDateTime.isAfter(LocalDateTime.now());
    }

    public boolean isPast() {
        LocalDateTime appointmentDateTime = getAppointmentDateTime();
        return appointmentDateTime != null && appointmentDateTime.isBefore(LocalDateTime.now());
    }

    public boolean canBeCancelled() {
        return status == AppointmentStatus.SCHEDULED || status == AppointmentStatus.CONFIRMED;
    }

    public void cancel(String reason) {
        if (canBeCancelled()) {
            this.status = AppointmentStatus.CANCELLED;
            this.cancelledAt = LocalDateTime.now();
            this.cancellationReason = reason;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public ClinicLocation getClinicLocation() {
        return clinicLocation;
    }

    public void setClinicLocation(ClinicLocation clinicLocation) {
        this.clinicLocation = clinicLocation;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getPreparationInstructions() {
        return preparationInstructions;
    }

    public void setPreparationInstructions(String preparationInstructions) {
        this.preparationInstructions = preparationInstructions;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public BookedBy getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(BookedBy bookedBy) {
        this.bookedBy = bookedBy;
    }

    public Boolean getConfirmationSent() {
        return confirmationSent;
    }

    public void setConfirmationSent(Boolean confirmationSent) {
        this.confirmationSent = confirmationSent;
    }

    public Boolean getReminderSent() {
        return reminderSent;
    }

    public void setReminderSent(Boolean reminderSent) {
        this.reminderSent = reminderSent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", doctor=" + (doctor != null ? doctor.getFullName() : "null") +
                ", patient=" + (patient != null ? patient.getFullName() : "null") +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", status=" + status +
                ", appointmentType=" + appointmentType +
                '}';
    }
}



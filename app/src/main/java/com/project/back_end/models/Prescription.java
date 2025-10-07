package com.project.back_end.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "prescriptions")
public class Prescription {

    @Id
    private String id;

    @NotNull(message = "Appointment ID is required")
    @JsonProperty("appointmentId")
    private Long appointmentId;

    @NotNull(message = "Patient ID is required")
    @JsonProperty("patientId")
    private Long patientId;

    @NotNull(message = "Doctor ID is required")
    @JsonProperty("doctorId")
    private Long doctorId;

    @NotNull(message = "Patient name is required")
    @Size(min = 2, max = 100, message = "Patient name must be between 2 and 100 characters")
    @JsonProperty("patientName")
    private String patientName;

    @NotNull(message = "Doctor name is required")
    @Size(min = 2, max = 100, message = "Doctor name must be between 2 and 100 characters")
    @JsonProperty("doctorName")
    private String doctorName;

    @NotNull(message = "Prescription date is required")
    @JsonProperty("prescriptionDate")
    private LocalDateTime prescriptionDate;

    @NotNull(message = "At least one medication is required")
    @Size(min = 1, message = "At least one medication must be prescribed")
    @JsonProperty("medications")
    private List<Medication> medications = new ArrayList<>();

    @JsonProperty("pharmacy")
    private Pharmacy pharmacy;

    @Size(max = 1000, message = "Doctor notes cannot exceed 1000 characters")
    @JsonProperty("doctorNotes")
    private String doctorNotes;

    @JsonProperty("allergies")
    private List<String> allergies = new ArrayList<>();

    @JsonProperty("interactions")
    private List<String> interactions = new ArrayList<>();

    @NotNull(message = "Status is required")
    @JsonProperty("status")
    private PrescriptionStatus status = PrescriptionStatus.ACTIVE;

    @JsonProperty("digitalSignature")
    private String digitalSignature;

    @JsonProperty("metadata")
    private PrescriptionMetadata metadata;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    // Nested classes for complex fields
    public static class Medication {
        @NotNull(message = "Medication name is required")
        @Size(min = 2, max = 100, message = "Medication name must be between 2 and 100 characters")
        @JsonProperty("name")
        private String name;

        @NotNull(message = "Dosage is required")
        @Size(min = 1, max = 50, message = "Dosage must be between 1 and 50 characters")
        @JsonProperty("dosage")
        private String dosage;

        @NotNull(message = "Frequency is required")
        @Size(min = 1, max = 100, message = "Frequency must be between 1 and 100 characters")
        @JsonProperty("frequency")
        private String frequency;

        @NotNull(message = "Duration is required")
        @Size(min = 1, max = 50, message = "Duration must be between 1 and 50 characters")
        @JsonProperty("duration")
        private String duration;

        @Size(max = 200, message = "Instructions cannot exceed 200 characters")
        @JsonProperty("instructions")
        private String instructions;

        @Min(value = 1, message = "Quantity must be at least 1")
        @JsonProperty("quantity")
        private Integer quantity;

        @Min(value = 0, message = "Refills allowed cannot be negative")
        @Max(value = 10, message = "Refills allowed cannot exceed 10")
        @JsonProperty("refillsAllowed")
        private Integer refillsAllowed = 0;

        @Min(value = 0, message = "Refills used cannot be negative")
        @JsonProperty("refillsUsed")
        private Integer refillsUsed = 0;

        // Constructors
        public Medication() {}

        public Medication(String name, String dosage, String frequency, String duration, Integer quantity) {
            this.name = name;
            this.dosage = dosage;
            this.frequency = frequency;
            this.duration = duration;
            this.quantity = quantity;
        }

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDosage() { return dosage; }
        public void setDosage(String dosage) { this.dosage = dosage; }

        public String getFrequency() { return frequency; }
        public void setFrequency(String frequency) { this.frequency = frequency; }

        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }

        public String getInstructions() { return instructions; }
        public void setInstructions(String instructions) { this.instructions = instructions; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        public Integer getRefillsAllowed() { return refillsAllowed; }
        public void setRefillsAllowed(Integer refillsAllowed) { this.refillsAllowed = refillsAllowed; }

        public Integer getRefillsUsed() { return refillsUsed; }
        public void setRefillsUsed(Integer refillsUsed) { this.refillsUsed = refillsUsed; }

        public boolean hasRefillsRemaining() {
            return refillsUsed < refillsAllowed;
        }
    }

    public static class Pharmacy {
        @Size(max = 100, message = "Pharmacy name cannot exceed 100 characters")
        @JsonProperty("name")
        private String name;

        @Size(max = 200, message = "Pharmacy address cannot exceed 200 characters")
        @JsonProperty("address")
        private String address;

        @Pattern(regexp = "^[0-9+\\-\\s()]{10,20}$", message = "Please provide a valid phone number")
        @JsonProperty("phone")
        private String phone;

        @Size(max = 100, message = "Pharmacist contact cannot exceed 100 characters")
        @JsonProperty("pharmacistContact")
        private String pharmacistContact;

        // Constructors
        public Pharmacy() {}

        public Pharmacy(String name, String address, String phone) {
            this.name = name;
            this.address = address;
            this.phone = phone;
        }

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getPharmacistContact() { return pharmacistContact; }
        public void setPharmacistContact(String pharmacistContact) { this.pharmacistContact = pharmacistContact; }
    }

    public static class PrescriptionMetadata {
        @Size(max = 50, message = "Prescription number cannot exceed 50 characters")
        @JsonProperty("prescriptionNumber")
        private String prescriptionNumber;

        @JsonProperty("tags")
        private List<String> tags = new ArrayList<>();

        @JsonProperty("priority")
        private PrescriptionPriority priority = PrescriptionPriority.NORMAL;

        @JsonProperty("followUpRequired")
        private Boolean followUpRequired = false;

        @JsonProperty("followUpDate")
        private LocalDateTime followUpDate;

        // Constructors
        public PrescriptionMetadata() {}

        // Getters and Setters
        public String getPrescriptionNumber() { return prescriptionNumber; }
        public void setPrescriptionNumber(String prescriptionNumber) { this.prescriptionNumber = prescriptionNumber; }

        public List<String> getTags() { return tags; }
        public void setTags(List<String> tags) { this.tags = tags; }

        public PrescriptionPriority getPriority() { return priority; }
        public void setPriority(PrescriptionPriority priority) { this.priority = priority; }

        public Boolean getFollowUpRequired() { return followUpRequired; }
        public void setFollowUpRequired(Boolean followUpRequired) { this.followUpRequired = followUpRequired; }

        public LocalDateTime getFollowUpDate() { return followUpDate; }
        public void setFollowUpDate(LocalDateTime followUpDate) { this.followUpDate = followUpDate; }
    }

    // Enums
    public enum PrescriptionStatus {
        ACTIVE("Active"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled"),
        EXPIRED("Expired");

        private final String displayName;

        PrescriptionStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum PrescriptionPriority {
        LOW("Low"),
        NORMAL("Normal"),
        HIGH("High"),
        URGENT("Urgent");

        private final String displayName;

        PrescriptionPriority(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructors
    public Prescription() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Prescription(Long appointmentId, Long patientId, Long doctorId, String patientName, String doctorName) {
        this();
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.prescriptionDate = LocalDateTime.now();
    }

    // Helper methods
    public void addMedication(Medication medication) {
        if (this.medications == null) {
            this.medications = new ArrayList<>();
        }
        this.medications.add(medication);
    }

    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return status == PrescriptionStatus.ACTIVE;
    }

    public int getTotalMedications() {
        return medications != null ? medications.size() : 0;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDateTime getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDateTime prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<String> interactions) {
        this.interactions = interactions;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public PrescriptionMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(PrescriptionMetadata metadata) {
        this.metadata = metadata;
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

    @Override
    public String toString() {
        return "Prescription{" +
                "id='" + id + '\'' +
                ", appointmentId=" + appointmentId +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", prescriptionDate=" + prescriptionDate +
                ", medicationsCount=" + getTotalMedications() +
                ", status=" + status +
                '}';
    }
}

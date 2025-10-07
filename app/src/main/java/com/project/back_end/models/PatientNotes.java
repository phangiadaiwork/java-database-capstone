package com.project.back_end.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "patient_notes")
public class PatientNotes {
    
    @Id
    @JsonProperty("_id")
    private String id;
    
    @Field("patientId")
    @NotNull(message = "Patient ID is required")
    @JsonProperty("patientId")
    private Integer patientId;
    
    @Field("doctorId")
    @NotNull(message = "Doctor ID is required")
    @JsonProperty("doctorId")
    private Integer doctorId;
    
    @Field("appointmentId")
    @NotNull(message = "Appointment ID is required")
    @JsonProperty("appointmentId")
    private Integer appointmentId;
    
    @Field("noteDate")
    @NotNull(message = "Note date is required")
    @JsonProperty("noteDate")
    private LocalDateTime noteDate;
    
    @Field("noteType")
    @NotBlank(message = "Note type is required")
    @JsonProperty("noteType")
    private String noteType;
    
    @Field("chiefComplaint")
    @JsonProperty("chiefComplaint")
    private String chiefComplaint;
    
    @Field("historyOfPresentIllness")
    @JsonProperty("historyOfPresentIllness")
    private String historyOfPresentIllness;
    
    @Field("physicalExamination")
    @JsonProperty("physicalExamination")
    private PhysicalExamination physicalExamination;
    
    @Field("assessment")
    @JsonProperty("assessment")
    private String assessment;
    
    @Field("plan")
    @JsonProperty("plan")
    private List<String> plan;
    
    @Field("doctorNotes")
    @JsonProperty("doctorNotes")
    private String doctorNotes;
    
    @Field("patientConcerns")
    @JsonProperty("patientConcerns")
    private List<String> patientConcerns;
    
    @Field("followUpInstructions")
    @JsonProperty("followUpInstructions")
    private String followUpInstructions;
    
    @Field("accessLog")
    @JsonProperty("accessLog")
    private List<AccessLog> accessLog;
    
    @Field("metadata")
    @JsonProperty("metadata")
    private NotesMetadata metadata;
    
    @Field("createdAt")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    
    @Field("updatedAt")
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
    
    // Constructors
    public PatientNotes() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Inner classes for nested objects
    public static class PhysicalExamination {
        @JsonProperty("vitalSigns")
        private VitalSigns vitalSigns;
        
        @JsonProperty("generalAppearance")
        private String generalAppearance;
        
        @JsonProperty("systems")
        private Map<String, String> systems;
        
        // Constructors, getters and setters
        public PhysicalExamination() {}
        
        public VitalSigns getVitalSigns() { return vitalSigns; }
        public void setVitalSigns(VitalSigns vitalSigns) { this.vitalSigns = vitalSigns; }
        
        public String getGeneralAppearance() { return generalAppearance; }
        public void setGeneralAppearance(String generalAppearance) { this.generalAppearance = generalAppearance; }
        
        public Map<String, String> getSystems() { return systems; }
        public void setSystems(Map<String, String> systems) { this.systems = systems; }
    }
    
    public static class VitalSigns {
        @JsonProperty("bloodPressure")
        private String bloodPressure;
        
        @JsonProperty("heartRate")
        private Integer heartRate;
        
        @JsonProperty("temperature")
        private String temperature;
        
        @JsonProperty("respiratoryRate")
        private Integer respiratoryRate;
        
        @JsonProperty("oxygenSaturation")
        private String oxygenSaturation;
        
        @JsonProperty("weight")
        private String weight;
        
        @JsonProperty("height")
        private String height;
        
        @JsonProperty("bmi")
        private Double bmi;
        
        // Constructors, getters and setters
        public VitalSigns() {}
        
        public String getBloodPressure() { return bloodPressure; }
        public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }
        
        public Integer getHeartRate() { return heartRate; }
        public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }
        
        public String getTemperature() { return temperature; }
        public void setTemperature(String temperature) { this.temperature = temperature; }
        
        public Integer getRespiratoryRate() { return respiratoryRate; }
        public void setRespiratoryRate(Integer respiratoryRate) { this.respiratoryRate = respiratoryRate; }
        
        public String getOxygenSaturation() { return oxygenSaturation; }
        public void setOxygenSaturation(String oxygenSaturation) { this.oxygenSaturation = oxygenSaturation; }
        
        public String getWeight() { return weight; }
        public void setWeight(String weight) { this.weight = weight; }
        
        public String getHeight() { return height; }
        public void setHeight(String height) { this.height = height; }
        
        public Double getBmi() { return bmi; }
        public void setBmi(Double bmi) { this.bmi = bmi; }
    }
    
    public static class AccessLog {
        @JsonProperty("accessedBy")
        private Integer accessedBy;
        
        @JsonProperty("accessedAt")
        private LocalDateTime accessedAt;
        
        @JsonProperty("accessType")
        private String accessType;
        
        @JsonProperty("ipAddress")
        private String ipAddress;
        
        // Constructors, getters and setters
        public AccessLog() {}
        
        public Integer getAccessedBy() { return accessedBy; }
        public void setAccessedBy(Integer accessedBy) { this.accessedBy = accessedBy; }
        
        public LocalDateTime getAccessedAt() { return accessedAt; }
        public void setAccessedAt(LocalDateTime accessedAt) { this.accessedAt = accessedAt; }
        
        public String getAccessType() { return accessType; }
        public void setAccessType(String accessType) { this.accessType = accessType; }
        
        public String getIpAddress() { return ipAddress; }
        public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    }
    
    public static class NotesMetadata {
        @JsonProperty("appointmentDuration")
        private Integer appointmentDuration;
        
        @JsonProperty("confidentialityLevel")
        private String confidentialityLevel;
        
        @JsonProperty("shareWithPatient")
        private Boolean shareWithPatient;
        
        @JsonProperty("billingCodes")
        private List<String> billingCodes;
        
        @JsonProperty("tags")
        private List<String> tags;
        
        // Constructors, getters and setters
        public NotesMetadata() {}
        
        public Integer getAppointmentDuration() { return appointmentDuration; }
        public void setAppointmentDuration(Integer appointmentDuration) { this.appointmentDuration = appointmentDuration; }
        
        public String getConfidentialityLevel() { return confidentialityLevel; }
        public void setConfidentialityLevel(String confidentialityLevel) { this.confidentialityLevel = confidentialityLevel; }
        
        public Boolean getShareWithPatient() { return shareWithPatient; }
        public void setShareWithPatient(Boolean shareWithPatient) { this.shareWithPatient = shareWithPatient; }
        
        public List<String> getBillingCodes() { return billingCodes; }
        public void setBillingCodes(List<String> billingCodes) { this.billingCodes = billingCodes; }
        
        public List<String> getTags() { return tags; }
        public void setTags(List<String> tags) { this.tags = tags; }
    }
    
    // Main getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }
    
    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }
    
    public Integer getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Integer appointmentId) { this.appointmentId = appointmentId; }
    
    public LocalDateTime getNoteDate() { return noteDate; }
    public void setNoteDate(LocalDateTime noteDate) { this.noteDate = noteDate; }
    
    public String getNoteType() { return noteType; }
    public void setNoteType(String noteType) { this.noteType = noteType; }
    
    public String getChiefComplaint() { return chiefComplaint; }
    public void setChiefComplaint(String chiefComplaint) { this.chiefComplaint = chiefComplaint; }
    
    public String getHistoryOfPresentIllness() { return historyOfPresentIllness; }
    public void setHistoryOfPresentIllness(String historyOfPresentIllness) { this.historyOfPresentIllness = historyOfPresentIllness; }
    
    public PhysicalExamination getPhysicalExamination() { return physicalExamination; }
    public void setPhysicalExamination(PhysicalExamination physicalExamination) { this.physicalExamination = physicalExamination; }
    
    public String getAssessment() { return assessment; }
    public void setAssessment(String assessment) { this.assessment = assessment; }
    
    public List<String> getPlan() { return plan; }
    public void setPlan(List<String> plan) { this.plan = plan; }
    
    public String getDoctorNotes() { return doctorNotes; }
    public void setDoctorNotes(String doctorNotes) { this.doctorNotes = doctorNotes; }
    
    public List<String> getPatientConcerns() { return patientConcerns; }
    public void setPatientConcerns(List<String> patientConcerns) { this.patientConcerns = patientConcerns; }
    
    public String getFollowUpInstructions() { return followUpInstructions; }
    public void setFollowUpInstructions(String followUpInstructions) { this.followUpInstructions = followUpInstructions; }
    
    public List<AccessLog> getAccessLog() { return accessLog; }
    public void setAccessLog(List<AccessLog> accessLog) { this.accessLog = accessLog; }
    
    public NotesMetadata getMetadata() { return metadata; }
    public void setMetadata(NotesMetadata metadata) { this.metadata = metadata; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
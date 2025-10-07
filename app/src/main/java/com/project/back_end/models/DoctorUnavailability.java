package com.project.back_end.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "doctor_unavailability")
public class DoctorUnavailability {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor is required")
    @JsonProperty("doctor")
    private Doctor doctor;
    
    @Column(name = "unavailable_date", nullable = false)
    @NotNull(message = "Unavailable date is required")
    @JsonProperty("unavailableDate")
    private Date unavailableDate;
    
    @Column(name = "start_time", nullable = false)
    @NotNull(message = "Start time is required")
    @JsonProperty("startTime")
    private Time startTime;
    
    @Column(name = "end_time", nullable = false)
    @NotNull(message = "End time is required")
    @JsonProperty("endTime")
    private Time endTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    @NotNull(message = "Reason is required")
    @JsonProperty("reason")
    private UnavailabilityReason reason;
    
    @Column(name = "reason_details", columnDefinition = "TEXT")
    @JsonProperty("reasonDetails")
    private String reasonDetails;
    
    @Column(name = "is_recurring")
    @JsonProperty("isRecurring")
    private Boolean isRecurring = false;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence_pattern")
    @JsonProperty("recurrencePattern")
    private RecurrencePattern recurrencePattern;
    
    @Column(name = "recurrence_end_date")
    @JsonProperty("recurrenceEndDate")
    private Date recurrenceEndDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "created_by")
    @JsonProperty("createdBy")
    private CreatedBy createdBy = CreatedBy.DOCTOR;
    
    @Column(name = "emergency_override_allowed")
    @JsonProperty("emergencyOverrideAllowed")
    private Boolean emergencyOverrideAllowed = false;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private Timestamp createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updatedAt")
    private Timestamp updatedAt;
    
    // Enums
    public enum UnavailabilityReason {
        VACATION("Vacation"),
        SICK_LEAVE("Sick Leave"),
        CONFERENCE("Conference"),
        PERSONAL("Personal"),
        EMERGENCY("Emergency"),
        OTHER("Other");
        
        private final String displayName;
        
        UnavailabilityReason(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum RecurrencePattern {
        DAILY("Daily"),
        WEEKLY("Weekly"),
        MONTHLY("Monthly"),
        YEARLY("Yearly");
        
        private final String displayName;
        
        RecurrencePattern(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum CreatedBy {
        DOCTOR("Doctor"),
        ADMIN("Admin");
        
        private final String displayName;
        
        CreatedBy(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public DoctorUnavailability() {}
    
    public DoctorUnavailability(Doctor doctor, Date unavailableDate, Time startTime, Time endTime, UnavailabilityReason reason) {
        this.doctor = doctor;
        this.unavailableDate = unavailableDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    public Date getUnavailableDate() {
        return unavailableDate;
    }
    
    public void setUnavailableDate(Date unavailableDate) {
        this.unavailableDate = unavailableDate;
    }
    
    public Time getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
    public Time getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    
    public UnavailabilityReason getReason() {
        return reason;
    }
    
    public void setReason(UnavailabilityReason reason) {
        this.reason = reason;
    }
    
    public String getReasonDetails() {
        return reasonDetails;
    }
    
    public void setReasonDetails(String reasonDetails) {
        this.reasonDetails = reasonDetails;
    }
    
    public Boolean getIsRecurring() {
        return isRecurring;
    }
    
    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }
    
    public RecurrencePattern getRecurrencePattern() {
        return recurrencePattern;
    }
    
    public void setRecurrencePattern(RecurrencePattern recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }
    
    public Date getRecurrenceEndDate() {
        return recurrenceEndDate;
    }
    
    public void setRecurrenceEndDate(Date recurrenceEndDate) {
        this.recurrenceEndDate = recurrenceEndDate;
    }
    
    public CreatedBy getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }
    
    public Boolean getEmergencyOverrideAllowed() {
        return emergencyOverrideAllowed;
    }
    
    public void setEmergencyOverrideAllowed(Boolean emergencyOverrideAllowed) {
        this.emergencyOverrideAllowed = emergencyOverrideAllowed;
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
    public boolean isCurrentlyActive() {
        Date today = new Date(System.currentTimeMillis());
        if (isRecurring != null && isRecurring) {
            return (recurrenceEndDate == null || !recurrenceEndDate.before(today));
        } else {
            return unavailableDate.equals(today);
        }
    }
    
    public boolean isAllDayUnavailable() {
        if (startTime != null && endTime != null) {
            // Check if it's approximately all day (e.g., 8+ hours)
            long diffInMillis = endTime.getTime() - startTime.getTime();
            long diffInHours = diffInMillis / (60 * 60 * 1000);
            return diffInHours >= 8;
        }
        return false;
    }
    
    public int getUnavailableDurationMinutes() {
        if (startTime != null && endTime != null) {
            long diffInMillis = endTime.getTime() - startTime.getTime();
            return (int) (diffInMillis / (60 * 1000));
        }
        return 0;
    }
    
    public boolean conflicts(Date date, Time time) {
        if (!unavailableDate.equals(date)) {
            return false;
        }
        return !time.before(startTime) && time.before(endTime);
    }
    
    @Override
    public String toString() {
        return "DoctorUnavailability{" +
                "id=" + id +
                ", unavailableDate=" + unavailableDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", reason=" + reason +
                ", isRecurring=" + isRecurring +
                '}';
    }
}
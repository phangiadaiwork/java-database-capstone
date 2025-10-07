package com.project.back_end.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "doctor_availability",
       uniqueConstraints = @UniqueConstraint(columnNames = {"doctor_id", "clinic_location_id", "day_of_week", "start_time"}))
public class DoctorAvailability {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor is required")
    @JsonProperty("doctor")
    private Doctor doctor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_location_id", nullable = false)
    @NotNull(message = "Clinic location is required")
    @JsonProperty("clinicLocation")
    private ClinicLocation clinicLocation;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    @NotNull(message = "Day of week is required")
    @JsonProperty("dayOfWeek")
    private DayOfWeek dayOfWeek;
    
    @Column(name = "start_time", nullable = false)
    @NotNull(message = "Start time is required")
    @JsonProperty("startTime")
    private Time startTime;
    
    @Column(name = "end_time", nullable = false)
    @NotNull(message = "End time is required")
    @JsonProperty("endTime")
    private Time endTime;
    
    @Column(name = "slot_duration_minutes")
    @Min(value = 15, message = "Slot duration must be at least 15 minutes")
    @Max(value = 480, message = "Slot duration must not exceed 480 minutes")
    @JsonProperty("slotDurationMinutes")
    private Integer slotDurationMinutes = 30;
    
    @Column(name = "is_available")
    @JsonProperty("isAvailable")
    private Boolean isAvailable = true;
    
    @Column(name = "effective_from", nullable = false)
    @NotNull(message = "Effective from date is required")
    @JsonProperty("effectiveFrom")
    private Date effectiveFrom;
    
    @Column(name = "effective_until")
    @JsonProperty("effectiveUntil")
    private Date effectiveUntil;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private Timestamp createdAt;
    
    // Enum
    public enum DayOfWeek {
        MONDAY("Monday"),
        TUESDAY("Tuesday"),
        WEDNESDAY("Wednesday"),
        THURSDAY("Thursday"),
        FRIDAY("Friday"),
        SATURDAY("Saturday"),
        SUNDAY("Sunday");
        
        private final String displayName;
        
        DayOfWeek(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public DoctorAvailability() {}
    
    public DoctorAvailability(Doctor doctor, ClinicLocation clinicLocation, DayOfWeek dayOfWeek, 
                            Time startTime, Time endTime, Date effectiveFrom) {
        this.doctor = doctor;
        this.clinicLocation = clinicLocation;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.effectiveFrom = effectiveFrom;
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
    
    public ClinicLocation getClinicLocation() {
        return clinicLocation;
    }
    
    public void setClinicLocation(ClinicLocation clinicLocation) {
        this.clinicLocation = clinicLocation;
    }
    
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
    
    public Integer getSlotDurationMinutes() {
        return slotDurationMinutes;
    }
    
    public void setSlotDurationMinutes(Integer slotDurationMinutes) {
        this.slotDurationMinutes = slotDurationMinutes;
    }
    
    public Boolean getIsAvailable() {
        return isAvailable;
    }
    
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    public Date getEffectiveFrom() {
        return effectiveFrom;
    }
    
    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }
    
    public Date getEffectiveUntil() {
        return effectiveUntil;
    }
    
    public void setEffectiveUntil(Date effectiveUntil) {
        this.effectiveUntil = effectiveUntil;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    // Helper methods
    public boolean isCurrentlyEffective() {
        Date today = new Date(System.currentTimeMillis());
        return (effectiveFrom == null || !effectiveFrom.after(today)) &&
               (effectiveUntil == null || !effectiveUntil.before(today)) &&
               (isAvailable != null && isAvailable);
    }
    
    public int getTotalAvailableMinutes() {
        if (startTime != null && endTime != null) {
            long diffInMillis = endTime.getTime() - startTime.getTime();
            return (int) (diffInMillis / (60 * 1000));
        }
        return 0;
    }
    
    public int getNumberOfSlots() {
        if (slotDurationMinutes != null && slotDurationMinutes > 0) {
            return getTotalAvailableMinutes() / slotDurationMinutes;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "DoctorAvailability{" +
                "id=" + id +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
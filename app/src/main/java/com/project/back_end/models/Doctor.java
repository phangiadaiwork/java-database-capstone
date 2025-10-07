package com.project.back_end.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(unique = true, nullable = false, length = 50)
    @JsonProperty("username")
    private String username;

    @NotNull(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    @JsonProperty("firstName")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    @JsonProperty("lastName")
    private String lastName;

    @NotNull(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(unique = true, nullable = false, length = 100)
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[0-9+\\-\\s()]{10,20}$", message = "Please provide a valid phone number")
    @Column(nullable = false, length = 20)
    @JsonProperty("phone")
    private String phone;

    @Pattern(regexp = "^[0-9+\\-\\s()]{10,20}$", message = "Please provide a valid public phone number")
    @Column(name = "phone_public", length = 20)
    @JsonProperty("phonePublic")
    private String phonePublic;

    @NotNull(message = "Specialization is required")
    @Size(min = 3, max = 100, message = "Specialization must be between 3 and 100 characters")
    @Column(nullable = false, length = 100)
    @JsonProperty("specialization")
    private String specialization;

    @NotNull(message = "License number is required")
    @Size(min = 5, max = 50, message = "License number must be between 5 and 50 characters")
    @Column(name = "license_number", unique = true, nullable = false, length = 50)
    @JsonProperty("licenseNumber")
    private String licenseNumber;

    @Min(value = 0, message = "Years of experience cannot be negative")
    @Max(value = 70, message = "Years of experience cannot exceed 70")
    @Column(name = "years_of_experience")
    @JsonProperty("yearsOfExperience")
    private Integer yearsOfExperience = 0;

    @Column(columnDefinition = "TEXT")
    @JsonProperty("education")
    private String education;

    @DecimalMin(value = "0.0", message = "Consultation fee cannot be negative")
    @Column(name = "consultation_fee", precision = 10, scale = 2)
    @JsonProperty("consultationFee")
    private BigDecimal consultationFee = BigDecimal.ZERO;

    @Size(max = 1000, message = "Bio cannot exceed 1000 characters")
    @Column(columnDefinition = "TEXT")
    @JsonProperty("bio")
    private String bio;

    @Column(name = "profile_image_url")
    @JsonProperty("profileImageUrl")
    private String profileImageUrl;

    @Column(name = "is_available")
    @JsonProperty("isAvailable")
    private Boolean isAvailable = true;

    @Column(name = "is_public")
    @JsonProperty("isPublic")
    private Boolean isPublic = true;

    @Column(name = "last_login")
    @JsonProperty("lastLogin")
    private LocalDateTime lastLogin;

    @Min(value = 15, message = "Session timeout must be at least 15 minutes")
    @Max(value = 480, message = "Session timeout cannot exceed 8 hours")
    @Column(name = "session_timeout_minutes")
    @JsonProperty("sessionTimeoutMinutes")
    private Integer sessionTimeoutMinutes = 30;

    @Column(name = "created_at", updatable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "doctor_available_times", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "time_slot")
    @JsonProperty("availableTimes")
    private List<String> availableTimes = new ArrayList<>();

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_location_id")
    @JsonProperty("clinicLocation")
    private ClinicLocation clinicLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonProperty("createdBy")
    private Admin createdBy;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DoctorAvailability> doctorAvailabilities = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DoctorUnavailability> doctorUnavailabilities = new ArrayList<>();

    // Constructors
    public Doctor() {
    }

    public Doctor(String username, String firstName, String lastName, String email, String password, 
                  String phone, String specialization, String licenseNumber) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
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
    public String getFullName() {
        return "Dr. " + firstName + " " + lastName;
    }

    public String getName() {
        return getFullName();
    }

    public String getSpecialty() {
        return specialization;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhonePublic() {
        return phonePublic;
    }

    public void setPhonePublic(String phonePublic) {
        this.phonePublic = phonePublic;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getSessionTimeoutMinutes() {
        return sessionTimeoutMinutes;
    }

    public void setSessionTimeoutMinutes(Integer sessionTimeoutMinutes) {
        this.sessionTimeoutMinutes = sessionTimeoutMinutes;
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

    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<String> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public ClinicLocation getClinicLocation() {
        return clinicLocation;
    }

    public void setClinicLocation(ClinicLocation clinicLocation) {
        this.clinicLocation = clinicLocation;
    }

    public Admin getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Admin createdBy) {
        this.createdBy = createdBy;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<DoctorAvailability> getDoctorAvailabilities() {
        return doctorAvailabilities;
    }

    public void setDoctorAvailabilities(List<DoctorAvailability> doctorAvailabilities) {
        this.doctorAvailabilities = doctorAvailabilities;
    }

    public List<DoctorUnavailability> getDoctorUnavailabilities() {
        return doctorUnavailabilities;
    }

    public void setDoctorUnavailabilities(List<DoctorUnavailability> doctorUnavailabilities) {
        this.doctorUnavailabilities = doctorUnavailabilities;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", specialization='" + specialization + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}


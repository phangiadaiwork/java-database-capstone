package com.project.back_end.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "clinic_locations")
public class ClinicLocation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;
    
    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Clinic name is required")
    @Size(max = 100, message = "Clinic name must not exceed 100 characters")
    @JsonProperty("name")
    private String name;
    
    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Address is required")
    @JsonProperty("address")
    private String address;
    
    @Column(name = "city", nullable = false, length = 50)
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City must not exceed 50 characters")
    @JsonProperty("city")
    private String city;
    
    @Column(name = "state", nullable = false, length = 50)
    @NotBlank(message = "State is required")
    @Size(max = 50, message = "State must not exceed 50 characters")
    @JsonProperty("state")
    private String state;
    
    @Column(name = "zip_code", nullable = false, length = 10)
    @NotBlank(message = "Zip code is required")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Invalid zip code format")
    @JsonProperty("zipCode")
    private String zipCode;
    
    @Column(name = "country", length = 50)
    @Size(max = 50, message = "Country must not exceed 50 characters")
    @JsonProperty("country")
    private String country = "USA";
    
    @Column(name = "phone", nullable = false, length = 20)
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    @JsonProperty("phone")
    private String phone;
    
    @Column(name = "email", length = 100)
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @JsonProperty("email")
    private String email;
    
    @Column(name = "operating_hours", columnDefinition = "JSON")
    @JsonProperty("operatingHours")
    private String operatingHours;
    
    @Column(name = "facilities", columnDefinition = "JSON")
    @JsonProperty("facilities")
    private String facilities;
    
    @Column(name = "capacity")
    @Min(value = 1, message = "Capacity must be at least 1")
    @JsonProperty("capacity")
    private Integer capacity = 50;
    
    @Column(name = "is_active")
    @JsonProperty("isActive")
    private Boolean isActive = true;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private Timestamp createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updatedAt")
    private Timestamp updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "clinicLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Doctor> doctors;
    
    @OneToMany(mappedBy = "clinicLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;
    
    @OneToMany(mappedBy = "clinicLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DoctorAvailability> doctorAvailabilities;
    
    // Constructors
    public ClinicLocation() {}
    
    public ClinicLocation(String name, String address, String city, String state, String zipCode, String phone) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getOperatingHours() {
        return operatingHours;
    }
    
    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }
    
    public String getFacilities() {
        return facilities;
    }
    
    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
    
    public List<Doctor> getDoctors() {
        return doctors;
    }
    
    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
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
    
    // Helper methods
    public String getFullAddress() {
        return String.format("%s, %s, %s %s, %s", address, city, state, zipCode, country);
    }
    
    public boolean isOperational() {
        return isActive != null && isActive;
    }
    
    @Override
    public String toString() {
        return "ClinicLocation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
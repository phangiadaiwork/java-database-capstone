package com.project.back_end.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "user_sessions")
public class UserSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;
    
    @Column(name = "session_id", unique = true, nullable = false, length = 255)
    @NotBlank(message = "Session ID is required")
    @Size(max = 255, message = "Session ID must not exceed 255 characters")
    @JsonProperty("sessionId")
    private String sessionId;
    
    @Column(name = "user_id", nullable = false)
    @NotNull(message = "User ID is required")
    @JsonProperty("userId")
    private Integer userId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    @NotNull(message = "User type is required")
    @JsonProperty("userType")
    private UserType userType;
    
    @Column(name = "ip_address", length = 45)
    @Size(max = 45, message = "IP address must not exceed 45 characters")
    @JsonProperty("ipAddress")
    private String ipAddress;
    
    @Column(name = "user_agent", columnDefinition = "TEXT")
    @JsonProperty("userAgent")
    private String userAgent;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private Timestamp createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_activity")
    @JsonProperty("lastActivity")
    private Timestamp lastActivity;
    
    @Column(name = "expires_at", nullable = false)
    @NotNull(message = "Expiration time is required")
    @Future(message = "Expiration time must be in the future")
    @JsonProperty("expiresAt")
    private Timestamp expiresAt;
    
    @Column(name = "is_active")
    @JsonProperty("isActive")
    private Boolean isActive = true;
    
    // Enum
    public enum UserType {
        PATIENT("Patient"),
        DOCTOR("Doctor"),
        ADMIN("Admin");
        
        private final String displayName;
        
        UserType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public UserSession() {}
    
    public UserSession(String sessionId, Integer userId, UserType userType, Timestamp expiresAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.userType = userType;
        this.expiresAt = expiresAt;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public UserType getUserType() {
        return userType;
    }
    
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getLastActivity() {
        return lastActivity;
    }
    
    public void setLastActivity(Timestamp lastActivity) {
        this.lastActivity = lastActivity;
    }
    
    public Timestamp getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    // Helper methods
    public boolean isExpired() {
        return expiresAt != null && expiresAt.before(new Timestamp(System.currentTimeMillis()));
    }
    
    public boolean isValid() {
        return isActive != null && isActive && !isExpired();
    }
    
    public void invalidate() {
        this.isActive = false;
    }
    
    public void updateLastActivity() {
        this.lastActivity = new Timestamp(System.currentTimeMillis());
    }
    
    public long getSessionDurationMinutes() {
        if (createdAt != null && lastActivity != null) {
            return (lastActivity.getTime() - createdAt.getTime()) / (60 * 1000);
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "UserSession{" +
                "id=" + id +
                ", sessionId='" + sessionId + '\'' +
                ", userId=" + userId +
                ", userType=" + userType +
                ", isActive=" + isActive +
                ", expiresAt=" + expiresAt +
                '}';
    }
}
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

@Document(collection = "audit_logs")
public class AuditLog {
    
    @Id
    @JsonProperty("_id")
    private String id;
    
    @Field("eventType")
    @NotBlank(message = "Event type is required")
    @JsonProperty("eventType")
    private String eventType;
    
    @Field("performedBy")
    @NotNull(message = "Performed by information is required")
    @JsonProperty("performedBy")
    private PerformedBy performedBy;
    
    @Field("targetEntity")
    @JsonProperty("targetEntity")
    private TargetEntity targetEntity;
    
    @Field("timestamp")
    @NotNull(message = "Timestamp is required")
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    
    @Field("action")
    @NotBlank(message = "Action is required")
    @JsonProperty("action")
    private String action; // CREATE, READ, UPDATE, DELETE, SOFT_DELETE, LOGIN, LOGOUT, etc.
    
    @Field("details")
    @JsonProperty("details")
    private Map<String, Object> details;
    
    @Field("beforeState")
    @JsonProperty("beforeState")
    private Map<String, Object> beforeState;
    
    @Field("afterState")
    @JsonProperty("afterState")
    private Map<String, Object> afterState;
    
    @Field("sessionInfo")
    @JsonProperty("sessionInfo")
    private SessionInfo sessionInfo;
    
    @Field("complianceInfo")
    @JsonProperty("complianceInfo")
    private ComplianceInfo complianceInfo;
    
    @Field("metadata")
    @JsonProperty("metadata")
    private AuditMetadata metadata;
    
    // Constructors
    public AuditLog() {
        this.timestamp = LocalDateTime.now();
    }
    
    public AuditLog(String eventType, PerformedBy performedBy, String action) {
        this();
        this.eventType = eventType;
        this.performedBy = performedBy;
        this.action = action;
    }
    
    // Inner classes for nested objects
    public static class PerformedBy {
        @JsonProperty("userId")
        private Integer userId;
        
        @JsonProperty("userType")
        private String userType; // Patient, Doctor, Admin, System
        
        @JsonProperty("username")
        private String username;
        
        @JsonProperty("ipAddress")
        private String ipAddress;
        
        // Constructors, getters and setters
        public PerformedBy() {}
        
        public PerformedBy(Integer userId, String userType, String username, String ipAddress) {
            this.userId = userId;
            this.userType = userType;
            this.username = username;
            this.ipAddress = ipAddress;
        }
        
        public Integer getUserId() { return userId; }
        public void setUserId(Integer userId) { this.userId = userId; }
        
        public String getUserType() { return userType; }
        public void setUserType(String userType) { this.userType = userType; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getIpAddress() { return ipAddress; }
        public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    }
    
    public static class TargetEntity {
        @JsonProperty("entityType")
        private String entityType; // Patient, Doctor, Admin, Appointment, etc.
        
        @JsonProperty("entityId")
        private Integer entityId;
        
        @JsonProperty("entityName")
        private String entityName;
        
        // Constructors, getters and setters
        public TargetEntity() {}
        
        public TargetEntity(String entityType, Integer entityId, String entityName) {
            this.entityType = entityType;
            this.entityId = entityId;
            this.entityName = entityName;
        }
        
        public String getEntityType() { return entityType; }
        public void setEntityType(String entityType) { this.entityType = entityType; }
        
        public Integer getEntityId() { return entityId; }
        public void setEntityId(Integer entityId) { this.entityId = entityId; }
        
        public String getEntityName() { return entityName; }
        public void setEntityName(String entityName) { this.entityName = entityName; }
    }
    
    public static class SessionInfo {
        @JsonProperty("sessionId")
        private String sessionId;
        
        @JsonProperty("userAgent")
        private String userAgent;
        
        @JsonProperty("duration")
        private String duration;
        
        // Constructors, getters and setters
        public SessionInfo() {}
        
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        
        public String getUserAgent() { return userAgent; }
        public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
        
        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }
    }
    
    public static class ComplianceInfo {
        @JsonProperty("hipaaCompliant")
        private Boolean hipaaCompliant = true;
        
        @JsonProperty("dataRetentionPolicy")
        private String dataRetentionPolicy;
        
        @JsonProperty("accessLogged")
        private Boolean accessLogged = true;
        
        // Constructors, getters and setters
        public ComplianceInfo() {}
        
        public Boolean getHipaaCompliant() { return hipaaCompliant; }
        public void setHipaaCompliant(Boolean hipaaCompliant) { this.hipaaCompliant = hipaaCompliant; }
        
        public String getDataRetentionPolicy() { return dataRetentionPolicy; }
        public void setDataRetentionPolicy(String dataRetentionPolicy) { this.dataRetentionPolicy = dataRetentionPolicy; }
        
        public Boolean getAccessLogged() { return accessLogged; }
        public void setAccessLogged(Boolean accessLogged) { this.accessLogged = accessLogged; }
    }
    
    public static class AuditMetadata {
        @JsonProperty("severity")
        private String severity; // LOW, MEDIUM, HIGH, CRITICAL
        
        @JsonProperty("category")
        private String category; // user_management, data_access, system_config, etc.
        
        @JsonProperty("requiresReview")
        private Boolean requiresReview = false;
        
        @JsonProperty("alertsTriggered")
        private List<String> alertsTriggered;
        
        // Constructors, getters and setters
        public AuditMetadata() {}
        
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public Boolean getRequiresReview() { return requiresReview; }
        public void setRequiresReview(Boolean requiresReview) { this.requiresReview = requiresReview; }
        
        public List<String> getAlertsTriggered() { return alertsTriggered; }
        public void setAlertsTriggered(List<String> alertsTriggered) { this.alertsTriggered = alertsTriggered; }
    }
    
    // Main getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    
    public PerformedBy getPerformedBy() { return performedBy; }
    public void setPerformedBy(PerformedBy performedBy) { this.performedBy = performedBy; }
    
    public TargetEntity getTargetEntity() { return targetEntity; }
    public void setTargetEntity(TargetEntity targetEntity) { this.targetEntity = targetEntity; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    
    public Map<String, Object> getDetails() { return details; }
    public void setDetails(Map<String, Object> details) { this.details = details; }
    
    public Map<String, Object> getBeforeState() { return beforeState; }
    public void setBeforeState(Map<String, Object> beforeState) { this.beforeState = beforeState; }
    
    public Map<String, Object> getAfterState() { return afterState; }
    public void setAfterState(Map<String, Object> afterState) { this.afterState = afterState; }
    
    public SessionInfo getSessionInfo() { return sessionInfo; }
    public void setSessionInfo(SessionInfo sessionInfo) { this.sessionInfo = sessionInfo; }
    
    public ComplianceInfo getComplianceInfo() { return complianceInfo; }
    public void setComplianceInfo(ComplianceInfo complianceInfo) { this.complianceInfo = complianceInfo; }
    
    public AuditMetadata getMetadata() { return metadata; }
    public void setMetadata(AuditMetadata metadata) { this.metadata = metadata; }
    
    // Helper methods
    public void setHighSeverity() {
        if (this.metadata == null) {
            this.metadata = new AuditMetadata();
        }
        this.metadata.setSeverity("HIGH");
        this.metadata.setRequiresReview(true);
    }
    
    public void setCriticalSeverity() {
        if (this.metadata == null) {
            this.metadata = new AuditMetadata();
        }
        this.metadata.setSeverity("CRITICAL");
        this.metadata.setRequiresReview(true);
    }
    
    public boolean isHighRiskEvent() {
        return this.metadata != null && 
               ("HIGH".equals(this.metadata.getSeverity()) || 
                "CRITICAL".equals(this.metadata.getSeverity()));
    }
}
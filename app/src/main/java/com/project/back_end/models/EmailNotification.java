package com.project.back_end.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "email_notifications")
public class EmailNotification {
    
    @Id
    @JsonProperty("_id")
    private String id;
    
    @Field("recipientId")
    @NotNull(message = "Recipient ID is required")
    @JsonProperty("recipientId")
    private Integer recipientId;
    
    @Field("recipientType")
    @NotBlank(message = "Recipient type is required")
    @JsonProperty("recipientType")
    private String recipientType; // Patient, Doctor, Admin
    
    @Field("recipientEmail")
    @NotBlank(message = "Recipient email is required")
    @Email(message = "Invalid email format")
    @JsonProperty("recipientEmail")
    private String recipientEmail;
    
    @Field("notificationType")
    @NotBlank(message = "Notification type is required")
    @JsonProperty("notificationType")
    private String notificationType;
    
    @Field("appointmentId")
    @JsonProperty("appointmentId")
    private Integer appointmentId;
    
    @Field("subject")
    @NotBlank(message = "Subject is required")
    @JsonProperty("subject")
    private String subject;
    
    @Field("content")
    @JsonProperty("content")
    private EmailContent content;
    
    @Field("emailStatus")
    @NotBlank(message = "Email status is required")
    @JsonProperty("emailStatus")
    private String emailStatus; // Pending, Sent, Delivered, Failed
    
    @Field("sentAt")
    @JsonProperty("sentAt")
    private LocalDateTime sentAt;
    
    @Field("deliveredAt")
    @JsonProperty("deliveredAt")
    private LocalDateTime deliveredAt;
    
    @Field("openedAt")
    @JsonProperty("openedAt")
    private LocalDateTime openedAt;
    
    @Field("scheduledFor")
    @JsonProperty("scheduledFor")
    private LocalDateTime scheduledFor;
    
    @Field("retryCount")
    @JsonProperty("retryCount")
    private Integer retryCount = 0;
    
    @Field("errorMessage")
    @JsonProperty("errorMessage")
    private String errorMessage;
    
    @Field("metadata")
    @JsonProperty("metadata")
    private EmailMetadata metadata;
    
    @Field("createdAt")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    
    // Constructors
    public EmailNotification() {
        this.createdAt = LocalDateTime.now();
        this.emailStatus = "Pending";
        this.retryCount = 0;
    }
    
    // Inner classes for nested objects
    public static class EmailContent {
        @JsonProperty("templateName")
        private String templateName;
        
        @JsonProperty("personalizedData")
        private Map<String, Object> personalizedData;
        
        // Constructors, getters and setters
        public EmailContent() {}
        
        public String getTemplateName() { return templateName; }
        public void setTemplateName(String templateName) { this.templateName = templateName; }
        
        public Map<String, Object> getPersonalizedData() { return personalizedData; }
        public void setPersonalizedData(Map<String, Object> personalizedData) { this.personalizedData = personalizedData; }
    }
    
    public static class EmailMetadata {
        @JsonProperty("campaignId")
        private String campaignId;
        
        @JsonProperty("priority")
        private String priority; // Low, Normal, High, Urgent
        
        @JsonProperty("tags")
        private List<String> tags;
        
        @JsonProperty("trackingEnabled")
        private Boolean trackingEnabled = true;
        
        // Constructors, getters and setters
        public EmailMetadata() {}
        
        public String getCampaignId() { return campaignId; }
        public void setCampaignId(String campaignId) { this.campaignId = campaignId; }
        
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
        
        public List<String> getTags() { return tags; }
        public void setTags(List<String> tags) { this.tags = tags; }
        
        public Boolean getTrackingEnabled() { return trackingEnabled; }
        public void setTrackingEnabled(Boolean trackingEnabled) { this.trackingEnabled = trackingEnabled; }
    }
    
    // Main getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Integer getRecipientId() { return recipientId; }
    public void setRecipientId(Integer recipientId) { this.recipientId = recipientId; }
    
    public String getRecipientType() { return recipientType; }
    public void setRecipientType(String recipientType) { this.recipientType = recipientType; }
    
    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }
    
    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }
    
    public Integer getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Integer appointmentId) { this.appointmentId = appointmentId; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public EmailContent getContent() { return content; }
    public void setContent(EmailContent content) { this.content = content; }
    
    public String getEmailStatus() { return emailStatus; }
    public void setEmailStatus(String emailStatus) { this.emailStatus = emailStatus; }
    
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
    
    public LocalDateTime getOpenedAt() { return openedAt; }
    public void setOpenedAt(LocalDateTime openedAt) { this.openedAt = openedAt; }
    
    public LocalDateTime getScheduledFor() { return scheduledFor; }
    public void setScheduledFor(LocalDateTime scheduledFor) { this.scheduledFor = scheduledFor; }
    
    public Integer getRetryCount() { return retryCount; }
    public void setRetryCount(Integer retryCount) { this.retryCount = retryCount; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public EmailMetadata getMetadata() { return metadata; }
    public void setMetadata(EmailMetadata metadata) { this.metadata = metadata; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    // Helper methods
    public void markAsSent() {
        this.emailStatus = "Sent";
        this.sentAt = LocalDateTime.now();
    }
    
    public void markAsDelivered() {
        this.emailStatus = "Delivered";
        this.deliveredAt = LocalDateTime.now();
    }
    
    public void markAsOpened() {
        this.openedAt = LocalDateTime.now();
    }
    
    public void markAsFailed(String errorMessage) {
        this.emailStatus = "Failed";
        this.errorMessage = errorMessage;
        this.retryCount++;
    }
    
    public boolean canRetry() {
        return this.retryCount < 3 && "Failed".equals(this.emailStatus);
    }
}
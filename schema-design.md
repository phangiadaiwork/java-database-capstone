## MySQL Database Design

### Table: patients
- id: INT, Primary Key, Auto Increment
- first_name: VARCHAR(50), Not Null
- last_name: VARCHAR(50), Not Null
- email: VARCHAR(100), Unique, Not Null
- password_hash: VARCHAR(255), Not Null
- phone: VARCHAR(20), Not Null
- date_of_birth: DATE, Not Null
- gender: ENUM('Male', 'Female', 'Other'), Not Null
- address: TEXT
- emergency_contact_name: VARCHAR(100)
- emergency_contact_phone: VARCHAR(20)
- medical_history: TEXT
- allergies: TEXT
- insurance_provider: VARCHAR(100)
- insurance_policy_number: VARCHAR(50)
- email_verified: BOOLEAN, Default FALSE
- email_verification_token: VARCHAR(255)
- password_reset_token: VARCHAR(255)
- password_reset_expires: TIMESTAMP
- last_login: TIMESTAMP
- failed_login_attempts: INT, Default 0
- account_locked_until: TIMESTAMP
- created_at: TIMESTAMP, Default CURRENT_TIMESTAMP
- updated_at: TIMESTAMP, Default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
- is_active: BOOLEAN, Default TRUE

### Table: doctors
- id: INT, Primary Key, Auto Increment
- username: VARCHAR(50), Unique, Not Null
- first_name: VARCHAR(50), Not Null
- last_name: VARCHAR(50), Not Null
- email: VARCHAR(100), Unique, Not Null
- password_hash: VARCHAR(255), Not Null
- phone: VARCHAR(20), Not Null
- phone_public: VARCHAR(20)
- specialization: VARCHAR(100), Not Null
- license_number: VARCHAR(50), Unique, Not Null
- years_of_experience: INT, Default 0
- education: TEXT
- clinic_location_id: INT, Foreign Key → clinic_locations(id)
- consultation_fee: DECIMAL(10,2), Default 0.00
- bio: TEXT
- profile_image_url: VARCHAR(255)
- is_available: BOOLEAN, Default TRUE
- is_public: BOOLEAN, Default TRUE 
- last_login: TIMESTAMP
- session_timeout_minutes: INT, Default 30
- created_at: TIMESTAMP, Default CURRENT_TIMESTAMP
- updated_at: TIMESTAMP, Default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
- created_by: INT, Foreign Key → admin(id)

### Table: appointments
- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id), Not Null
- patient_id: INT, Foreign Key → patients(id), Not Null
- clinic_location_id: INT, Foreign Key → clinic_locations(id), Not Null
- appointment_date: DATE, Not Null
- appointment_time: TIME, Not Null
- duration_minutes: INT, Default 60, Not Null 
- status: ENUM('Scheduled', 'Confirmed', 'Completed', 'Cancelled', 'No-Show'), Default 'Scheduled'
- appointment_type: ENUM('Consultation', 'Follow-up', 'Emergency', 'Routine Check-up'), Not Null
- chief_complaint: TEXT
- preparation_instructions: TEXT 
- notes: TEXT
- diagnosis: TEXT
- booked_by: ENUM('Patient', 'Admin', 'Doctor'), Default 'Patient'
- confirmation_sent: BOOLEAN, Default FALSE
- reminder_sent: BOOLEAN, Default FALSE
- created_at: TIMESTAMP, Default CURRENT_TIMESTAMP
- updated_at: TIMESTAMP, Default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
- cancelled_at: TIMESTAMP, Null
- cancellation_reason: TEXT

### Table: admin
- id: INT, Primary Key, Auto Increment
- username: VARCHAR(50), Unique, Not Null
- email: VARCHAR(100), Unique, Not Null
- password_hash: VARCHAR(255), Not Null
- first_name: VARCHAR(50), Not Null
- last_name: VARCHAR(50), Not Null
- role: ENUM('Super Admin', 'Clinic Admin', 'Receptionist'), Default 'Receptionist'
- permissions: JSON
- last_login: TIMESTAMP
- is_active: BOOLEAN, Default TRUE
- created_at: TIMESTAMP, Default CURRENT_TIMESTAMP
- updated_at: TIMESTAMP, Default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
- created_by: INT, Foreign Key → admin(id)

### Table: clinic_locations
- id: INT, Primary Key, Auto Increment
- name: VARCHAR(100), Not Null
- address: TEXT, Not Null
- city: VARCHAR(50), Not Null
- state: VARCHAR(50), Not Null
- zip_code: VARCHAR(10), Not Null
- country: VARCHAR(50), Default 'USA'
- phone: VARCHAR(20), Not Null
- email: VARCHAR(100)
- operating_hours: JSON
- facilities: JSON
- capacity: INT, Default 50
- is_active: BOOLEAN, Default TRUE
- created_at: TIMESTAMP, Default CURRENT_TIMESTAMP
- updated_at: TIMESTAMP, Default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

### Table: payments
- id: INT, Primary Key, Auto Increment
- appointment_id: INT, Foreign Key → appointments(id), Not Null
- patient_id: INT, Foreign Key → patients(id), Not Null
- amount: DECIMAL(10,2), Not Null
- payment_method: ENUM('Cash', 'Credit Card', 'Debit Card', 'Insurance', 'Online'), Not Null
- payment_status: ENUM('Pending', 'Completed', 'Failed', 'Refunded'), Default 'Pending'
- transaction_id: VARCHAR(100), Unique
- payment_date: TIMESTAMP, Default CURRENT_TIMESTAMP
- insurance_claim_number: VARCHAR(100)
- copay_amount: DECIMAL(10,2), Default 0.00
- insurance_coverage_amount: DECIMAL(10,2), Default 0.00
- notes: TEXT
- processed_by: INT, Foreign Key → admin(id)
- created_at: TIMESTAMP, Default CURRENT_TIMESTAMP
- updated_at: TIMESTAMP, Default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

### Table: user_sessions
- id: INT, Primary Key, Auto Increment
- session_id: VARCHAR(255), Unique, Not Null
- user_id: INT, Not Null
- user_type: ENUM('Patient', 'Doctor', 'Admin'), Not Null
- ip_address: VARCHAR(45)
- user_agent: TEXT
- created_at: TIMESTAMP, Default CURRENT_TIMESTAMP
- last_activity: TIMESTAMP, Default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
- expires_at: TIMESTAMP, Not Null
- is_active: BOOLEAN, Default TRUE

### Table: doctor_availability
- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id), Not Null
- clinic_location_id: INT, Foreign Key → clinic_locations(id), Not Null
- day_of_week: ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'), Not Null
- start_time: TIME, Not Null
- end_time: TIME, Not Null
- slot_duration_minutes: INT, Default 30
- is_available: BOOLEAN, Default TRUE
- effective_from: DATE, Not Null
- effective_until: DATE
- created_at: TIMESTAMP, Default CURRENT_TIMESTAMP

### Table: doctor_unavailability
- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id), Not Null
- unavailable_date: DATE, Not Null
- start_time: TIME, Not Null
- end_time: TIME, Not Null
- reason: ENUM('Vacation', 'Sick Leave', 'Conference', 'Personal', 'Emergency', 'Other'), Not Null
- reason_details: TEXT
- is_recurring: BOOLEAN, Default FALSE
- recurrence_pattern: ENUM('Daily', 'Weekly', 'Monthly', 'Yearly')
- recurrence_end_date: DATE
- created_by: ENUM('Doctor', 'Admin'), Default 'Doctor'
- emergency_override_allowed: BOOLEAN, Default FALSE
- created_at: TIMESTAMP, Default CURRENT_TIMESTAMP
- updated_at: TIMESTAMP, Default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

### Key Design Decisions and Constraints:

1. **Cascading Deletes**: Patient and doctor deletions are handled with soft deletes (is_active flag) to preserve historical appointment data.

2. **Appointment Conflicts**: Unique constraint on (doctor_id, appointment_date, appointment_time) prevents overlapping appointments.

3. **Data Integrity**: Foreign key constraints ensure referential integrity across all relationships.

4. **Flexibility**: JSON fields in clinic_locations and admin tables provide flexibility for varying configurations.

5. **Audit Trail**: All tables include created_at and updated_at timestamps for audit purposes.

6. **Scalability**: Auto-increment primary keys and proper indexing on foreign keys ensure good performance.

**Procedure: GetMonthlyAppointmentStats**
```sql
DELIMITER //
CREATE PROCEDURE GetMonthlyAppointmentStats(
    IN start_year INT,
    IN end_year INT
)
BEGIN
    SELECT 
        YEAR(appointment_date) as year,
        MONTH(appointment_date) as month,
        MONTHNAME(appointment_date) as month_name,
        COUNT(*) as total_appointments,
        COUNT(CASE WHEN status = 'Completed' THEN 1 END) as completed_appointments,
        COUNT(CASE WHEN status = 'Cancelled' THEN 1 END) as cancelled_appointments,
        COUNT(CASE WHEN status = 'No-Show' THEN 1 END) as no_show_appointments,
        ROUND(AVG(CASE WHEN status = 'Completed' THEN 1 ELSE 0 END) * 100, 2) as completion_rate
    FROM appointments 
    WHERE YEAR(appointment_date) BETWEEN start_year AND end_year
    GROUP BY YEAR(appointment_date), MONTH(appointment_date)
    ORDER BY year DESC, month DESC;
END //
DELIMITER ;
```

**Usage:** `CALL GetMonthlyAppointmentStats(2024, 2025);`

## MongoDB Collection Design

### Collection: prescriptions
```json
{
  "_id": "ObjectId('64abc123456789')",
  "appointmentId": 51,
  "patientId": 123,
  "doctorId": 45,
  "patientName": "John Smith",
  "doctorName": "Dr. Sarah Johnson",
  "prescriptionDate": "2025-10-07T14:30:00Z",
  "medications": [
    {
      "name": "Paracetamol",
      "dosage": "500mg",
      "frequency": "Every 6 hours",
      "duration": "5 days",
      "instructions": "Take with food",
      "quantity": 20,
      "refillsAllowed": 2,
      "refillsUsed": 0
    },
    {
      "name": "Vitamin D3",
      "dosage": "1000 IU",
      "frequency": "Once daily",
      "duration": "30 days",
      "instructions": "Take with breakfast",
      "quantity": 30,
      "refillsAllowed": 3,
      "refillsUsed": 1
    }
  ],
  "pharmacy": {
    "name": "Walgreens SF",
    "address": "123 Market Street, San Francisco, CA",
    "phone": "(555) 123-4567",
    "pharmacistContact": "Alice Brown"
  },
  "doctorNotes": "Patient responding well to treatment. Continue current medication regimen.",
  "allergies": ["Penicillin", "Shellfish"],
  "interactions": [],
  "status": "Active",
  "digitalSignature": "dr_johnson_sig_hash_xyz",
  "metadata": {
    "prescriptionNumber": "RX-2025-10-0001",
    "tags": ["routine", "maintenance"],
    "priority": "Normal",
    "followUpRequired": false,
    "followUpDate": null
  },
  "createdAt": "2025-10-07T14:30:00Z",
  "updatedAt": "2025-10-07T14:30:00Z"
}
```

### Collection: patient_notes
```json
{
  "_id": "ObjectId('64def456789abc')",
  "patientId": 123,
  "doctorId": 45,
  "appointmentId": 51,
  "noteDate": "2025-10-07T14:30:00Z",
  "noteType": "Consultation",
  "chiefComplaint": "Persistent headaches for the past week",
  "historyOfPresentIllness": "Patient reports gradual onset of headaches starting last Monday...",
  "physicalExamination": {
    "vitalSigns": {
      "bloodPressure": "120/80 mmHg",
      "heartRate": 72,
      "temperature": "98.6°F",
      "respiratoryRate": 16,
      "oxygenSaturation": "99%",
      "weight": "70 kg",
      "height": "175 cm",
      "bmi": 22.9
    },
    "generalAppearance": "Well-appearing, no acute distress",
    "systems": {
      "cardiovascular": "Regular rate and rhythm, no murmurs",
      "respiratory": "Clear to auscultation bilaterally",
      "neurological": "Alert and oriented x3, no focal deficits",
      "gastrointestinal": "Soft, non-tender, no masses"
    }
  },
  "assessment": "Tension headaches likely due to work stress",
  "plan": [
    "Prescribe Paracetamol 500mg as needed for headache relief",
    "Recommend stress management techniques and regular sleep schedule",
    "Follow-up appointment in 2 weeks if symptoms persist",
    "Patient education on stress reduction methods provided"
  ],
  "doctorNotes": "Patient appears stressed due to work deadlines. Advised lifestyle modifications. Monitor for improvement over next 2 weeks.",
  "patientConcerns": [
    "Worried about brain tumor",
    "Affecting work performance",
    "Sleep disturbances"
  ],
  "followUpInstructions": "Return if headaches worsen or new symptoms develop",
  "accessLog": [
    {
      "accessedBy": 45,
      "accessedAt": "2025-10-07T14:30:00Z",
      "accessType": "Created",
      "ipAddress": "192.168.1.10"
    }
  ],
  "metadata": {
    "appointmentDuration": 60,
    "confidentialityLevel": "Standard",
    "shareWithPatient": true,
    "billingCodes": ["99213"],
    "tags": ["headache", "stress-related", "routine-consultation"]
  },
  "createdAt": "2025-10-07T14:30:00Z",
  "updatedAt": "2025-10-07T14:30:00Z"
}
```

### Collection: email_notifications
```json
{
  "_id": "ObjectId('64abc123456789')",
  "recipientId": 123,
  "recipientType": "Patient",
  "recipientEmail": "john.smith@email.com",
  "notificationType": "Appointment Confirmation",
  "appointmentId": 51,
  "subject": "Appointment Confirmation - Dr. Sarah Johnson",
  "content": {
    "templateName": "appointment_confirmation",
    "personalizedData": {
      "patientName": "John Smith",
      "doctorName": "Dr. Sarah Johnson",
      "appointmentDate": "2025-10-15",
      "appointmentTime": "14:00",
      "clinicLocation": "Main Clinic - 123 Health St",
      "preparationInstructions": "Please arrive 15 minutes early and bring your insurance card"
    }
  },
  "emailStatus": "Sent",
  "sentAt": "2025-10-07T15:30:00Z",
  "deliveredAt": "2025-10-07T15:30:45Z",
  "openedAt": "2025-10-07T16:15:22Z",
  "scheduledFor": null,
  "retryCount": 0,
  "errorMessage": null,
  "metadata": {
    "campaignId": "appointment_confirmations_oct_2025",
    "priority": "High",
    "tags": ["appointment", "confirmation", "automated"],
    "trackingEnabled": true
  },
  "createdAt": "2025-10-07T15:30:00Z"
}
```

### Collection: audit_logs
```json
{
  "_id": "ObjectId('64jkl012345678')",
  "eventType": "Doctor Profile Deleted",
  "performedBy": {
    "userId": 1,
    "userType": "Admin",
    "username": "admin_user",
    "ipAddress": "192.168.1.200"
  },
  "targetEntity": {
    "entityType": "Doctor",
    "entityId": 45,
    "entityName": "Dr. Sarah Johnson"
  },
  "timestamp": "2025-10-07T13:45:00Z",
  "action": "SOFT_DELETE",
  "details": {
    "reason": "Doctor resigned from clinic",
    "appointmentsAffected": 12,
    "appointmentsRescheduled": 10,
    "appointmentsCancelled": 2,
    "patientsNotified": 12
  },
  "beforeState": {
    "isAvailable": true,
    "isActive": true,
    "lastLogin": "2025-10-05T09:00:00Z"
  },
  "afterState": {
    "isAvailable": false,
    "isActive": false,
    "deactivatedAt": "2025-10-07T13:45:00Z"
  },
  "sessionInfo": {
    "sessionId": "sess_admin_abc123",
    "userAgent": "Mozilla/5.0...",
    "duration": "45 minutes"
  },
  "complianceInfo": {
    "hipaaCompliant": true,
    "dataRetentionPolicy": "7 years",
    "accessLogged": true
  },
  "metadata": {
    "severity": "HIGH",
    "category": "user_management",
    "requiresReview": true,
    "alertsTriggered": ["doctor_deactivation_alert"]
  }
}
```
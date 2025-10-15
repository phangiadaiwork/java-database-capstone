    # User Stories

## Admin User Stories

### User Story 1: Admin Login

**Title:**
_As an admin, I want to log into the portal with my username and password, so that I can manage the platform securely._

**Acceptance Criteria:**
1. Admin can enter username and password on login form
2. System validates credentials against admin database
3. Successful login redirects to admin dashboard
4. Failed login displays appropriate error message
5. Session is established for authenticated admin

**Priority:** High

**Story Points:** 3

**Notes:**
- Implement secure password hashing
- Consider session timeout mechanisms
- Add login attempt rate limiting

### User Story 2: Admin Logout

**Title:**
_As an admin, I want to log out of the portal, so that I can protect system access when I'm done._

**Acceptance Criteria:**
1. Admin can click logout button from any admin page
2. System terminates admin session immediately
3. Admin is redirected to login page
4. All admin privileges are revoked upon logout
5. Browser back button cannot access admin pages after logout

**Priority:** High

**Story Points:** 2

**Notes:**
- Clear all session data on logout
- Ensure proper session invalidation
- Consider automatic logout after inactivity

### User Story 3: Add Doctors

**Title:**
_As an admin, I want to add doctors to the portal, so that they can provide medical services to patients._

**Acceptance Criteria:**
1. Admin can access "Add Doctor" form from admin dashboard
2. Form includes fields for doctor's personal and professional information
3. System validates all required fields before submission
4. Successfully added doctor appears in doctors list
5. New doctor receives login credentials to access their dashboard

**Priority:** High

**Story Points:** 5

**Notes:**
- Include fields: name, specialization, contact info, license number
- Generate secure default password for new doctors
- Send welcome email with login instructions

### User Story 4: Delete Doctor Profile

**Title:**
_As an admin, I want to delete a doctor's profile from the portal, so that I can remove inactive or terminated doctors._

**Acceptance Criteria:**
1. Admin can view list of all doctors in the system
2. Each doctor entry has a delete option
3. System prompts for confirmation before deletion
4. Deleted doctor profile is removed from all listings
5. Associated appointments are handled appropriately (cancelled/reassigned)

**Priority:** Medium

**Story Points:** 4

**Notes:**
- Consider soft delete vs hard delete
- Handle existing appointments and patient relationships
- Maintain audit trail of deleted profiles

### User Story 5: Generate Appointment Statistics

**Title:**
_As an admin, I want to run a stored procedure in MySQL CLI to get the number of appointments per month, so that I can track usage statistics._

**Acceptance Criteria:**
1. Stored procedure exists to calculate monthly appointment counts
2. Procedure can be executed from MySQL CLI
3. Results show appointments grouped by month
4. Data includes current year and previous months
5. Output is formatted for easy reading and analysis

**Priority:** Low

**Story Points:** 3

**Notes:**
- Consider creating a web interface for this report
- Include parameters for date range selection
- Add additional metrics like doctor utilization
- Ensure procedure handles timezone considerations

## Patient User Stories

### User Story 6: View Doctors List (Anonymous)

**Title:**
_As a patient, I want to view a list of doctors without logging in, so that I can explore options before registering._

**Acceptance Criteria:**
1. Public page displays list of available doctors
2. Each doctor entry shows name, specialization, and basic information
3. No personal contact information is exposed publicly
4. Page loads without authentication requirement
5. Call-to-action prompts visitors to register for booking

**Priority:** Medium

**Story Points:** 3

**Notes:**
- Display only public information
- Include doctor availability status
- Add filters by specialization

### User Story 7: Patient Registration

**Title:**
_As a patient, I want to sign up using my email and password, so that I can book appointments._

**Acceptance Criteria:**
1. Registration form accepts email, password, and basic personal information
2. System validates email format and password strength
3. Email verification is sent to confirm account
4. Duplicate email addresses are rejected with appropriate message
5. Successful registration creates patient profile and enables login

**Priority:** High

**Story Points:** 4

**Notes:**
- Implement email verification workflow
- Include terms of service acceptance
- Validate unique email addresses

### User Story 8: Patient Login

**Title:**
_As a patient, I want to log into the portal, so that I can manage my bookings._

**Acceptance Criteria:**
1. Patient can enter email and password on login form
2. System validates credentials against patient database
3. Successful login redirects to patient dashboard
4. Failed login displays appropriate error message
5. "Forgot password" option is available

**Priority:** High

**Story Points:** 3

**Notes:**
- Implement password reset functionality
- Consider "Remember me" option
- Add account lockout after failed attempts

### User Story 9: Patient Logout

**Title:**
_As a patient, I want to log out of the portal, so that I can secure my account._

**Acceptance Criteria:**
1. Patient can click logout button from any patient page
2. System terminates patient session immediately
3. Patient is redirected to public homepage
4. All patient data access is revoked upon logout
5. Browser back button cannot access patient pages after logout

**Priority:** High

**Story Points:** 2

**Notes:**
- Clear all session data on logout
- Ensure proper session invalidation
- Automatic logout after extended inactivity

### User Story 10: Book Appointment

**Title:**
_As a patient, I want to log in and book an hour-long appointment, so that I can consult with a doctor._

**Acceptance Criteria:**
1. Patient can select doctor from available list
2. Calendar shows available time slots for selected doctor
3. Appointment duration is fixed at one hour
4. Patient can select preferred date and time
5. Booking confirmation is displayed and sent via email

**Priority:** High

**Story Points:** 5

**Notes:**
- Prevent double-booking of time slots
- Include appointment cancellation policy
- Send calendar invitation to patient

### User Story 11: View Upcoming Appointments

**Title:**
_As a patient, I want to view my upcoming appointments, so that I can prepare accordingly._

**Acceptance Criteria:**
1. Patient dashboard displays list of upcoming appointments
2. Each appointment shows doctor name, date, time, and location
3. Appointments are sorted by date and time
4. Past appointments are separated or filtered out
5. Patient can access appointment details and preparation instructions

**Priority:** Medium

**Story Points:** 3

**Notes:**
- Include reminder notifications
- Add option to reschedule appointments
- Display appointment status (confirmed, pending, etc.)

## Doctor User Stories

### User Story 12: Doctor Login

**Title:**
_As a doctor, I want to log into the portal, so that I can manage my appointments._

**Acceptance Criteria:**
1. Doctor can enter username and password on login form
2. System validates credentials against doctor database
3. Successful login redirects to doctor dashboard
4. Failed login displays appropriate error message
5. Session is established for authenticated doctor

**Priority:** High

**Story Points:** 3

**Notes:**
- Use secure authentication method
- Consider two-factor authentication
- Implement session timeout for security

### User Story 13: Doctor Logout

**Title:**
_As a doctor, I want to log out of the portal, so that I can protect my data.

**Acceptance Criteria:**
1. Doctor can click logout button from any doctor page
2. System terminates doctor session immediately
3. Doctor is redirected to login page
4. All patient data access is revoked upon logout
5. Browser back button cannot access doctor pages after logout

**Priority:** High

**Story Points:** 2

**Notes:**
- Ensure HIPAA compliance for data protection
- Clear all cached patient information
- Automatic logout in shared computer environments

### User Story 14: View Appointment Calendar

**Title:**
_As a doctor, I want to view my appointment calendar, so that I can stay organized._

**Acceptance Criteria:**
1. Calendar displays all scheduled appointments
2. Daily, weekly, and monthly views are available
3. Each appointment shows patient name and appointment time
4. Available time slots are clearly distinguished from booked slots
5. Calendar synchronizes with appointment booking system

**Priority:** High

**Story Points:** 4

**Notes:**
- Include color coding for different appointment types
- Allow easy navigation between date ranges
- Integrate with external calendar systems

### User Story 15: Mark Unavailability

**Title:**
_As a doctor, I want to mark my unavailability, so that patients can only see available slots._

**Acceptance Criteria:**
1. Doctor can block out time periods as unavailable
2. Unavailable slots are hidden from patient booking interface
3. Doctor can set recurring unavailability patterns
4. Emergency override option exists for urgent appointments
5. Unavailability changes are reflected immediately in booking system

**Priority:** Medium

**Story Points:** 4

**Notes:**
- Include vacation and holiday scheduling
- Allow partial day unavailability
- Notify patients of cancelled appointments due to unavailability

### User Story 16: Update Doctor Profile

**Title:**
_As a doctor, I want to update my profile with specialization and contact information, so that patients have up-to-date information._

**Acceptance Criteria:**
1. Doctor can edit profile information including specialization
2. Contact information fields are available for updates
3. Changes are validated before saving
4. Updated information appears in patient-facing doctor listings
5. Profile photo upload capability is available

**Priority:** Medium

**Story Points:** 3

**Notes:**
- Include professional credentials and certifications
- Allow biography and education information
- Implement approval workflow for sensitive changes

### User Story 17: View Patient Details

**Title:**
_As a doctor, I want to view patient details for upcoming appointments, so that I can be prepared._

**Acceptance Criteria:**
1. Doctor can access patient information for scheduled appointments
2. Patient details include medical history and previous appointments
3. Information is displayed securely and compliantly
4. Doctor can add notes to patient records
5. Access is logged for audit and compliance purposes

**Priority:** High

**Story Points:** 4

**Notes:**
- Ensure HIPAA compliance for patient data access
- Include appointment history with previous doctors
- Add ability to view and update patient medical records


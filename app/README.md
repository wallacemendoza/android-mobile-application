# Vacation Scheduler

## Purpose

Vacation Scheduler is a multi-screen Android application developed to help users organize and manage scheduled vacations and associated excursions. The application allows users to create, edit, and delete vacations, manage related excursions, and receive alerts for important dates.

This project was completed as part of D308 – Mobile Application Development at Western Governors University.

---

## Android Version Information

- Minimum Supported Version: Android 8.0 (API 26)
- Target SDK Version: 36
- Tested On: Android Emulator – Medium Phone API 36.1
- Signed Release APK Generated: Yes

---

## Git Repository

Repository URL:  
https://gitlab.com/wgu-gitlab-environment/student-repos/wmendo8/d308-mobile-application-development-android

Branch Used:  
working_branch

---

## How to Operate the Application

### Home Screen
- Launch the application.
- Select "View Vacations" to navigate to the vacation list screen.

### Vacation List Screen
- Displays all vacations stored in the Room database.
- Select the "+" button to add a new vacation.
- Select an existing vacation to view or edit its details.

### Vacation Details Screen
Users can:
- Enter or edit vacation title.
- Enter or edit vacation price.
- Enter hotel or accommodation name.
- Enter start and end dates.
- Save changes to update the database.
- Delete the vacation.
- Set alerts for vacation start and end dates.
- Share vacation details using the built-in Android sharing feature.
- View a list of associated excursions.
- Add new excursions.

Validation includes:
- End date and time must be after start  and time.

### Excursion Details Screen
Users can:
- Enter or edit excursion title.
- Enter or edit excursion price.
- Enter excursion date and time.
- Save changes to update the database.
- Delete excursion.
- Set an alert for the excursion date.

Validation includes:
- Excursion date must fall within the associated vacation dates.
- Date format validation is enforced.

---

## Database and Architecture

The application was developed using:

- Java
- Room Framework (SQLite abstraction layer)
- DAO pattern for database operations
- Object-oriented programming principles
- Multiple Activities for navigation

### Entities

**Vacation**
- vacationID (Primary Key)
- vacationName
- hotel
- price
- startVacationDate
- endVacationDate

**Excursion**
- excursionID (Primary Key)
- excursionName
- price
- excursionDate
- vacationID (Foreign Key reference)

### Relationship
One Vacation can have multiple Excursions.

---

## Functional Requirements Implementation (Rubric Alignment)

### 4086.1.1 Creates Page Layouts
- Home screen layout
- Vacation list layout
- Vacation detail layout
- Excursion detail layout
- RecyclerView used for list rendering

### 4086.1.2 Designs Mobile Applications
- Structured navigation using Intents
- Layered architecture separating UI and data layers

### 4086.1.3 Develops Mobile Applications
- Room database for persistent storage
- Full CRUD functionality for vacations and excursions
- AlarmManager and BroadcastReceiver for alerts
- Share Intent for vacation sharing
- Input validation for date formatting and logical constraints

### 4086.1.4 Documents Solutions with Storyboards
- Application screenshots provided
- Signed release APK generation documented

### 4086.1.5 Articulates Development Process Challenges
Challenges included:
- Implementing date validation logic
- Configuring Room entity relationships
- Managing broadcast receivers for notifications

### 4086.1.6 Describes Alternative Methods
Alternative approaches considered:
- Using WorkManager instead of AlarmManager
- Using Fragments instead of multiple Activities
- Implementing cloud storage instead of local Room database

---

## Deployment

A signed release APK was generated using Android Studio’s “Generate Signed Bundle / APK” feature.

Release APK File:
`app-release.apk`

The application was tested to ensure all required features operate as expected.

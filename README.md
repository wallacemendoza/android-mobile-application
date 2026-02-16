# `VACATION_SCHEDULER`

```ascii
██╗   ██╗ █████╗  ██████╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
██║   ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
██║   ██║███████║██║     ███████║   ██║   ██║██║   ██║██╔██╗ ██║
╚██╗ ██╔╝██╔══██║██║     ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
 ╚████╔╝ ██║  ██║╚██████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚═══╝  ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
███████╗ ██████╗██╗  ██╗███████╗██████╗ ██╗   ██╗██╗     ███████╗██████╗ 
██╔════╝██╔════╝██║  ██║██╔════╝██╔══██╗██║   ██║██║     ██╔════╝██╔══██╗
███████╗██║     ███████║█████╗  ██║  ██║██║   ██║██║     █████╗  ██████╔╝
╚════██║██║     ██╔══██║██╔══╝  ██║  ██║██║   ██║██║     ██╔══╝  ██╔══██╗
███████║╚██████╗██║  ██║███████╗██████╔╝╚██████╔╝███████╗███████╗██║  ██║
╚══════╝ ╚═════╝╚═╝  ╚═╝╚══════╝╚═════╝  ╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═╝
```

<div align="center">

### 🌴 NATIVE ANDROID VACATION PLANNING & MANAGEMENT APP 🌴

**`JAVA`** × **`ANDROID`** × **`ROOM_DATABASE`** × **`MATERIAL_DESIGN`**

*Full-featured mobile application demonstrating Android development best practices, local database persistence, and modern UI/UX patterns*

-----

![Java](https://img.shields.io/badge/JAVA-Native_Android-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/ANDROID-API_26+-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Room](https://img.shields.io/badge/ROOM-Database-4285F4?style=for-the-badge&logo=google&logoColor=white)

</div>

-----

## 🎯 `APPLICATION_OVERVIEW`

**Production-ready Android vacation planner** enabling users to organize trips, manage excursions, set date-based alerts, and share itineraries. Built with native Java, Room persistence library, and Material Design components — showcasing mobile-first development patterns.

### `CORE_CAPABILITIES`

```yaml
platform: "Native Android (Java)"
min_sdk: "API 26 (Android 8.0 Oreo)"
target_sdk: "API 36"
database: "Room (SQLite abstraction)"
architecture: "DAO Pattern + Repository"
features:
  - Full CRUD operations
  - Local SQLite persistence
  - Date-based notifications
  - Social sharing integration
  - Input validation & error handling
  - Multi-screen navigation
```

-----

## 🔥 `FEATURES_IMPLEMENTED`

<table>
<tr>
<td width="50%">

### `VACATION_MANAGEMENT`

```java
✓ Create/Edit/Delete vacations
✓ Track hotel accommodations
✓ Set vacation dates & prices
✓ Associate multiple excursions
✓ Set start/end date alerts
✓ Share vacation details
✓ Data validation (date logic)
```

</td>
<td width="50%">

### `EXCURSION_TRACKING`

```java
✓ Create/Edit/Delete excursions
✓ Link to parent vacation
✓ Set excursion dates & prices
✓ Date-based notifications
✓ Validation (dates within vacation)
✓ RecyclerView list display
✓ One-to-Many relationship
```

</td>
</tr>
</table>

### `TECHNICAL_IMPLEMENTATIONS`

|Feature           |Technology                     |Purpose                  |
|------------------|-------------------------------|-------------------------|
|**Local Database**|Room Persistence Library       |SQLite abstraction & ORM |
|**Data Access**   |DAO (Data Access Objects)      |Clean database operations|
|**UI Components** |RecyclerView, CardView         |Modern list displays     |
|**Notifications** |AlarmManager, BroadcastReceiver|Date-based alerts        |
|**Sharing**       |Android Share Intent           |Social integration       |
|**Navigation**    |Activity-based                 |Multi-screen flow        |
|**Validation**    |Custom logic                   |Date constraints         |

-----

## 🛠️ `ARCHITECTURE`

```
╔═══════════════════════════════════════════════════════════════╗
║                      UI LAYER (Activities)                     ║
║   ┌──────────────────────────────────────────────────────┐   ║
║   │  HomeActivity                                         │   ║
║   │  VacationListActivity                                 │   ║
║   │  VacationDetailActivity                               │   ║
║   │  ExcursionDetailActivity                              │   ║
║   └──────────────────────────────────────────────────────┘   ║
╚══════════════════════╦════════════════════════════════════════╝
                       ║
╔══════════════════════╩════════════════════════════════════════╗
║                   DAO LAYER (Data Access)                      ║
║   ┌──────────────────────────────────────────────────────┐   ║
║   │  VacationDAO                                          │   ║
║   │  ├─ @Insert, @Update, @Delete                        │   ║
║   │  └─ @Query (custom queries)                          │   ║
║   │                                                       │   ║
║   │  ExcursionDAO                                         │   ║
║   │  ├─ @Insert, @Update, @Delete                        │   ║
║   │  └─ @Query (with foreign key filters)                │   ║
║   └──────────────────────────────────────────────────────┘   ║
╚══════════════════════╦════════════════════════════════════════╝
                       ║
╔══════════════════════╩════════════════════════════════════════╗
║                 ROOM DATABASE LAYER                            ║
║   ┌──────────────────────────────────────────────────────┐   ║
║   │  VacationSchedulerDB.java                            │   ║
║   │  @Database(entities = {Vacation, Excursion})         │   ║
║   │  Version: 1                                           │   ║
║   └──────────────────────────────────────────────────────┘   ║
╚══════════════════════╦════════════════════════════════════════╝
                       ║
╔══════════════════════╩════════════════════════════════════════╗
║                   SQLITE DATABASE                              ║
║   Tables: vacations, excursions                               ║
║   Relationship: 1-to-Many (Vacation → Excursions)             ║
╚═══════════════════════════════════════════════════════════════╝
```

-----

## 💾 `DATABASE_SCHEMA`

### Entity Relationships

```
Vacation (1) ──────── (Many) Excursion
    │                      │
    │                      │
    └─ vacationID ─────────┘ (Foreign Key)
```

### Tables

**VACATION**

```sql
vacationID (PK)       INTEGER AUTO_INCREMENT
vacationName          TEXT NOT NULL
hotel                 TEXT
price                 REAL
startVacationDate     TEXT (ISO 8601 format)
endVacationDate       TEXT (ISO 8601 format)
```

**EXCURSION**

```sql
excursionID (PK)      INTEGER AUTO_INCREMENT
excursionName         TEXT NOT NULL
price                 REAL
excursionDate         TEXT (ISO 8601 format)
vacationID (FK)       INTEGER REFERENCES Vacation(vacationID)
```

### Room Entities

```java
@Entity(tableName = "vacations")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    
    private String vacationName;
    private String hotel;
    private double price;
    private String startVacationDate;
    private String endVacationDate;
    
    // Getters, Setters, Constructors
}

@Entity(tableName = "excursions",
        foreignKeys = @ForeignKey(
            entity = Vacation.class,
            parentColumns = "vacationID",
            childColumns = "vacationID",
            onDelete = ForeignKey.CASCADE
        ))
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    
    private String excursionName;
    private double price;
    private String excursionDate;
    private int vacationID;
    
    // Getters, Setters, Constructors
}
```

-----

## ⚙️ `INSTALLATION_&_SETUP`

### Prerequisites

```bash
# Android Studio
Version: Arctic Fox or later

# JDK
Version: 11 or later

# Android SDK
Min API: 26 (Android 8.0)
Target API: 36
```

### Build & Run

```bash
# Clone repository
git clone <repo-url>
cd android-mobile-application
git checkout working_branch

# Open in Android Studio
# File → Open → Select project folder

# Sync Gradle
# Build → Make Project

# Run on Emulator or Device
# Run → Run 'app'
```

### APK Generation

```bash
# Generate Signed Release APK
1. Build → Generate Signed Bundle / APK
2. Select APK
3. Create or use existing keystore
4. Select release build variant
5. APK generated in: app/release/app-release.apk
```

-----

## 🎮 `USER_WORKFLOWS`

### Complete Vacation Planning Flow

```
1. Launch App
   └─→ Home Screen

2. Tap "View Vacations"
   └─→ Vacation List Screen

3. Tap "+" to Add Vacation
   └─→ Vacation Detail Screen
   
4. Enter Vacation Details:
   ├─ Vacation Title
   ├─ Hotel Name
   ├─ Price
   ├─ Start Date
   └─ End Date

5. Save Vacation
   └─→ Saved to Room Database

6. Add Excursions:
   ├─ Tap "Add Excursion"
   ├─ Enter excursion details
   └─ Save (validates date is within vacation)

7. Set Notifications:
   ├─ Enable start date alert
   ├─ Enable end date alert
   └─ AlarmManager schedules notifications

8. Share Vacation:
   └─→ Tap Share → Select app → Send details
```

-----

## 🎯 `VALIDATION_RULES`

### Date Validation

```java
// Vacation dates
if (endDate.before(startDate)) {
    showError("End date must be after start date");
    return false;
}

// Excursion dates
if (excursionDate.before(vacationStartDate) || 
    excursionDate.after(vacationEndDate)) {
    showError("Excursion must be within vacation dates");
    return false;
}
```

### Input Validation

```java
// Required fields
if (vacationName.isEmpty()) {
    showError("Vacation name required");
}

// Date format (MM/dd/yyyy)
SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
sdf.setLenient(false);
try {
    Date date = sdf.parse(dateString);
} catch (ParseException e) {
    showError("Invalid date format");
}
```

-----

## 📱 `SCREENS_&_FEATURES`

### 1. Home Screen

- Welcome message
- “View Vacations” button
- Material Design card layout

### 2. Vacation List Screen

- RecyclerView of all vacations
- Card-based item display
- Floating Action Button (+) to add vacation
- Click item to view/edit details

### 3. Vacation Detail Screen

**Input Fields:**

- Vacation title
- Hotel name
- Price
- Start date (with DatePicker)
- End date (with DatePicker)

**Actions:**

- Save vacation
- Delete vacation
- Set start date alert
- Set end date alert
- Share vacation details
- View associated excursions
- Add new excursion

### 4. Excursion Detail Screen

**Input Fields:**

- Excursion title
- Price
- Date (with DatePicker)

**Actions:**

- Save excursion
- Delete excursion
- Set date alert

-----

## 🔔 `NOTIFICATIONS_SYSTEM`

### AlarmManager Implementation

```java
// Set vacation start alert
AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
Intent intent = new Intent(this, VacationAlertReceiver.class);
intent.putExtra("title", vacationName);
intent.putExtra("message", "Your vacation starts today!");

PendingIntent pendingIntent = PendingIntent.getBroadcast(
    this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
);

alarmManager.setExact(
    AlarmManager.RTC_WAKEUP,
    startDate.getTime(),
    pendingIntent
);
```

### BroadcastReceiver

```java
public class VacationAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        
        // Create notification
        NotificationCompat.Builder builder = 
            new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        
        NotificationManager manager = 
            (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(notificationId, builder.build());
    }
}
```

-----

## 📊 `PROJECT_METRICS`

```yaml
Development:
  Commits: 23
  Activities: 4
  Layouts: 4+
  Entities: 2
  DAOs: 2
  Screenshots: Complete set included

Database:
  Tables: 2
  Relationship: 1-to-Many with CASCADE
  Persistence: Room + SQLite
  
Features:
  CRUD Operations: Complete
  Notifications: AlarmManager + BroadcastReceiver
  Sharing: Android Share Intent
  Validation: Date logic + Input checks
  
Build:
  Min SDK: API 26
  Target SDK: API 36
  Signed APK: Generated
  Testing: Emulator (Medium Phone API 36.1)
```

-----

## 🧪 `TESTING`

### Tested Scenarios

```
✓ Create vacation with valid dates
✓ Edit existing vacation
✓ Delete vacation (cascades to excursions)
✓ Add excursion within vacation dates
✓ Reject excursion outside vacation dates
✓ Set vacation start alert
✓ Set vacation end alert
✓ Set excursion alert
✓ Share vacation details via messaging
✓ Navigate between screens
✓ RecyclerView displays all items
✓ Database persists across app restarts
```

-----

## 🔬 `LEARNING_OBJECTIVES`

Demonstrates proficiency in:

- **Android Development**: Native Java application structure
- **Room Database**: SQLite ORM with annotations
- **DAO Pattern**: Clean database abstraction
- **Entity Relationships**: Foreign keys, CASCADE operations
- **UI Design**: RecyclerView, CardView, Material Design
- **Activity Navigation**: Multi-screen flow with Intents
- **Notifications**: AlarmManager, BroadcastReceiver
- **Android Intents**: Share functionality, data passing
- **Input Validation**: Date logic, format checking
- **Data Persistence**: Local storage patterns

**Real-World Application**: Mobile-first travel planning patterns used in production apps.

-----

## 📸 `SCREENSHOTS`

Application screenshots available in `/Screenshots` directory:

- Home screen
- Vacation list
- Vacation detail form
- Excursion detail form
- Notifications
- Share functionality

-----

## 📜 `LICENSE_&_USAGE`

```
┌─────────────────────────────────────────────────────────┐
│  ANDROID MOBILE APPLICATION DEMONSTRATION                │
│                                                          │
│  Native Android vacation planning app showcasing         │
│  mobile development best practices and Room database.    │
│  Portfolio demonstration of mobile engineering.          │
│                                                          │
│  ⚠️  Academic/portfolio project only                    │
│  ⚠️  Not for commercial deployment                     │
│  ✓  Available for technical review                      │
│  ✓  Open to discussion                                  │
└─────────────────────────────────────────────────────────┘
```

-----

## 🚀 `AUTHOR`

**Wallace Mendoza** — *Mobile Developer*

Specializing in native Android development, Room persistence, and modern mobile UI/UX.

[GitHub](https://github.com/wallacemendoza) • [Portfolio](https://wallacemendoza.github.io/portfolio/)

-----

<div align="center">

### `TECH_FINGERPRINT`

`ANDROID` • `JAVA` • `ROOM` • `SQLite` • `MATERIAL_DESIGN` • `DAO` • `NOTIFICATIONS`

-----

*Building mobile experiences that users love*

**[⬆ back to top](#vacation_scheduler)**

</div>

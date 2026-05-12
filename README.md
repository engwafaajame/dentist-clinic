# Dental Care App

A modern Android application built with Kotlin and Jetpack Compose for managing dental clinic appointments, finding doctors, and maintaining patient profiles using Firebase.

## Features

### 📋 Core Features
- **Home Dashboard**: Quick overview of popular doctors
- **Appointment Management**: Book, view, filter, and cancel appointments
- **Doctor Directory**: Browse all doctors, filter by specialty
- **Patient Profile**: Manage personal information
- **Push Notifications**: Real-time appointment reminders via Firebase Cloud Messaging
- **Offline Support**: Local caching with Room Database

## Architecture

The app follows **Clean Architecture** principles with MVVM pattern:

```
app/
├── ui/                      # UI Layer (Compose)
│   ├── screens/            # Screen composables
│   └── theme/              # Material Design 3 theme
├── presentation/           # Presentation Layer (ViewModels)
│   └── viewmodel/          # MVVM ViewModels
├── domain/                 # Domain Layer
│   └── repository/         # Repository interfaces
├── data/                   # Data Layer
│   ├── local/              # Room Database
│   ├── remote/             # Firebase Service
│   └── models/             # Data models
├── di/                     # Dependency Injection (Hilt)
└── services/               # Background services
```

## Technologies Used

### UI Framework
- **Jetpack Compose**: Modern declarative UI framework
- **Material Design 3**: Latest material design system

### Database & Storage
- **Firebase Firestore**: Cloud database
- **Room Database**: Local data persistence

### Firebase Services
- **Firebase Cloud Messaging**: Push notifications
- **Firebase Realtime Database**: Alternative data storage
- **Firebase Analytics**: Usage analytics
- **Firebase Crashlytics**: Crash reporting

### Architecture Components
- **Hilt**: Dependency injection
- **ViewModel**: UI-related data holder
- **Flow**: Reactive data streams
- **Navigation Compose**: Type-safe navigation

## Project Structure

### Data Models
- `Appointment`: Represents a clinic appointment
- `Doctor`: Represents a healthcare provider
- `User`: Represents a patient profile

### Screens
1. **HomeScreen**: Dashboard with quick access to popular doctors
2. **AppointmentsScreen**: Manage appointments with filtering
3. **DoctorsScreen**: Browse and search doctors by specialty
4. **ProfileScreen**: View and edit patient information

## Setup Instructions

### Prerequisites
- Android Studio Arctic Fox or later
- Kotlin 1.9.21+
- Android SDK 34 (Target)
- Minimum SDK 24 (API level)
- Firebase project setup

### Build & Run

1. Clone the repository
2. Open the project in Android Studio
3. Add `google-services.json` to `app/` directory (download from Firebase Console)
4. Sync Gradle files
5. Run the app on an emulator or physical device

### Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com)
2. Create a new project
3. Add Android app to your project
4. Download `google-services.json`
5. Place it in `app/` directory
6. Create Firestore Database collections:
   - `doctors`: Store doctor information
   - `appointments`: Store appointment records
   - `users`: Store patient profiles

## Firestore Collections Schema

### Doctors Collection
```
id: string
name: string
specialty: string
experience: string
rating: number
image: string
bio: string
phone: string
availability: string
createdAt: timestamp
```

### Appointments Collection
```
id: string
userId: string
doctorId: string
doctorName: string
specialty: string
appointmentDate: number (timestamp)
appointmentTime: string
serviceType: string
status: string (pending, confirmed, completed)
notes: string
createdAt: timestamp
```

### Users Collection
```
id: string
name: string
email: string
phone: string
dateOfBirth: string
gender: string
address: string
profileImage: string
createdAt: timestamp
```

## Permissions

The app requires the following permissions:
- `INTERNET` - API calls
- `ACCESS_NETWORK_STATE` - Network status
- `POST_NOTIFICATIONS` - Push notifications

## Key Libraries & Versions

- Kotlin: 1.9.21
- Jetpack Compose: 2023.10.01
- Room: 2.6.1
- Hilt: 2.48
- Firebase BOM: 32.7.1
- Material3: 1.1.2

## Development Roadmap

- [ ] Video consultation feature
- [ ] Prescription management
- [ ] Payment integration
- [ ] Dental records upload
- [ ] Full offline support
- [ ] Doctor rating and review system
- [ ] SMS appointment reminders
- [ ] Insurance claim management
- [ ] Multi-language support

## Testing

### Unit Tests
```bash
./gradlew test
```

### Integration Tests
```bash
./gradlew connectedAndroidTest
```

## Contributing

1. Create a feature branch
2. Make your changes
3. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For issues, feature requests, or questions, please open an issue on GitHub.

---

**Note**: Make sure to configure your Firebase project and add the `google-services.json` file before running the app.

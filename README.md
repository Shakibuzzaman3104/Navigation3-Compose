# Navigation3-Compose Demo Project

A comprehensive showcase of modern Android development using **MVI architecture** with **Unidirectional Data Flow (UDF)**, **Clean Architecture**, and **Multi-modular architecture**. This project demonstrates the latest **Navigation3** library from Google and serves as a guideline for developers adopting these cutting-edge technologies.

## 🚀 Project Highlights

- **Navigation3 Alpha**: Implementation of Google's latest navigation library with type-safe navigation
- **MVI Pattern**: Complete Model-View-Intent architecture with UDF
- **Multi-modular Architecture**: Feature-based modular design with clear separation of concerns
- **Clean Architecture**: Layered architecture with domain, data, and presentation layers
- **Convention Plugins**: Custom Gradle plugins for consistent build configuration
- **Offline-First**: NetworkBoundResource pattern for robust data handling

## 📱 Features

- **Posts Management**: Browse, search, and view detailed posts
- **Albums Gallery**: Photo albums with grid layout
- **Todo Lists**: Task management with CRUD operations
- **User Profiles**: User information and management
- **Offline Support**: Data caching and offline-first approach
- **Search Functionality**: Real-time filtering across features
- **Pull-to-Refresh**: Modern Material3 refresh implementation

## 🏗️ Architecture Overview

### Multi-Modular Structure

```
Navigation3-Compose/
├── app/                           # Main application module
├── build-logic/                   # Convention plugins
│   └── convention/               # Custom Gradle plugins
├── core/                          # Core shared modules
│   ├── database/                 # Room database layer
│   ├── navigation/               # Navigation routes & back stack
│   ├── network/                  # Networking layer
│   └── ui/                       # Shared UI components
└── feature/                      # Feature modules
    ├── album/                    # Albums feature
    ├── posts/                    # Posts feature
    ├── todo/                     # Todo feature
    └── users/                    # Users feature
```

### Clean Architecture Layers

#### 1. **Presentation Layer**
- **ViewModels**: Handle UI state and business logic
- **Composables**: Jetpack Compose UI components
- **State Management**: Reactive state with StateFlow

#### 2. **Domain Layer**
- **Repository Interfaces**: Data operation contracts
- **Entities**: Domain models

#### 3. **Data Layer**
- **Repository Implementations**: Concrete data operations
- **API Services**: Retrofit network interfaces
- **Local Database**: Room database with DAOs

### MVI Pattern Implementation

```kotlin
// State
data class PostsState(
    val isLoading: Boolean = false,
    val posts: List<ModelPost> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)

// Intent
sealed class PostsIntent {
    object RefreshData : PostsIntent()
    data class UpdateSearchQuery(val query: String) : PostsIntent()
}

// Effect
sealed class PostsScreenEffect {
    data class ShowSnackBar(val message: String) : PostsScreenEffect()
}
```

## 🛠️ Technologies Used

### Core Technologies
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern declarative UI (`2025.06.01`)
- **Navigation3**: Type-safe navigation (`1.0.0-alpha04`)
- **Material3**: Material Design 3 components (`1.4.0-alpha17`)

### Architecture & Patterns
- **Hilt**: Dependency injection (`2.56.2`)
- **Kotlin Coroutines**: Asynchronous programming (`1.10.2`)
- **StateFlow/SharedFlow**: Reactive state management
- **Room Database**: Local persistence (`2.7.2`)

### Networking & Data
- **Retrofit**: HTTP client (`3.0.0`)
- **Moshi**: JSON serialization (`1.15.2`)
- **OkHttp**: HTTP client with logging
- **Coil**: Image loading for Compose

### Development Tools
- **Timber**: Logging framework
- **KotlinX Serialization**: Type-safe serialization
- **Convention Plugins**: Custom Gradle plugins

## 🎯 Key Features Demonstrated

### 1. **Navigation3 Implementation**
- Type-safe navigation with Kotlin serialization
- Custom BackStack management for tab navigation
- Modular navigation setup with entry providers
- Deep linking support

### 2. **MVI with UDF**
- Immutable state management
- Sealed classes for intents and effects
- Clear separation of concerns
- Reactive UI updates

### 3. **Multi-modular Architecture**
- Feature-based modules
- Shared core modules
- Convention plugins for consistency
- Clear module boundaries

### 4. **NetworkBoundResource Pattern**
- Offline-first data strategy
- Automatic cache management
- Network error handling
- Loading state management

## 🚦 Getting Started

### Prerequisites
- Android Studio Iguana or later
- Kotlin 1.9.0 or later
- Android SDK 34
- Java 8 or later

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/Navigation3-Compose.git
```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Run the app on an emulator or physical device

## 📋 Convention Plugins

The project uses custom Gradle convention plugins for consistent build configuration:

- `diatomicsoft.android.application` - Base application setup
- `diatomicsoft.android.application.compose` - Compose application setup
- `diatomicsoft.android.library` - Library module setup
- `diatomicsoft.android.library.compose` - Compose library setup
- `diatomicsoft.android.feature` - Feature module setup
- `diatomicsoft.android.hilt` - Hilt integration
- `diatomicsoft.navigation` - Navigation setup
- `diatomicsoft.database` - Room database setup
- `diatomicsoft.moshi` - JSON serialization setup

## 📊 Data Flow

```
UI Layer (Compose) 
    ↓ Intent
ViewModel (MVI)
    ↓ Business Logic
Repository
    ↓ Data Operations
NetworkBoundResource
    ↓ Cache Strategy
Local Database ←→ Remote API
```

## 🤝 Contributing

This is a demo project designed for learning and showcasing modern Android development practices. Feel free to:

1. Fork the project
2. Create feature branches
3. Submit pull requests
4. Report issues or suggestions

## 📚 Learning Resources

This project demonstrates concepts from:
- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Navigation3 Documentation](https://developer.android.com/guide/navigation)
- [MVI Pattern Guide](https://developer.android.com/topic/architecture/ui-layer/stateholders)
- [Multi-module Architecture](https://developer.android.com/topic/modularization)
- [Clean Architecture Principles](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Google Android Team for Navigation3 and modern architecture guidelines
- The Android development community for best practices and patterns
- Contributors to the open-source libraries used in this project

---

**Note**: This project uses Navigation3 alpha version. Please check the [official documentation](https://developer.android.com/guide/navigation) for the latest updates and stable releases.
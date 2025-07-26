# 🎓 University Wishlist App - Android University Explorer & Favorites

University Wishlist App is a modern Android application that allows users to browse universities from an external data source, search through them, filter by country, and add favorite universities for easy access later.

---

## 🏛️ System Architecture Flow

The University Wishlist App follows the MVVM (Model-View-ViewModel) architecture pattern with Repository pattern for clean separation of concerns and maintainable code structure.

### 📐 Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                           UI LAYER                              │
│  ┌─────────────────┐    ┌─────────────────┐                    │
│  │  Universities   │    │   Wishlist      │                    │
│  │   Fragment      │    │   Fragment      │                    │
│  │                 │    │                 │                    │
│  │ - RecyclerView  │    │ - RecyclerView  │                    │
│  │ - SearchView    │    │ - Checkboxes    │                    │
│  │ - Filter Menu   │    │ - Remove Items  │                    │
│  └─────────────────┘    └─────────────────┘                    │
│           │                       │                            │
│           │                       │                            │
└───────────┼───────────────────────┼────────────────────────────┘
            │                       │
            │                       │
┌───────────┼───────────────────────┼────────────────────────────┐
│           │        VIEWMODEL LAYER│                            │
│           ▼                       ▼                            │
│  ┌─────────────────┐    ┌─────────────────┐                    │
│  │ UniversityViewModel  │ WishlistViewModel │                  │
│  │                 │    │                 │                    │
│  │ - LiveData      │    │ - LiveData      │                    │
│  │ - Search Logic  │    │ - CRUD Logic    │                    │
│  │ - Filter Logic  │    │ - Check Logic   │                    │
│  └─────────────────┘    └─────────────────┘                    │
│           │                       │                            │
│           │                       │                            │
└───────────┼───────────────────────┼────────────────────────────┘
            │                       │
            │                       │
┌───────────┼───────────────────────┼────────────────────────────┐
│           │     REPOSITORY LAYER  │                            │
│           ▼                       ▼                            │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │            UniversityRepository                         │   │
│  │                                                         │   │
│  │ - Manages data sources                                  │   │
│  │ - Handles caching logic                                 │   │
│  │ - Coordinates API and Database calls                    │   │
│  │ - Data transformation                                   │   │
│  └─────────────────────────────────────────────────────────┘   │
│           │                       │                            │
│           │                       │                            │
└───────────┼───────────────────────┼────────────────────────────┘
            │                       │
            │                       │
┌───────────┼───────────────────────┼────────────────────────────┐
│           │       DATA LAYER      │                            │
│           ▼                       ▼                            │
│  ┌─────────────────┐    ┌─────────────────┐                    │
│  │   API SERVICE   │    │  LOCAL DATABASE │                    │
│  │                 │    │                 │                    │
│  │ - Retrofit      │    │ - Room DB       │                    │
│  │ - HTTP Calls    │    │ - SQLite        │                    │
│  │ - JSON Parsing  │    │ - DAO           │                    │
│  │ - Error Handle  │    │ - Entities      │                    │
│  └─────────────────┘    └─────────────────┘                    │
│           │                       │                            │
│           │                       │                            │
└───────────┼───────────────────────┼────────────────────────────┘
            │                       │
            ▼                       ▼
┌─────────────────┐    ┌─────────────────┐
│  External API   │    │   Local Storage │
│                 │    │                 │
│ - Universities  │    │ - Favorites DB  │
│   REST API      │    │ - User Prefs    │
│                 │    │                 │
└─────────────────┘    └─────────────────┘
```

### 🔄 Data Flow Process

#### 1. **Loading Universities**
```
User Opens App → Fragment → ViewModel → Repository → API Service → External API
                     ↑                                                    ↓
            UI Updates ← LiveData ← Data Processing ← JSON Response ← HTTP Response
```

#### 2. **Adding to Favorites**
```
User Clicks Heart → Fragment → ViewModel → Repository → Room Database
                       ↑                                        ↓
              UI Updates ← LiveData ← Success Callback ← SQLite Insert
```

#### 3. **Search & Filter Flow**
```
User Types/Selects → Fragment → ViewModel → Repository → API Service (with params)
                        ↑                                           ↓
                UI Updates ← LiveData ← Filtered Data ← API Response
```

### 🧩 Component Responsibilities

#### **UI Layer (Fragments & Adapters)**
- Display data to user
- Handle user interactions
- Observe ViewModel changes
- Update UI based on LiveData

#### **ViewModel Layer**
- Business logic processing
- State management
- LiveData exposure
- Communication bridge between UI and Repository

#### **Repository Layer**
- Single source of truth
- Data source coordination
- Caching strategies
- Network and database abstraction

#### **Data Layer**
- Network operations (Retrofit)
- Local storage (Room)
- Data models and entities
- Error handling and parsing

### 📊 Key Architecture Benefits

- **Separation of Concerns**: Each layer has distinct responsibilities
- **Testability**: Easy unit testing for each component
- **Maintainability**: Changes in one layer don't affect others
- **Scalability**: Easy to add new features and data sources
- **Lifecycle Awareness**: ViewModels survive configuration changes
- **Reactive UI**: LiveData ensures UI stays synchronized with data

---

## 📋 Table of Contents

- [Features](#-features)
- [Technologies Used](#-technologies-used)
- [System Architecture Flow](#-system-architecture-flow)
- [App Screenshots](#-app-screenshots)
- [Project Structure](#-project-structure)
- [Setup and Installation](#-setup-and-installation)
- [Required Permissions](#-required-permissions)
- [Known Issues and Notes](#-known-issues-and-notes)
- [Author](#-author)

---

## ✨ Features

- 📚 **Display University List** from external API (Open Universities API)
- 🔎 **Search Universities** by name with ease
- 🌍 **Filter Universities by Country** using sidebar navigation
- ❤️ **Add Universities to Favorites** with ability to remove favorites
- ✅ **Enable/Disable Checkbox State** for each university in favorites
- 📦 **Local Storage for Favorites** using Room Database
- 🧩 **MVVM Architecture Design** for separation of concerns
- 🔄 **Real-time UI Updates** when data changes via LiveData
- 🧪 **Error Handling** for network failures and data loading issues

---

## 🛠️ Technologies Used

| Category | Technology |
| -------- | ---------- |
| Programming Language | Kotlin |
| UI Design | XML Layout + Material Components |
| Architecture | MVVM (ViewModel + LiveData) |
| Database | Room (SQLite) |
| Networking | Retrofit + Coroutines |
| State Management | LiveData + ViewModel |

---

## 🎬 App Screenshots

### 📱 Screen Captures

<div align="center">
  <img src="screenshots/universities_list.png" width="30%" />
  <img src="screenshots/search_university.png" width="30%" />
  <img src="screenshots/wishlist_with_checkboxes.png" width="30%" />
</div>

---

## 🏗️ Project Structure

```
UniversityWishlistApp/
├── app/src/main/java/
│   ├── ui/
│   │   ├── universities/
│   │   │   ├── UniversitiesFragment.kt
│   │   │   ├── UniversityAdapter.kt
│   │   ├── wishlist/
│   │   │   ├── WishlistFragment.kt
│   │   │   ├── WishlistAdapter.kt
│   ├── data/
│   │   ├── api/                    # Retrofit API interfaces
│   │   ├── db/                     # Room Database entities & DAOs
│   │   ├── model/                  # Data classes
│   │   ├── repository/             # Repository pattern
│   ├── viewmodel/                  # ViewModels for MVVM
├── app/src/main/res/
│   ├── layout/                     # XML Layout files
│   ├── drawable/                   # Icons & images
│   ├── menu/                       # Sidebar menu XML
├── screenshots/                    # App screenshots for README
└── README.md                       # This file
```

---

## 🚀 Setup and Installation

1. **Clone the Repository:**
```bash
git clone https://github.com/username/university-wishlist-app.git
cd university-wishlist-app
```

2. **Open Project in Android Studio**

3. **Sync Gradle**

4. **Run the App** on emulator or physical device

5. **Test Features:**
   - Browse universities
   - Search functionality
   - Filter by country from sidebar
   - Add/Remove from favorites
   - Enable/Disable checkbox in favorites

---

## 🔐 Required Permissions

| Permission | Purpose | When Requested |
| ---------- | ------- | -------------- |
| INTERNET | Connect to internet for fetching university data | Always |
| ACCESS_NETWORK_STATE | Check network connectivity status | Always |

---

## ❗ Known Issues and Notes

- Ensure internet connection when opening the app to fetch data from API
- You may experience loading delays depending on your network speed
- Checkbox state in favorites is stored locally and needs sync when adding/removing items
- The app uses Open Universities API which may have rate limits

---

## 📝 Code Implementation Details

### Key Components:

#### 1. University Data Model
```kotlin
data class University(
    val name: String,
    val country: String,
    val web_pages: List<String>,
    val domains: List<String>
)
```

#### 2. Room Database Entity
```kotlin
@Entity(tableName = "favorite_universities")
data class FavoriteUniversity(
    @PrimaryKey val id: String,
    val name: String,
    val country: String,
    val webPages: String,
    val isChecked: Boolean = false
)
```

#### 3. API Service Interface
```kotlin
interface UniversityApiService {
    @GET("search")
    suspend fun getUniversities(
        @Query("country") country: String? = null
    ): List<University>
}
```

#### 4. Repository Pattern
```kotlin
class UniversityRepository(
    private val apiService: UniversityApiService,
    private val favoriteDao: FavoriteUniversityDao
) {
    suspend fun getUniversities(country: String? = null) = apiService.getUniversities(country)
    
    fun getAllFavorites() = favoriteDao.getAllFavorites()
    
    suspend fun addToFavorites(university: FavoriteUniversity) = favoriteDao.insert(university)
    
    suspend fun removeFromFavorites(university: FavoriteUniversity) = favoriteDao.delete(university)
}
```

#### 5. ViewModel Implementation
```kotlin
class UniversityViewModel(private val repository: UniversityRepository) : ViewModel() {
    
    private val _universities = MutableLiveData<List<University>>()
    val universities: LiveData<List<University>> = _universities
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun loadUniversities(country: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getUniversities(country)
                _universities.value = result
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}
```

---

## 👨‍💻 Author

**Mahmoud Atia**
- 🌐 [GitHub Profile](https://github.com/mahmoudatia)
- 📧 Email: mahmoud.atia@example.com
- 💼 LinkedIn: [Mahmoud Atia](https://linkedin.com/in/mahmoudatia)

---

<div align="center">
  <h3>⭐ If you like this project, don't forget to give it a star on GitHub!</h3>
  <p>Developed with love by Mahmoud Atia ❤️</p>
</div>

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📞 Support

If you have any questions or need help with the project, please open an issue on GitHub or contact me directly.

---

**Happy Coding! 🚀**

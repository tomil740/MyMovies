# Movies App

## Overview

The Movies App is an Android application that provides a dynamic and scalable interface for exploring movie data, sorting movies by various criteria, and managing a personalized list of favorite movies. The app pulls its data from the TMDb (The Movie Database) API, which provides comprehensive details about movies, including titles, overviews, release dates, and user ratings.

With a focus on performance, user experience, and scalability, the app supports features like:

- **Movie Data Representation:** The app fetches movie data from the TMDb API and displays it in a clean, user-friendly interface.
- **Sorting Options:** Users can sort the list of movies by several criteria, including:
  - Popularity
  - Rating
  - Release Date
- **Favorites List:** Users can mark movies as favorites, which are stored locally for easy access at any time.
- **Paging & Caching:** For efficient data loading, the app uses Google’s Paging 3 library and local Room database for caching.

---

## Architecture & Technologies

### MVVM & Clean Architecture

The app follows MVVM (Model-View-ViewModel) and Clean Architecture to ensure scalability, maintainability, and separation of concerns. This approach allows for easy testing and future extensibility.

### Repository Pattern

The Repository Pattern is used to manage data from both the network and local database. This helps centralize data management and keeps the ViewModel decoupled from the data sources.

### Technologies Used

- **Jetpack Compose:** For building the UI in a declarative, scalable way.
- **Paging 3:** Efficiently handles large datasets with pagination.
- **Room Database:** Caches data locally for offline support and better performance.
- **OkHttp:** Handles network requests to fetch data from the TMDb API.
- **Material 3:** Provides a modern, adaptive UI with dynamic theming.

---

## Performance & Scalability

- **Paging & Caching:** Uses Paging 3 for loading large movie lists efficiently and Room for local caching, ensuring smooth scrolling and offline access.
- **Dynamic Theming:** Supports both light and dark modes with Material 3’s dynamic theming.

---

## Paging 3 Library and Repository Pattern

In this app, we utilize Paging 3 to efficiently load and display movie data in a `LazyColumn` (a scrollable list) in the UI. The Paging 3 library from Google helps manage large datasets by breaking them into smaller chunks, or pages, and loading data incrementally as the user scrolls. This approach ensures that the app uses memory efficiently and provides a smooth user experience even with large amounts of data.

However, using Paging 3 for data management introduces a slight deviation from the classic Repository Pattern. Typically, the repository pattern abstracts the data layer, making it independent of specific data sources. In the case of Paging 3, the repository itself is tightly coupled with data sources (like the API and Room database) and needs to provide data in the form of `PagingData` (a special type of data stream).

### Why Paging 3?

- **Efficiency:** It loads data incrementally as needed, reducing memory usage and network calls.
- **Scalability:** Paging allows the app to handle large datasets efficiently, improving performance.
- **Built-in support:** Paging 3 integrates seamlessly with Jetpack Compose and supports both local caching (with Room) and network data fetching (with Retrofit and OkHttp), making it an ideal choice for this app.

### Trade-off with Repository Pattern

While Paging 3 is highly efficient, it requires the repository to directly manage data sources, which is a minor deviation from the ideal separation of concerns in a strict Repository Pattern. However, considering the requirements of the app—efficient data handling with large datasets and lazy loading—Paging 3 was chosen as the most reliable and scalable solution for this specific scenario.

---

# RemoteMediator vs PagingSource: Choosing the Right Approach for Data Fetching

The `PagingSource` approach is a flexible solution for handling pagination and data fetching from both local databases and remote APIs. While the `RemoteMediator` is often the ideal choice for seamlessly integrating these data sources, providing automatic synchronization, error handling, and pagination management, I chose to implement `PagingSource` after encountering several unclear bugs with the `RemoteMediator`. 

The `PagingSource` offers more explicit control over when to fetch from the API or fallback to local data, making it a better fit for scenarios that require fine-tuned error handling and custom data fetching behavior. Although `PagingSource` requires more manual pagination logic, it offers stability and control, making it an effective solution for managing large datasets while leveraging local caching efficiently.

Additionally, the app's local database is designed with normalization in mind, ensuring that data is efficiently stored and relationships between entities (such as movies and sorting mappings) are managed properly. This improves performance, reduces redundancy, and ensures that the app’s data layer remains clean and scalable.

---

## Authentication with TMDB API

The app integrates authentication with the TMDB API, which enables additional features beyond the default guest access. While guests can use basic functionality, authenticated users can access advanced features, such as tracking their favorite movies and managing other personalized data within the API.

### Authentication Flow

Authentication in the app is handled by redirecting users to TMDB's website for login via an intent. Once the user successfully logs in, a session ID is obtained and used to interact with the API on behalf of the authenticated user. This session ID is stored and used for further API calls to access personalized data like favorites and account details.

### Implementation Details

The authentication mechanism was implemented in a slightly unconventional way by modifying the repository pattern. Instead of having a direct connection to the authentication state from a repository, the repository serves as a connection point to the authentication process.

A special object is used to store authentication data, such as the session ID and associated keys, as a shared state across the app. This approach serves as a preparation for adding security measures to protect this data in a production environment. While the current implementation functions as a basic wrapper around authentication, it lays the groundwork for future enhancements, including secure storage and better data protection.

---

## How to Run

1. Clone the repository
First, clone the repository to your local machine by running the following command in your terminal:
    ```bash
    git clone https://github.com/yourusername/movies-app.git
    ```

2. Add your API key
To access the movie data from the TMDb API, you need to provide your API key.

Navigate to the `data/util` directory in the project.

Create or open the `ApiConstants.kt` file.

Add your TMDb API key in the file.

You can obtain your TMDb API key by creating an account on The Movie Database and generating an API key under your account settings.

3. Open the project in Android Studio
Open the project in Android Studio.

4. Run the app
Connect a physical device or start an Android emulator (Android 5.0+). Then press Run in Android Studio.

---

## Future Improvements

- **Search Functionality:** Implement a search feature for users to find specific movies.

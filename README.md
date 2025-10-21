# S4639940_Assignment2 — Paw-radise Book App 

##  Student Details
**Name:** Haboon Maalin  
**Student ID:** 4639940  
**Unit:** NIT3213 — Android Development  
**Assignment:** Assignment 2

---

## Overview
This Android app allows users to log in using their first name and student ID, view a dashboard of classic books, and see detailed book information.  
It demonstrates **MVVM architecture**, **Koin dependency injection**, **ViewModel testing**, and **Retrofit network integration**.

---

## Features
- **Login Screen** – Validates first name and 7-digit student ID.
- **Dashboard Screen** – Displays a list of books fetched from a remote API.
- **Details Screen** – Shows author, year, and description of the selected book.
- **Navigation Component** – Enables smooth navigation between screens.
- **Koin Dependency Injection** – Manages ViewModel and repository dependencies.
- **JUnit Unit Tests** – Verifies login validation and ViewModel states.

---

## Tech Stack
| Category | Library / Tool |
|-----------|----------------|
| Language | Kotlin |
| Architecture | MVVM |
| Dependency Injection | Koin |
| Networking | Retrofit + Moshi |
| Coroutines | Kotlin Coroutines |
| Testing | JUnit4 + kotlinx-coroutines-test |
| UI | Jetpack Navigation, RecyclerView, ConstraintLayout |

---

## Unit Testing
Unit tests for `LoginViewModel` verify error handling for:
- Blank first name
- Invalid student ID length

**File:**  
`app/src/test/java/com/example/s4639940_assignment2/LoginViewModelTest.kt`

---

## Navigation Flow
1. **LoginFragment** →
2. **DashboardFragment** →
3. **DetailsFragment**

Back navigation is supported using the action bar "up" button.

---

## Project Structure
com.example.s4639940_assignment2
├── data
│   ├── model
│   ├── remote
│   └── repo
├── di
├── ui
│   ├── login
│   ├── dashboard
│   └── details
└── theme

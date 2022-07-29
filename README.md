# Kotlin MVVM app using clean architecture, Jetpack, Hilt, Retrofit and Coroutines Flow API

## Introduction
This application displays public holidays for countries and also allows displaying of common public holidays 
for given two countries. Built using modern Android development strategies focusing on the following key aspects:

- Code structuring as per clean Architecture
- Using MVVM Pattern as per Google's recommendation
- Using Hilt for DI
- Android Architecture Components (LiveData, ViewModel, Navigation)
- Kotlin features (Lambdas, Extension functions, typealias, sealed class and Coroutines)

## App Overview
The app features a 2 screen navigation

- Country input screen for entering two countries for which the holiday request is going to be made.

- Holiday list screen which outputs the holidays for the two countries with buttons that toggle 
  which days should be displayed (public holidays common to both country A and country B, 
  holidays that are only in country A, but not in country B and holidays that are only in country B, but not country A)

Navigation between the screens has been done using the Jetpack Navigation library.

## Libraries The App uses libraries and tools used to build Modern Android application, mainly part of Android Jetpack
- [Kotlin](https://kotlinlang.org/)
- [Coroutines Flow API](https://kotlinlang.org/docs/reference/coroutines/flow.html)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [Android desugaring for Java 8+ APIs](https://developer.android.com/studio/write/java8-support#library-desugaring)
- [Retrofit](https://square.github.io/retrofit/)
- [Hilt](https://dagger.dev/hilt/) for dependency injection
- [Android KTX](https://developer.android.com/kotlin/ktx) features
- [CountryCodePicker](https://github.com/hbb20/CountryCodePickerProject)
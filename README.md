# AssigmentHub
Android app to manage assignments for teachers in college.


[![Build](https://github.com/hellosagar/AssigmentHub/workflows/Build/badge.svg?branch=main)](https://github.com/hellosagar/AssigmentHub/actions?query=workflow%3ABuild)
[![Lint](https://github.com/hellosagar/AssigmentHub/workflows/Lint/badge.svg?branch=main)](https://github.com/hellosagar/AssigmentHub/actions?query=workflow%3ALint)

[![GitHub license](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
![Github Followers](https://img.shields.io/github/followers/hellosagar?label=Follow&style=social)
![GitHub stars](https://img.shields.io/github/stars/hellosagar/AssigmentHub?style=social)
![GitHub forks](https://img.shields.io/github/forks/hellosagar/AssigmentHub?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/hellosagar/AssigmentHub?style=social)
![Twitter Follow](https://img.shields.io/twitter/follow/SagarKh03980377?label=Follow&style=social)

**AssigmentHub** is a Assignment manager ✅ Android application 📱 to help teachers to mange assignments in University built to demonstrate use of *Modern Android development* tools. Dedicated to all Android Developers and *AWS Amplify* with ❤️. 

***You can Install and test latest AssigmentHub app from below 👇***

[![AssigmentHub App](https://img.shields.io/badge/AssigmentHub✅-APK-red.svg?style=for-the-badge&logo=android)](https://github.com/hellosagar/AssigmentHub/releases/latest/download/app.apk)

## About
It simply manges **assignments**

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

# Package Structure
    
    dev.hellosagar.assigmenthub    # Root Package
    .
    ├── data                # For data handling.
    │   └── repository      # Single source of data.   
    |
    ├── di                  # Dependency Injection             
    |
    ├── ui                  # Activity/View layer
    │   ├── viewmodel       # ViewModels
    │   └── adapter         # Adpaters
    │   └── fragment        # Fragnents
    |
    └── utils               # Utility Classes / Kotlin extensions


## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

## Discuss 💬

Have any questions, doubts or want to present your opinions, views? You're always welcome. You can [start discussions](https://github.com/hellosagar/AssigmentHub/discussions).

## Contact
If you need any help, you can connect with me.

Visit:- [hellosagarkhurana](https://www.instagram.com/hellosagarkhurana/)

## License
```
MIT License

Copyright (c) 2020 Sagar Khurana

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

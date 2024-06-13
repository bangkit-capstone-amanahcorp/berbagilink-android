# Event-Organizer

This repository contains an Android app that is implemented using XML Layout, enabling a modern and flexible UI design.

## Table of Contents

- [Introduction](#introduction)
- [Tech Used](#tech-used)
- [Dependencies](#dependencies)

## Introduction

The Event Organizer app aims to optimize the process of organizing events through the development of a mobile application tailored to meet the various needs of organizers. Recognizing the challenges inherent to traditional website platforms, our app focuses on improving accessibility and user experience. The app provides an easy-to-use interface and can be accessed on mobile devices, facilitating smooth management of activities from pre-event planning to post-event tasks. The app caters to a variety of roles within the organizer team, including administrators, cashiers, and tenants, offering features specific to each role such as visitor ticket scanning, transaction management, and booth ticket tracking. By overcoming the limitations of traditional website platforms and utilizing ubiquitous mobile technology, this event organizer platform strives to simplify the event management process, ensuring greater efficiency and satisfaction for organizers.

## Tech Used

1. Kotlin
2. Android Studio
3. Postman

## Dependencies

| Dependency                                    | Purpose                                              |
|-----------------------------------------------|------------------------------------------------------|
| implementation(libs.androidx.core.ktx)        | Core KTX library for AndroidX                        |
| implementation(libs.androidx.appcompat)       | Support for older versions of Android                |
| implementation(libs.material)                 | Material design components                           |
| implementation(libs.androidx.activity)        | Activity KTX for AndroidX                            |
| implementation(libs.androidx.constraintlayout)| Layout management                                    |
| implementation(libs.play.services.mlkit.barcode.scanning) | Barcode scanning with ML Kit                          |
| testImplementation(libs.junit)                | Unit testing framework                               |
| androidTestImplementation(libs.androidx.junit)| AndroidX JUnit integration                           |
| androidTestImplementation(libs.androidx.espresso.core) | UI testing with Espresso                              |
| implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1") | LiveData KTX extension                               |
| implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1") | ViewModel KTX extension                              |
| implementation("androidx.activity:activity-ktx:1.7.2") | Activity KTX extension                               |
| implementation("com.squareup.okhttp3:logging-interceptor:4.11.0") | Logging for network requests                         |
| implementation("com.squareup.retrofit2:retrofit:2.9.0") | Retrofit for network requests                        |
| implementation("com.squareup.retrofit2:converter-gson:2.9.0") | JSON conversion with Gson                            |
| implementation("androidx.datastore:datastore-preferences:1.0.0") | Preferences DataStore                                |
| implementation("androidx.datastore:datastore-preferences-core:1.0.0") | Core Preferences DataStore                           |
| implementation("com.github.bumptech.glide:glide:4.16.0") | Image loading and caching                            |
| implementation("de.hdodenhof:circleimageview:3.1.0") | Circular ImageView                                   |
| implementation("androidx.paging:paging-runtime-ktx:3.1.1") | Paging library for loading data                      |
| implementation("androidx.fragment:fragment-ktx:1.2.5") | Fragment KTX extension                               |
| implementation("androidx.viewpager2:viewpager2:1.0.0") | ViewPager2 for swipeable views                       |
| implementation("androidx.camera:camera-camera2:1.3.0") | CameraX Camera2 library                              |
| implementation("androidx.camera:camera-lifecycle:1.3.0") | CameraX lifecycle library                            |
| implementation("androidx.camera:camera-view:1.3.0") | CameraX View library                                 |
| implementation("com.google.mlkit:barcode-scanning:17.2.0") | ML Kit Barcode Scanning                              |
| implementation("androidx.camera:camera-mlkit-vision:1.4.0-alpha02") | CameraX ML Kit Vision integration                    |
| implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01") | Swipe to refresh layout                              |
| implementation("com.github.denzcoskun:ImageSlideshow:0.1.0") | Image slideshow component                            |

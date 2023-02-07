# Overall

This demo app is to demonstrate my knowledge of development of Android applications. The application
could be much smaller in terms of the code and architecture but I wanted to showcase best practices
of architecture and development. Unfortunately because of limited time I worked on the app it still
requires improvements.

## Built With 🏗

- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
    - [Navigation](https://developer.android.com/guide/navigation)
- [Retrofit](https://square.github.io/retrofit/)

## How Do I Navigate This Project?

`core-data` is a base class for network data related sources are kept.
`core-resources` is a base class for android resources and common views module.
`core-testing` is a base class for shared testing resoureces.
`core-util` module has some shared functionality that can be used across the app,
`feature-categories` is a UI module. It has the List feature that you see when you first open the
app, and its UI is written in XML.
`feature-petdetails` is a UI module. It has the detail view that you're taken to when you select
an item from the list, and its UI is written in XML.

## Future Plans / What Would I Do Differently? ⏳

This is a live document, so these thoughts might change in the future.

- support of landscape mode/ big screens
- `core-tracking` where all tracking things would live
- missing helpers tools e.g. leakCanary, Chucker
- display of error dialog
- pagination of imaged in PetCategoryFeed
- Unit Testing
- UI testing
- accessibility
- proper theming/styling

## Acknowledgments & Inspiration

Some aspects of the code are inspired by open source community contributions.

Chris Bane's [tivi](https://github.com/chrisbanes/tivi)
Gabor
Varadi's [fragmentviewbindingdelegate](https://github.com/Zhuinden/fragmentviewbindingdelegate-kt)
Google's [Architecture Samples](https://github.com/android/architecture-samples)
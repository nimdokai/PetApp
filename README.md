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

`core-data` is a base class for data source (repositories).
`core-domain` is a base class for business related sources are kept.
`core-netowrk` is a base class for  networking.
`core-resources` is a base class for android resources and common views module.
`core-testing` is a base class for shared testing resources.
`core-util` module has some shared functionality that can be used across the app,
`feature-categories` is a UI module. It has the List feature that you see when you first open the
app, and its UI is written in XML.
`feature-petdetails` is a UI module. It has the detail view that you're taken to when you select
an item from the list, and its UI is written in XML.


## Before testing:

Please make sure to add your own cat api key to your local.properties.
1. Getting the token - sign up for token - https://thecatapi.com/signup 
2. Receive the token via email
3. put your token to 'local.properties' as: 'cat_api_key="token_from_email"'

## what is completed:
- Overall architecture of the app
- displaying categories
- displaying feed for each category
- zoom in for each details
- supporting dark mode
- support of landscape mode

## Things to do:

### UI:
- placeholders for images
- styling
- animations
- some xmls contains dimensions directly instead of extracted
- pagination (right now each category displays only 25 items)
- better transitions for loading the spinner and the content.
- icon launcher

### Features:
- cat details right now displays image (unfortunately, the cat api doesn't allow zipping together breeds and categories for cats, though it's not in the documentation) hence I can't display for categories image more than image itself.
- possibility to search by breed
- add to favourite
- see the favourite cats
- uploading your own photo
- deleting your photo
- sharing cat details
- bottom bar to switch between above features:
- landscape mode is not fully supported -> same design as on portrait mode
- dark mode is partially supported
- `core-tracking` where all tracking things would live
- UI testing
- accessibility
- missing helpers tools e.g. leakCanary, Chucker

### Unit Tests
- I missed unit tests for most classes because of lack of time.
  Currently, only test are:
- PetCategoryViewModelTest
- CatRepositoryTest

### Architecture
- I sometimes took shortcuts in terms of the object's structure, flow and performance of solution - it might cause some issues.

## Acknowledgments & Inspiration

Some aspects of the code are inspired by open source community contributions.

- Chris Bane's [tivi](https://github.com/chrisbanes/tivi)
- Gabor Varadi's [fragmentviewbindingdelegate](https://github.com/Zhuinden/fragmentviewbindingdelegate-kt)
- Google's [Architecture Samples](https://github.com/android/architecture-samples)
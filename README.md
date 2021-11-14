# MVVM-KOTLIN
Android application using MVVM to display a list of events from Ticketmaster.
Tapping add to favourite will move the event to the top of the list in a separate
“Favourites” section and also to the “Favourites Events” screen.

## Libraries used in this project
- [Constraint layout](https://developer.android.com/training/constraint-layout/index.html)
- [Gson](https://github.com/google/gson)
- [Koin](https://insert-koin.io/)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)
- [OkHttp](https://github.com/square/okhttp)
- [Picasso](https://github.com/square/picasso)
- [Retrofit](https://square.github.io/retrofit/)
- [Room](https://developer.android.com/topic/libraries/architecture/room.html)
- [RxJava and RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Robolectric](https://github.com/robolectric/robolectric)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)

## Events Screen
- Name
- Image
- Venue name
- Dates
- Add to favourite action

## Tests
- Activity
- View Model
- Repository
- Use Case
- Dao
- Modules

## Favourites Events Screen
- Name
- Image
- Venue name
- Dates
- Add to favourite action

## What you should expect
- Display a list of the next 50 events in London from the [Ticketmaster API]
(http://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2)
- MVVM architecture pattern
- Storing favourites events using Room DB
- DEBUG and RELEASE mode
- Handling screen rotation
- Swipe to refresh
- Project hosted on [GitHub](https://github.com/uhconst/Ticket_Master.git)

## Important to notice
- In the <b>Favourites Screen</b> the app requests the API for each event using the event id. For some reason the API is returning sometimes *429 Too Many Requests*. To test that screen would be better having no more than 3 favourite events to avoid multiple calls and the API problem

## Developed by
Uryel Constancio - [uryelhenrique.c@gmail.com](uryelhenrique.c@gmail.com)

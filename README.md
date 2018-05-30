[![Circle CI](https://circleci.com/gh/ivelius/RestaurantReservations2/tree/master.svg?style=svg)](https://circleci.com/gh/ivelius/RestaurantReservations2/tree/master)

# Restaurant Reservations
This little reservations management app is a tiny POC for tables management in a typical restaurant.

![](https://github.com/ivelius/RestaurantReservations2/blob/master/screenshots/device-2018-05-25-204318.png?raw=true)

![](https://github.com/ivelius/RestaurantReservations2/blob/master/screenshots/device-2018-05-25-204431.png?raw=true)


## The use case
First time when you start the app , you should be connected to the internet. As soon as you going through all your screens at least once , data is being cached and managed in the local database.
As long as there are no real APIs for tables and users management , I assumed that downloading the data only once will suffice. Hence the app can be used completely offline once the customers and table data downloaded at least once.

You can reserve one or all free tables for a customer . Each customer can regret his reservation and unmark his reserved tables. By doing so the free tables will be available for other customers . Customers can also regret their choice later in time.

Once every 15 minutes (or so , depending on OS ...) , all the reserved tables will be freed and available for a new selection.

## Language
My language of choice for this task was Kotlin . However there are plenty of other Android (and not ony ...) projects in my public repo that are written in Java  . I highly recommend to check them out. 

## Architecture
I went with MVP , since it is fair simple and quite powerful . The perfect balance between complex solution and lack of architecture at all. MVP has proven to suite most of my needs during the years of development. I have also added an additional level of abstraction of data fetching by using repositories (following the repository design pattern) ... 
I have integrated a [Circle CI](https://circleci.com/) to this projects , and all the tests are run when changes are made.


## UI
I've decided to keep things simple. Using a plain Material design provided by vanilla Android (which I find nice BTW). I don't use custom views nor Fragments for this task as they would rather add complexity than serve a real purpose.

## Frameworks
I didn't abuse the project with unnecessary dependencies , I've only used what I though is needed.

  - Dagger 2 
  - RxKotlin 2
  - Retrofit 2
  - Mockito
  - Espresso
  - Room (for database)
  - WorkScheduler (for recurring tasks)

## Tests
I have focused on 2 types of tests :

  - BL/Presenters tests - which are implemented through plain Junit Testing and run very fast. I mostly test the business logic and interaction with views (which are abstracted)
  - Instrumentation Tests - utilizing Espresso framework to mainly test the views behaviour with mocked data and presenters.
  
In production apps I also put a lot of emphasize on End To End test (via user journeys) and data integrity tests (basically I verify that my API calls return expected content).

## Installation
  - Clone the project and open it in Android Studio 3.+
  - Run the project on Emulator / Device with internet connection (Suggest using Tablet ...)
  - To run tests I suggest to run them directly from the Android Studio by pressing a "play" button that appears near the test

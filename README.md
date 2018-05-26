# RestaurantReservations
This little reservations management app is a tiny POC for tables management in a typical restaraunt.

![](https://github.com/ivelius/RestaurantReservations2/blob/master/screenshots/device-2018-05-25-204318.png?raw=true)

![](https://github.com/ivelius/RestaurantReservations2/blob/master/screenshots/device-2018-05-25-204431.png?raw=true)


## The use case
First time when you start the app , you should be connected to the internet. As soon as you going through all your screens at least once , data is being cached and managed in the local data base.
As long as there are no real APIs for tables and users management , I assumed that downloading the data only once will suffice. Hence the app can be used completey offline once the customers and table data downloaded at least once.

You can reserve one or all free tables for a customer . Each customer can regret his reservation and unmark his reserved tables. By doing so the free tables will be availible for other customers . Customers can also regret their choice later in time.

Once every 15 minutes (or so , depending on OS ...) , all the reserved tables will be freed and availible for a new selection.

## Language
My language of choice for this task was Kotlin . However there are plenty of other Android (and not ony ...) projects in my public repo that are written in Java  . I hightly recommend to check them out. 

## Architechture
I went with MVP , since it is fair simple and quite powerful . The perfect balance between complex solution and lack of architecture at all. MVP has proven to suite most of my needs during the years o developement. I have also added a little element of Clean Architecture which is a "Use Case" . Basically it is needed to add yet another layer of abstraction to the presenters . Alternativley in this task I could use a Repository pattern instead of UseCases ... 

## Constraints
I am under sevire time pressure so I have to choose wisley what I should focus on. I am focusing on functionality and architecture. Trying to do some adequate testing without polishing every aspect of this task.

This app is targeted for tablets , I did constrain the orientation for landscape only , for sake of simplicity and to not handle the state loss during rotation (which might be important , but I am intentionally leaving this out). I have tested this app on Nexus 10 Simulator running API 27. I have set minimum SDK , just to use some new fancy features of Android Jetpack and Kotlin. In real production life we all still target Gingerbread :(

Even though I know it is hard to impress with this task solution , I am again hightly recommend to check my other projects on this Github . I am especially proud of the following :

  - https://github.com/ivelius/Watch-View
  - https://github.com/ivelius/DurakGameClient
  - https://github.com/ivelius/OpenGLEngineAndroid
  - https://github.com/ivelius/Virtual-City
  
But there are more , written in different languages and technologies ...

## Bonus task (search functionality)
Due to time constraints I am not implementing this feature.  However if I would , I would start from something rather simple like this [stack overflow post describes](https://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview). A few years back I have implemented my own search functionality completley from scratch using material design (when there was no support for material design yet). You can check it out in [Home24 application](https://play.google.com/store/apps/details?id=com.home24.android&hl=de) which is mostly written by me. Unfortunatley I cannot share the code with you due to properiaty reasons.


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
  - WorkScheduler (for reoccuring tasks)

## Tests
Tests are important and there are many types of tests out there. I tried to test as much as I could given the time constraints . Obviously I did not cover all the edge cases nor the failure states . In reality writing tests is more time consuming than writing feature code , but it pays off in the end ...

I have focused on 2 types of tests :

  - BL/Presenters tests - which are implemented through plain Junit Testing and run very fast. I mostly test the buisness logic and interaction with views (which are abstracteed)
  - Instrumentation Tests - utiizing Espresso framework to mainly test the views behaviour with mocked data and presenters.
  
In production apps I also put a lot of emphasize on End To End test (via user jorneys) and data integrity tests (basically I verify that my API calls return expected content).

## Installation
  - Clone the project and open it in Android Studio 3.+
  - Run the project on Emulator / Device with internet connection (Suggest using Tablet ...)
  - To run tests I suggest to run them directly from the Android Studio by pressing a "play" button that appears near the test

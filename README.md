# Gelato Android developer challenge

#### Task Explanation:
STACK USED:
- Kotlin
- MVVM + DataBinding + Livedata pattern
- Coroutines (not used Rxjava as I feel Coroutines Job's are more efficient)
- Retrofit for Network lib
- okhttp-logging-interceptor for log request / response data
- Hilt for Dependency Injection
- Navigation Component to load fragment
- Mockito , AndroidJUnit4 for testing

TESTING PROCESS Explanation:
- Added single test case for checking 1 api and their responses viz movie collection api
- Implemented Unit test case to test viewmodel and livedata usecases with flow

####  Note (Below I'm sharing some highlight w.r.t to this task):
- Caching responses and offline support.
- Handled screen orientation change
- Removed MyViewModelFactory (ViewModelProvider.Factory) class as we are using @HiltViewModel
- Livedata can emit more than once as there are operation's on error responses and retrying.
- Added unit test for viewmodel and test to verify user experience with fake data.
- Added test case of viewmodel to check the api working.
- Have added comments wherever necessary
- Used coroutines to do major work/job in background and give output whenever ready.

#### Need Java 11
#### Fix incase you get plugin error (like below):

 An exception occurred applying plugin request [id: 'com.android.application']
 > Failed to apply plugin 'com.android.internal.application'.
    > Android Gradle plugin requires Java 11 to run. You are currently using Java 1.8.
      You can try some of the following options:
        - changing the IDE settings.
        - changing the JAVA_HOME environment variable.
        - changing `org.gradle.java.home` in `gradle.properties`.

 Change Gradle JDK
 Goto => AndroidStudio > Preferences > Build Tools > Gradle >  Look for Gradle JDK and Select `Embedded JDK version 11.0.10` from dropdown.

#### Potential Improvements to keep working on:
- Implement Room Databse for limited offline support
- Bifurcate Collection and Movie into different viewmodels
- Improve test coverage

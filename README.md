# Common Mobile Services

[![](https://jitpack.io/v/Explore-In-HMS/common-mobile-services/month.svg)](https://jitpack.io/#Explore-In-HMS/common-mobile-services) [![](https://jitpack.io/v/Explore-In-HMS/common-mobile-services.svg)](https://jitpack.io/#Explore-In-HMS/common-mobile-services)

It is a library that provides a common interface for mobile services for Android developers. Its aim is removing special mobile service dependencies for your app code.
This has mainly two benefits:
1- It removes creation and lifecycle control code from your app code. So your classes get rid of one extra responsibility.(Separation of Concerns)
2- Makes it possible to use different mobile services. For example not all Android devices has Google Mobile Services(GMS). By doing this library you can use
different services without modifying your app code.

This library contains 2 services for now: Google Mobile Services(GMS) and Huawei Mobile Services(HMS). This library will grow with the added services.
If you want to contribute don't hesitate to create PR's :)

Currently added services: `MapKit`, `Location`, `Analytics`, `CreditCardScanner`, `Awareness`, `Scan`, `Translate`, `Speech To Text`, `Text To Speech`, `Object Detection`, `Text Recognition`, `Face Detection`, `Language Detection`, `Image Classification`, `Account`, `Auth`, `Safety`, `Crash`, `Push`, `Site`, `Identity` and `Remote Config`.

## Table of Contents

- [Common Mobile Services](#common-mobile-services)
  - [Table of Contents](#table-of-contents)
  - [How to install](#how-to-install)
    - [Add gradle repositories](#add-gradle-repositories)
    - [Configure app information](#configure-app-information)
    - [Download](#download)
  - [Result Data](#result-data)
  - [MapKit](#mapkit)
    - [Download](#download-1)
    - [Usage](#usage)
  - [Location](#location)
    - [Download](#download-2)
    - [Usage](#usage-1)
      - [Getting location continuously](#getting-location-continuously)
      - [Using the Mock location feature](#using-the-mock-location-feature)
      - [Geofence](#geofence)
        - [Creating and Adding a Geofence](#creating-and-adding-a-geofence)
        - [Create a request for adding a geofence](#create-a-request-for-adding-a-geofence)
        - [Remove a geofence](#remove-a-geofence)
      - [Activity Recognition](#activity-recognition)
        - [Activity Transition](#activity-transition)
        - [Stop activity transition update request](#stop-activity-transition-update-request)
  - [Analytics](#analytics)
    - [Download](#download-3)
    - [Usage](#usage-2)
  - [Credit Card Scanner](#credit-card-scanner)
    - [Download](#download-4)
    - [Usage](#usage-3)
  - [Awareness](#awareness)
    - [Download](#download-5)
    - [Usage](#usage-4)
  - [Scan](#scan)
    - [Download](#download-6)
    - [Usage](#usage-5)
  - [Translate](#translate)
    - [Download](#download-7)
    - [Usage](#usage-6)
  - [Speech To Text](#speech-to-text)
    - [Download](#download-8)
    - [Usage](#usage-7)
  - [Text To Speech](#text-to-speech)
    - [Download](#download-9)
    - [Usage](#usage-8)
  - [Object Detection](#object-detection)
    - [Download](#download-10)
    - [Usage](#usage-9)
  - [Text Recognition](#text-recognition)
    - [Download](#download-11)
    - [Usage](#usage-10)
  - [Face Detection](#face-detection)
    - [Download](#download-12)
    - [Usage](#usage-11)
  - [Language Detection](#language-detection)
    - [Download](#download-13)
    - [Usage](#usage-12)
  - [Image Classification](#image-classification)
    - [Download](#download-14)
    - [Usage](#usage-13)
  - [Account](#account)
    - [Download](#download-15)
    - [Usage](#usage-14)
  - [Auth](#auth)
    - [Download](#download-16)
    - [Usage](#usage-15)
      - [Facebook Sign in](#facebook-sign-in)
      - [Huawei or Google Sign in](#huawei-or-google-sign-in)
      - [Email Sign in](#email-sign-in)
      - [Phone Sign in](#phone-sign-in)
      - [Twitter Sign in](#twitter-sign-in)
      - [Anonymous Sign in](#anonymous-sign-in)
      - [Email Sign up](#email-sign-up)
      - [Phone Sign up](#phone-sign-up)
      - [Reset password](#reset-password)
      - [Update Username](#update-username)
      - [Update Photo](#update-photo)
      - [Get Code](#get-code)
      - [Update Email](#update-email)
      - [Update Phone](#update-phone)
      - [Update Password](#update-password)
      - [Link With Twitter](#link-with-twitter)
      - [Link With Facebook](#link-with-facebook)
      - [Link With Email](#link-with-email)
      - [Link With Phone](#link-with-phone)
      - [Unlink](#unlink)
      - [ReAuthenticate](#reauthenticate)
      - [Delete User](#delete-user)
  - [Safety](#safety)
    - [Download](#download-17)
    - [Usage](#usage-16)
      - [User Detection](#user-detection)
      - [Root Detection](#root-detection)
      - [AppChecks](#appchecks)
      - [URLCheck](#urlcheck)
  - [Crash](#crash)
    - [Download](#download-18)
    - [Usage](#usage-17)
      - [Available functions](#available-functions)
  - [Push](#push)
    - [Download](#download-19)
    - [Usage](#usage-18)
      - [Slider Push Notification](#slider-push-notification)
  - [Site Kit](#site-kit)
    - [Download](#download-20)
    - [Usage](#usage-19)
  - [Identity Kit](#identity-kit)
    - [Download](#download-21)
    - [Usage](#usage-20)
  - [Remote Config](#remote-config)
    - [Download](#download-22)
    - [Usage](#usage-21)

## How to install

### Add gradle repositories

Add the JitPack repository, Huawei repo and classpaths to your build file

```gradle
buildscript {
    repositories {
        //...
        maven {url 'https://developer.huawei.com/repo/'}
    }
    dependencies {
        //...
        classpath 'com.huawei.agconnect:agcp:1.7.1.300'
        classpath 'com.google.gms:google-services:4.3.14'
    }
}

allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
        maven { url "https://developer.huawei.com/repo/" }
    }
}
```

### Configure app information

1. Get the `agconnect-services.json` file from the AGC Console and place in the `app` folder
1. Get the `google-services.json` file from the Firebase Console and place in the `app` folder
1. Add Google and Huawei gradle plugins to `App` level `build.gradle`

```gradle
apply plugin: 'com.huawei.agconnect'
apply plugin: 'com.google.gms.google-services'
```

### Download

**Latest version: [2.2.2](https://github.com/Explore-In-HMS/common-mobile-services/releases)**

Add dependencies as required to your app.

> Google, Firebase and Huawei dependencies are included so you do NOT need to declare them manually.

```gradle
dependencies {
    //...
    def cms_version = "2.2.2"
    implementation "com.github.Explore-In-HMS.common-mobile-services:mapkit:$cms_version"
    implementation "com.github.Explore-In-HMS.common-mobile-services:location:$cms_version"
    implementation "com.github.Explore-In-HMS.common-mobile-services:analytics:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:creditcardscanner:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:awareness:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:scan:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:translate:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:speechtotext:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:texttospeech:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:objectdetection:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:textrecognition:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:facedetection:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:languagedetection:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:imageclassification:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:account:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:auth:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:safety:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:crash:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:push:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:site:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:identity:$cms_ver"
    implementation "com.github.Explore-In-HMS.common-mobile-services:remoteconfig:$cms_ver"
}
```

_**You only need to add the specific modules your app will use**_

## Result Data

Libraries usually return data in a common data wrapper class: `ResultData`. It has three states: `Success`, `Failed` and `Loading`. This wrapper class helps us to manage the data status.

```kt
sealed class ResultData<out T>{
    data class Loading(val nothing: Nothing? = null): ResultData<Nothing>()
    data class Success<out T>(val data: T? = null): ResultData<T>()
    data class Failed(val error: String? = null,val errorModel: ErrorModel?=null): ResultData<Nothing>()
}
```

## MapKit

<details> 
This library wraps a mapview to use it in application code. It has [CommonMap](https://github.com/Explore-In-HMS/common-mobile-services/blob/master/mapkit/src/main/java/com/hms/lib/commonmobileservices/mapkit/factory/CommonMap.kt) interface which can be [GoogleCommonMapImpl](https://github.com/Explore-In-HMS/common-mobile-services/blob/master/mapkit/src/main/java/com/hms/lib/commonmobileservices/mapkit/factory/GoogleCommonMapImpl.kt) or [HMSCommonMapImpl](https://github.com/Explore-In-HMS/common-mobile-services/blob/master/mapkit/src/main/java/com/hms/lib/commonmobileservices/mapkit/factory/HuaweiCommonMapImpl.kt). A custom view created to hold these map views: [CommonMapView](https://github.com/Explore-In-HMS/common-mobile-services/blob/master/mapkit/src/main/java/com/hms/lib/commonmobileservices/mapkit/CommonMapView.kt). This view also manages lifecycle events of its map.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:mapkit:<versionName>"
```

### Usage

First add Google Map api key to `Manifest.xml`

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="xxx-xxx"/>
```

Add `CommonMapView` to your layout file

```xml
<com.hms.commonmobileservices.mapkit.CommonMapView
        android:id="@+id/mapView"
        app:hms_api_key="..."
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

It's advised to put the api_key that taken from AppGallery console

Then in your Activity or Fragment

```kt
val commonMap=mapView.onCreate(savedInstanceState,lifecycle).apply {
  getMapAsync {
    it.addMarker("Marker", "Snippet", 41.0540255, 29.0129607)
    it.animateCamera(41.0540255, 29.0129607, 10f)
  }
}
```

`CommonMapView.onCreate()` function needs savedInstanceState and lifecycle. It uses `savedInstanceState` to keep the map state when `Activity`/`Fragment` is destroyed and recreated. Uses `lifecycle` to redirect lifecycle events to the map view via `LifecycleObserver`. So you don't need to override lifecycle callback like `onDestroy()` and call `commonMap.onDestroy()`

`getMapAsync()` function takes a callback that informs you when the map is ready. After this callback is called you can use `commonMap` functions.

Here is all functions of `CommonMap`

```kt
interface CommonMap : UISettings{
    fun getMapView(): View
    fun onCreate(bundle: Bundle?)
    fun getMapAsync(onMapReadyListener: (map: CommonMap) -> Unit)
    fun addMarker(title: String?=null,
                  snippet: String?=null,
                  latitude: Double,
                  longitude: Double,
                  iconBitmap: Bitmap?=null,
                  anchor : Pair<Float,Float>?=null
                  ) : CommonMarker
    fun addPolygon(commonPolygonOptions: CommonPolygonOptions) : CommonPolygon
    fun setOnInfoWindowClickListener(markerClickCallback : (markerTitle: String?,
                                                            markerSnippet: String?) -> Unit)
    fun setOnMapClickListener(onClick : (commonLatLng: CommonLatLng) -> Unit)
    fun moveCamera(latitude: Double, longitude: Double, zoomRatio: Float)
    fun animateCamera(latitude: Double, longitude: Double, zoomRatio: Float)
    fun setMyLocationEnabled(myLocationEnabled: Boolean?,context: Context) : Boolean
    fun clear()
    fun onSaveInstanceState(bundle: Bundle)
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
    fun onLowMemory()
}
```

You can also use `UISettings` functions

```kt
interface UISettings {
    fun isCompassEnabled(): Boolean

    fun setCompassEnabled(compassEnabled: Boolean?)

    fun isIndoorLevelPickerEnabled(): Boolean

    fun setIndoorLevelPickerEnabled(indoorLevelPickerEnabled: Boolean?)

    fun isMapToolbarEnabled(): Boolean

    fun setMapToolbarEnabled(mapToolbarEnabled: Boolean?)

    fun isMyLocationButtonEnabled(): Boolean

    fun setMyLocationButtonEnabled(myLocationButtonEnabled: Boolean?)

    fun isRotateGesturesEnabled(): Boolean

    fun setRotateGesturesEnabled(rotateGesturesEnabled: Boolean?)

    fun isScrollGesturesEnabled(): Boolean

    fun setScrollGesturesEnabled(scrollGesturesEnabled: Boolean?)

    fun isScrollGesturesEnabledDuringRotateOrZoom(): Boolean

    fun setScrollGesturesEnabledDuringRotateOrZoom(scrollGesturesEnabledDuringRotateOrZoom: Boolean?)

    fun isTiltGesturesEnabled(): Boolean

    fun setTiltGesturesEnabled(tiltGesturesEnabled: Boolean?)

    fun isZoomControlsEnabled(): Boolean

    fun setZoomControlsEnabled(zoomControlsEnabled: Boolean?)

    fun isZoomGesturesEnabled(): Boolean

    fun setZoomGesturesEnabled(zoomGesturesEnabled: Boolean?)

    fun setAllGesturesEnabled(allGestureEnable: Boolean?)
}
```

</details>

## Location

<details>

This library provides a [CommonLocationClient](https://github.com/Explore-In-HMS/common-mobile-services/blob/master/location/src/main/java/com/hms/lib/commonmobileservices/location/CommonLocationClient.kt). It is a base class for GMS `FusedLocationProviderClient` and HMS `FusedLocationProviderClient`. This library handles enabling GPS and getting location permissions at runtime.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:location:<versionName>"
```

### Usage

First, initialize `CommonLocationClient`:

```kt
val locationClient: CommonLocationClient? = LocationFactory.getLocationClient(this,lifecycle)
```

It needs `Activity` to handle runtime permissions, create fused client and check GPS setting is on or off. Needs `lifecycle` to manage the lifecycle of its element according to `Activity`/`Fragment` lifecycles. Finally needs to permission callbacks implemented by the application code. It covers denying permissions one time and permanently cases.

Then you must check if the GPS setting is on or off. If it is enabled you are ready to use `CommonLocationClient`.

```kt
locationClient.enableGps{enableGPSFinalResult, error ->
    when(enableGPSFinalResult){
        EnableGPSFinalResult.ENABLED -> showToast("GPS Enabled")
        EnableGPSFinalResult.FAILED -> showToast("GPS Enabling Failed")
        EnableGPSFinalResult.USER_CANCELLED -> showToast("GPS Enabling Cancelled")
    }
}
```

If `enableGPSFinalResult` is `EnableGPSFinalResult.ENABLED` you can use clients functions.
Getting last known location:

```kt
locationClient?.getLastKnownLocation{
    when(it.state){
        LocationResultState.SUCCESS->{
            Toast.makeText(
                context,
                "Current Location Latitude: ${it.location?.latitude} ,Current Location Longitude: ${it.location?.longitude}",
                Toast.LENGTH_LONG
            ).show()
        }
        LocationResultState.FAIL->{
            Toast.makeText(
                context,
                "Failed to get current location",
                Toast.LENGTH_LONG
            ).show()
        }
    }
 }
```

#### Getting location continuously

```kt
locationClient?.requestLocationUpdates(priority = Priority.PRIORITY_HIGH_ACCURACY,interval = 1000){
   var message:String?=null
   when(it.state){
       LocationResultState.SUCCESS->{
           message="Current Latitude: ${it.location?.latitude} \nCurrent Longitude: ${it.location?.longitude}"
       }
       LocationResultState.FAIL->{
           message="Failed to get current location"
       }
       LocationResultState.GPS_DISABLED->{
           message="User disabled gps"
       }
       LocationResultState.LOCATION_UNAVAILABLE->{
           message="Location unavailable due to the environment"
       }
   }
   Log.d(TAG,message)
   locationUpdatesLiveData.value=message
}
```

When you don't need to location updates you can remove listener like this:

```kt
locationClient?.removeLocationUpdates()
```

#### Using the Mock location feature

To use the mock location function, go to **Settings** > **System & updates** > **Developer options** > Select mock location app and select the desired app. (If Developer options is unavailable, go to **Settings** > **About phone** and tap **Build number** for seven consecutive times. Then, Developer options will be displayed in **System & updates**.)
You need to add necessary permission to use Mock location feature to `AndroidManifest.xml` file.

```xml
<uses-permission android:name="android.permission.ACCESS_MOCK_LOCATION"/>
```

```kt
fun setMockMode(isMockMode : Boolean) : Work<Unit>
```

```kt
commonLocationClient?.setMockMode(true).addOnSuccessListener {  }.addOnFailureListener {  }
```

```kt
fun setMockLocation(location: Location): Work<Unit>
```

```kt
val mockLocation = Location(LocationManager.GPS_PROVIDER)
     mockLocation.latitude = latitude
     mockLocation.longitude = longitude
     commonLocationClient?.setMockLocation(mockLocation).addOnSuccessListener {  }.addOnFailureListener {  }
```

#### Geofence

The usage of the Geofence feature is as follows.
To use the geofence service APIs of Location Kit, declare the `ACCESS_FINE_LOCATION` and `ACCESS_COARSE_LOCATION` permissions in the `AndroidManifest.xml` file.

```xml
 <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
```

First you have to initialize the `CommonGeofenceService` object.

```kt
   private var geofenceService: CommonGeofenceService? = null
   private var geofenceList: ArrayList<Geofence>? = null
   private var pendingIntent: PendingIntent?=null

   geofenceService = CommonGeofenceService()
   pendingIntent = getPendingIntent()
   geofenceList = ArrayList()
```

##### Creating and Adding a Geofence

```kt
geofenceList!!.add(Geofence()
  .also { it.uniqueId= "testGeofence" }.also { it.conversions = Geofence.ENTER_GEOFENCE_CONVERSION }
  .also { it.validDuration = Geofence.GEOFENCE_NEVER_EXPIRE }.also { it.latitude = latitude }.also { it.longitude = longitude }
  .also { it.radius = 20F })
```

##### Create a request for adding a geofence

```kt
fun createGeofenceRequest(): GeofenceRequestRes{
      return GeofenceRequestRes().also { it.geofenceList = geofenceList }.also { it.initConversion = GeofenceRequestRes.INITIAL_TRIGGER_ENTER }
  }
```

You should initialize the pendingIntent object that we have created.

```kt
  private fun getPendingIntent(): PendingIntent? {
      val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
      intent.action = "com.hms.commonmobileservices.GEOFENCE"
      return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
  }
```

After all this process, we need to send the request to add a geofence via `createGeofenceList()` method.

```kt
fun createGeofenceList(context: Context, geofenceReq: GeofenceRequestRes,pendingIntent: PendingIntent): Work<Unit>
```

```kt
geofenceService.createGeofenceList(applicationContext,createGeofenceRequest(),pendingIntent)
  .addOnSuccessListener {

  }.addOnFailureListener {

  }
```

After adding Geofence, we need to create a Broadcast receiver and define it in `AndroidManifest.xml` file so that Geofence can be triggered.

```xml
 <receiver android:name=".GeofenceBroadcastReceiver"
    android:exported="true">
      <intent-filter>
	   <action android:name="com.hms.commonmobileservices.GEOFENCE" />
      </intent-filter>
 </receiver>
```

We will be able to listen to the Geofence triggering process thanks to the Broadcast receiver.Thanks to our `CommonGeofenceData` class, you can get all information about the triggered event.

```kt
class GeofenceBroadcastReceiver : BroadcastReceiver() {
   override fun onReceive(context: Context?, intent: Intent) {
       val geofenceData = CommonGeofenceData().fetchDataFromIntent(context,intent)
       val errorCode = geofenceData.errorCode
       val conversion = geofenceData.conversion
       val convertingGeofenceList = geofenceData.convertingGeofenceList
       val convertingLocation = geofenceData.convertingLocation
       val isFailure = geofenceData.isFailure
   }
}
```

##### Remove a geofence

With the `deleteGeofenceList()` method, you can delete the previously created geofence list according to the geofence id list or according to the pending intent value.

```kt
fun deleteGeofenceList(context: Context,geofenceList:List<String>):Work<Unit>

fun deleteGeofenceList(context: Context,pendingIntent: PendingIntent):Work<Unit>
```

```kt
val geofenceIdList : ArrayList<String> = ArrayList()
geofenceIdList.add("testGeofence")
geofenceService.deleteGeofenceList(applicationContext,geofenceIdList)
   .addOnSuccessListener { }
   .addOnFailureListener { }
```

```kt
geofenceService.deleteGeofenceList(applicationContext,pendingIntent)
   .addOnSuccessListener { }
   .addOnFailureListener { }
```

#### Activity Recognition

Thanks to the Activity Recognition feature, you can follow the user's activity instantly.
To use the activity recognition service in versions earlier than Android 10, add the following permission in the `AndroidManifest.xml` file.

```xml
 <uses-permission android:name="com.huawei.hms.permission.ACTIVITY_RECOGNITION"/>
 <uses-permission android:name="com.google.android.gms.permission.ACTIVITY_RECOGNITION"/>
```

To use the activity recognition service in Android 10 and later versions, add the following permission in the `AndroidManifest.xml` file;

```xml
 <uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
```

After adding the required permissions, you must create and initialize the `CommonActivityIdentificationService` object.

```kt
private var activityIdentificationService : CommonActivityIdentificationService?=null
private var pendingIntent: PendingIntent? = null

activityIdentificationService = CommonActivityIdentificationService()
pendingIntentEdit = getPendingIntent()

private fun getPendingIntent(): PendingIntent?{
      val intent = Intent(this, ActivityRecognitionReceiver::class.java)
      intent.action = "com.hms.commonmobileservices.ACTIVITY_RECOGNITION"
      return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
  }
```

With the `createActivityIdentificationUpdates()` method, the user's activities are controlled according to the specified time.

```kt
fun createActivityIdentificationUpdates(context: Context,intervalMillis:Long, pendingIntent: PendingIntent):Work<Unit>
```

```kt
activityIdentificationService.createActivityIdentificationUpdates(applicationContext,5000,pendingIntent)
   .addOnSuccessListener { }
   .addOnFailureListener { }
```

You can follow to the user activities with the Broadcast Receiver we will create.

```xml
  <receiver android:name=".ActivityRecognitionReceiver"
     android:exported="true">
        <intent-filter>
        <action android:name="com.hms.commonmobileservices.ACTIVITY_RECOGNITION" />
        </intent-filter>
  </receiver>
```

With `fetchDataFromIntent()` method you can get detected activities status list.

```kt
fun fetchDataFromIntent(context:Context, intent:Intent): CommonActivityIdentificationResponse
```

```kt
class ActivityRecognitionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val activityIdentificationResponse = CommonActivityIdentificationResponse().fetchDataFromIntent(context,intent)
        val detectedActivityList = activityIdentificationResponse.activityIdentificationDataList
    }
 }
```

To Stop requesting activity identification updates, you can use `deleteActivityIdentificationUpdates()` method.

```kt
fun deleteActivityIdentificationUpdates(context: Context,pendingIntent: PendingIntent):Work<Unit>
```

```kt
activityIdentificationService.deleteActivityIdentificationUpdates(applicationContext,pendingIntent)
        .addOnSuccessListener { }
        .addOnFailureListener { }
```

##### Activity Transition

Activity transition is a process of detecting user activity converting from one to another. You can call the `createActivityConversionUpdates()` method in your app to request user activity conversion updates.

```kt
fun createActivityConversionUpdates(context: Context,activityConversionReq: CommonActivityConversionReq,pendingIntent: PendingIntent):Work<Unit>
```

```kt
val activityConversionEnter = CommonActivityConversionInfo()
        .also { it.activityType = CommonActivityIdentificationData().activityType(applicationContext,CommonActivityIdentificationData.STILL)}
        .also { it.conversionType = CommonActivityConversionInfo.ENTER_ACTIVITY_CONVERSION }
val activityConversionExit = CommonActivityConversionInfo()
        .also { it.activityType = CommonActivityIdentificationData().activityType(applicationContext,CommonActivityIdentificationData.STILL) }
        .also { it.conversionType = CommonActivityConversionInfo.EXIT_ACTIVITY_CONVERSION }

val activityConversionList: MutableList<CommonActivityConversionInfo> = ArrayList()
      activityConversionList.add(activityConversionEnter)
      activityConversionList.add(activityConversionExit)

val activityConRequest = CommonActivityConversionReq()
      activityConRequest.activityConversions = activityConversionList

activityIdentificationService.createActivityConversionUpdates(applicationContext,activityConRequest,pendingIntent)
      .addOnSuccessListener { }
      .addOnFailureListener { }
```

You can receive the broadcast activity transition result with broadcast receiver. With Common `fetchDataFromIntent()` method you can get activity transition status.

```kt
fun fetchDataFromIntent(context: Context, intent: Intent): CommonActivityConversionResponse
```

```kt
class ActivityRecognitionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val activityTransitionResponse = CommonActivityConversionResponse().fetchDataFromIntent(context,intent)
        val activityTransitionList = activityTransitionResponse.getActivityConversionDataList
    }
 }
```

##### Stop activity transition update request

If you want to stop getting activity transition information, you can use `deleteActivityConversionUpdates()` method.

```kt
fun deleteActivityConversionUpdates(context: Context,pendingIntent: PendingIntent):Work<Unit>
```

```kt
activityIdentificationService.deleteActivityConversionUpdates(applicationContext,pendingIntent)
       .addOnSuccessListener { }
       .addOnFailureListener { }
```

</details>

## Analytics

<details>

It is made to ease logging for your project. You can log your event with a one line of code.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:analytics:<versionName>"
```

### Usage

First, get the instance of common logger by calling `CommonAnalytics.instance(this)`. The function takes a context instance as a parameter. Then returns an implementation of `CommonAnalytics`. It can be the class that uses `Firebase` or `HiAnalyticsTools` for logging.
If you want to implement your own logger you can just implement the CommonAnalytics interface and use it instead.

You can log your events with classic Bundle.

```kt
val myEvent = Bundle().apply {
    putString("productName", "socks")
    putInt("quantity", 5)
}
CommonAnalytics.instance(this)?.saveEvent("cartEvent", myEvent)
```

You can also delete previously collected data with the `clearCachedData()` method.
Data configured through the following APIs will be cleared:

`onEvent`
`setUserId`
`setUserProfile`
`addDefaultEventParams`

```kt
CommonAnalytics.instance(this)?.clearCachedData()
```

You can set whether to enable event tracking. If event tracking is disabled, no data is recorded or analyzed. The default value is `true`

```kt
CommonAnalytics.instance(this)?.setAnalyticsEnabled(false)
```

User id can be assigned. When this method is called, a new session will be generated if the old value of id is not empty and is different from the new value. If you do not want to use id to identify a user (for example, when a user signs out), you must set id to null.

```kt
CommonAnalytics.instance(this)?.setUserId("id")
```

You can set user attributes. The values of user attributes remain unchanged throughout the app lifecycle and during each session. A maximum of 25 user attributes are supported. If the name of an attribute set later is the same as that of an existing attribute, the value of the existing attribute is updated.

```kt
CommonAnalytics.instance(this)?.setUserProfile("name", "value")
```

This method sets the session timeout interval. A new session will be generated when an app is running in the foreground but the interval between two adjacent events exceeds the specified timeout interval. The minimum value is 5 seconds, and the maximum value is 5 hours. If the specified value is beyond the value range, the boundary value is used. By default, the timeout interval is 1,800,000 milliseconds (that is, 30 minutes).

```kt
val milliseconds = 10000
CommonAnalytics.instance(this)?.setSessionDuration(milliseconds)
```

You can also add default event parameters. These parameters will be added to all events except the automatically collected events. If the name of a default event parameter is the same as that of an event parameter, the event parameter will be used.

```kt
val params = Bundle().apply {
    putString("productName", "keyboard")
    putInt("quantity", 3)
}
CommonAnalytics.instance(this)?.addDefaultEventParams(params)
```

If you want to obtain **AAID** then you can use `getAAID()` method. This method returns to you **AAID** as a string.

```kt
CommonAnalytics.instance(this)?.getAAID()
```

</details>

## Credit Card Scanner

<details>

This library reads a credit card with device camera. It uses Huawei `ML-Card-Bcr` library to scan image. But it is inherited from `CreditCardScanner` common interface, so you can use your own implementation of credit card reader. HMS library does not require a Huawei device or HMS Core, so this library works well in all devices.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:creditcardscanner:<versionName>"
```

### Usage

Get `CreditCardScanner` instance and call `scan()` function like this:

```kt
CreditCardScanner.instance(this)?.scan {
    when(it){
        is ResultData.Success ->{
            // handle success data
        }
        is ResultData.Failed->{
            // show error to user
        }
        is ResultData.Loading->{
            // show loading indicator
        }
    }
```

The `scan()` function takes a callback lambda function as a parameter. The lambda function gives us a `ResultData` sealed class object.

```kt
fun scan(callback: (ResultData<CommonCreditCardResult>) -> Unit)
```

This library has a `ScanError` class which is inherited from `ErrorModel` of `ResultData`. `ScanError` as error states to manage scan error situations.

```kt
data class ScanError(
    val errorString: String,
    val scanErrorType: ScanErrorType = ScanErrorType.ERROR
) : ErrorModel(errorString) {

    enum class ScanErrorType {
        USER_CANCELED, ERROR, DENIED
    }
}
```

</details>

## Awareness

<details>

Awareness SDK provides your app with the ability to obtain contextual information including user's behavior, audio device status, weather, and current time. Depending on the situation, this information can be obtained in certain instantly.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:awareness:<versionName>"
```

### Usage

To use Google Awareness Api Key, you need to get Google Awareness Api Key. With this Api Key, add the code block to your `AndroidManifest.xml` file as follows.

```xml
<meta-data
    android:name="com.google.android.awareness.API_KEY"
    android:value="GOOGLE_AWARENESS_API_KEY"/>
```

By using the code block below, you instantly get the data from the class you want to use the Awareness SDK for.

```kt
HuaweiGoogleAwarenessManager(this).getWeather {dataArray ->
            dataArray.handleSuccess {
                it.data?.forEach {value ->
                    Log.d("AWARENESS_WEATHER",WeatherDataValue.valueOf(value)!!.name)
                }
            }
        }
```

The `getWeather()` or `getBehavior()` or `getHeadset()` or `getTime()` functions take a callback lambda function as a parameter. The lambda function gives us a `ResultData` sealed class object.

```kt
fun getWeather(callback: (weatherVal: ResultData<IntArray>) -> Unit)
```

</details>

## Scan

<details>

Scan SDK scans and parses all major 1D and 2D barcodes, helping you quickly barcode scanning functions into your apps.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:scan:<versionName>"
```

### Usage

The scan process is started with the following line of code. `scanBarcodeResultCode` is the variable that is the result of OnActivityResult.

```kt
HuaweiGoogleScanManager(this).performScan(this,scanBarcodeResultCode)
```

After scanning is done, The result of the scanning process gets from `onActivityResult`. Using the `parseScanToTextData` function, the data is converted to string format.

```kt
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == scanBarcodeResultCode) {
                HuaweiGoogleScanManager(this).parseScanToTextData({resultData->
                    Toast.makeText(this,"The result is -> ${resultData.handleSuccess {
                        it.data
                    }}",Toast.LENGTH_SHORT).show()
                },this,data)
            }
        }
    }
```

The `parseScanToTextData()` function takes a callback lambda function as a parameter. The lambda function gives us a `ResultData` sealed class object.

```kt
fun parseScanToTextData(callback: (scanToTextResult: ResultData<String>) -> Unit, activity: Activity, data:Intent)
```

</details>

## Translate

<details>

With the support of an on-device model, the on-device translation service can translate text from the source language into the target language.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already
Add the dependency to App level `build.gradle`

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:translate:<versionName>"
```

### Usage

First, get the instance of language detection by calling `Translator.getClient(context)` The function takes `context` as parameter.

```kt
val translator = Translator.getClient(this)
```

To translate a string of text, pass the text, source language and target language to `translate(...)` method

```kt
translator.translate("text","source-lang","target-lang"){ translateResult ->
    when(translateResult){
        is TranslateResult.Success -> {
            Log.d("Translate result: ", translateResult.translatedText)
        }
        is TranslateResult.Error -> {
            //Handle exception -> translateResult.exception
        }
    }
}
```

Make sure the required translation model has been downloaded to the device.

```kt
translator.requiresModelDownload("lang-code"){ requiresModelDownloadResult ->
    when(requiresModelDownloadResult){
        is RequiresModelDownloadResult.Required -> {
            //You have to download the model to be able to translate
        }
        is RequiresModelDownloadResult.NotRequired -> {
            //You can translate text
        }
        is RequiresModelDownloadResult.Error -> {
            //Handle exception -> requiresModelDownloadResult.exception
        }
    }
}
```

On-device models can be download.

```kt
translator.downloadModel("lang-code"){ downloadModelResult ->
    when(downloadModelResult){
        is DownloadModelResult.Success -> {
            //Model Downloaded
        }
        is DownloadModelResult.Error -> {
            //Handle exception -> downloadModelResult.exception
        }
    }
}
```

Delete an unnecessary model package.

```kt
translator.deleteModel("lang-code"){ deleteModelResult ->
    when(deleteModelResult){
        is DeleteModelResult.Success -> {
            //Model Deleted
        }
        is DeleteModelResult.Error -> {
            //Handle exception -> deleteModelResult.exception
        }
    }
}
```

Ensure that the `close()` method is called when the Translator object will no longer be used.

```kt
translator.close()
```

</details>

## Speech To Text

<details>

Speech to Text can recognize speech not longer than 60s and convert the input speech into text in real time. This service uses industry-leading deep learning technologies to achieve a recognition accuracy of over 95%. Currently, Mandarin Chinese (including Chinese-English bilingual speech), English, French, German, Spanish, and Italian can be recognized. [Click here](https://developer.huawei.com/consumer/en/doc/development/HMS-Plugin-References-V1/language-0000001058780607-V1) to view instantly supported speech to text languages and language codes.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:speechtotext:<versionName>"
```

### Usage

The speech to text process is started with the following line of code. `speechToTextResultCode` is the variable that is the result of `OnActivityResult`. The language to be spoken in must be chosen.

```kt
HuaweiGoogleSpeechToTextManager(this).performSpeechToText(this,speechToTextResultCode,"en-US","API_KEY")
```

After speaking is done, The result of the speech to text process gets from `onActivityResult`. Using the `parseSpeechToTextData` function, the data is converted to string format.

```kt
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == speechToTextResultCode) {
            if (data != null) {
                HuaweiGoogleSpeechToTextManager(this).parseSpeechToTextData(
                    {
                        it.handleSuccess {
                            Log.d("Speech to text result: ",it.data.toString())
                        }
                   }, this, data, resultCode
                )
            }
        }
    }
```

The `parseSpeechToTextData()` function takes a callback lambda function as a parameter. The lambda function gives us a `ResultData` sealed class object.

```kt
fun parseSpeechToTextData(callback: (speechToTextResult: ResultData<String>) -> Unit, activity:Activity, data:Intent, resultCode:Int)
```

</details>

## Text To Speech

<details>

Text to speech (TTS) can convert text information into audio output in real time. Rich timbres are provided and the volume and speed can be adjusted (5x adjustment is supported for Chinese and English), thereby natural voices can be produced. This service uses the deep neural network (DNN) synthesis mode and can be quickly integrated through the on-device SDK to generate audio data in real time. [Click here](https://developer.huawei.com/consumer/en/doc/development/hiai-References/mlsdktts-overview-0000001050167594#section17784135144012) to view instantly supported text to speech languages and person types.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:texttospeech:<versionName>"
```

### Usage

The text to speech process is started with the following line of code. The `languageCode` and `personType` must be chosen.

```kt
HuaweiGoogleTextToSpeechManager(this).runTextToSpeech(
                    "Hello, how are you?",
                    this,
                    API_KEY,
                    "en-US",
                    "en-US-st-3"
                )
```

The `runTextToSpeech()` function takes `text`, `activity`, `apiKey`, `languageCode`, `personType` as a parameters. Then the text to speech process will start.

```kt
fun runTextToSpeech(text: String, activity: Activity, apiKey: String, languageCode: String, personType: String)
```

The `stopTextToSpeech()` function stops the speech process.

```kt
fun stopTextToSpeech()
```

</details>

## Object Detection

<details>

The object detection and tracking service can detect and track multiple objects in an image, so they can be located and classified in real time. A maximum of eight objects can be detected and tracked concurrently. The following object categories are supported: household products, fashion goods, food, places, plants, faces, and others.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:objectdetection:<versionName>"
```

### Usage

Parameters that should be used to use the object detection feature: `callback`, `context`, `bitmap`, `apiKey`. Then the objects which in the picture will be detected.

```kt
HuaweiGoogleObjectDetectionManager(this).staticImageDetection({
                when(it) {
                    is ResultData.Success -> {
                       Log.d("Object detection result: ", it.data)
                    }
                    is ResultData.Failed -> {
                        Log.d("error: ", error occured)
                    }
                }

                }, this, bitmap!!, apiKey)
```

The `staticImageDetection()` function takes a callback lambda function as a parameter. The lambda function gives us a `ResultData` sealed class object.

```kt
fun staticImageDetection(callback: (detectedValue: ResultData<List<Any>>) -> Unit, activity: Activity, bitmap: Bitmap, apiKey: String)
```

</details>

## Text Recognition

<details>

The text recognition service can extract text from images of receipts, business cards, and documents. This service is useful for industries such as printing, education, and logistics. You can use it to create apps that handle data entry and check tasks.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:textrecognition:<versionName>"
```

### Usage

Parameters that should be used to use the text recognition feature: `bitmap` and `callback`. Then the text which in the image will be recognized.

```kt
        HuaweiGoogleTextRecognitionManager(this).textRecognition(bitmap) { result ->
            when (result) {
                is RecognitionResult.Success -> {
                    Log.d("Text Recognition result: ", result.data)
                }
                is RecognitionResult.Error -> {
                    Log.d("Text Recognition result: ", result.errorMessage)
                }
            }
        }
```

The `textRecognition()` function takes a callback lambda function as a parameter. The lambda function gives us a `RecognitionResult` sealed class object.

```kt
fun textRecognition(bitmap: Bitmap, callback: (recognizedValue: RecognitionResult<Any>) -> Unit)
```

</details>

## Face Detection

<details>

With ML Kit's face detection API, you can detect faces in an image, identify key facial features, and get the contours of detected faces. Note that the API detects faces, it does not recognize people. With face detection, you can get the information you need to perform tasks like embellishing selfies and portraits, or generating avatars from a user's photo. Because ML Kit can perform face detection in real time, you can use it in applications like video chat or games that respond to the player's expressions.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:facedetection:<versionName>"
```

### Usage

Parameters that should be used to use the object detection feature: `callback`, `context`, `bitmap`, `apiKey`. Then the objects which in the picture will be detected.

```kt
HuaweiGoogleFaceDetectionManager(this).faceDetection({
                when (it) {
                    is ResultData.Success -> {
                        Log.d("Object detection result: ", it.data)
                    }
                    is ResultData.Failed -> {
                        Log.d("Object detection result: ", it.data)
                    }
                }
            }, this, bitmap!!, apiKey)
```

The `faceDetection()` function takes a callback lambda function as a parameter. The lambda function gives us a `ResultData` sealed class object.

```kt
fun faceDetection(callback: (detectedValue: ResultData<List<Any>>) -> Unit, activity: Activity, bitmap: Bitmap, apiKey: String)
```

</details>

## Language Detection

<details>

The language detection service can detect the language of text. ML Kit detects languages in text and returns the language codes (the **ISO 639-1** standard is used for languages, other than certain languages specified) and their respective confidences or the language code with the highest confidence.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:languagedetection:<versionName>"
```

### Usage

You can configure your app to automatically download the model to the device after your app is installed from the Play Store or AppGallery. Add the following statements to the `AndroidManifest.xml` file.

```xml
<manifest
    <!-- ... -->
    <meta-data
        android:name="com.huawei.hms.ml.DEPENDENCY"
        android:value= "langdetect"/>

    <meta-data
        android:name="com.google.mlkit.vision.DEPENDENCIES"
        android:value="langid" >
    <!-- ... -->
</manifest>
```

First, get the instance of language detection by calling `HuaweiGoogleLanguageDetector.getClient(....)` The function takes `context` and `confidence threshold` as parameters. The `confidence threshold` can be null. If you give a confidence threshold, ML Kit will use the minimum confidence threshold for language detection, otherwise it will use the default ML Kit settings.

```kt
val languageDetector = HuaweiGoogleLanguageDetector.getClient(context, confidenceThreshold?)
```

To detect the language of a string, pass the string to the `detectLanguage()` method.

```kt
languageDetector.detectLanguage("sourceText"){ detectResult ->
            when(detectResult){
                is DetectionResult.Success -> {
                    Log.d("Language detection result: ", detectResult.data)
                }
                is DetectionResult.Error -> {
                    Log.d("Language detection result: ", detectResult.errorMessage)
                }
            }
        }
```

To get the confidence values of a string's most likely languages, pass the string to the `detectPossibleLanguages()` method.

```kt
languageDetector.detectPossibleLanguages("sourceText"){ detectResult ->
            when(detectResult){
                is DetectionResult.Success -> {
                    detectResult.data.forEach { possibleLanguage ->
                        Log.d("Language detection result: ", "${possibleLanguage.langCode} - ${possibleLanguage.confidence}")
                    }
                }
                is DetectionResult.Error -> {
                    Log.d("Language detection result: ", detectResult.errorMessage)
                }
            }
        }
```

</details>

## Image Classification

<details>

The image classification service classifies elements in images into intuitive categories, such as people, objects, environments, activities, or artwork, to define image themes and application scenarios.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:imageclassification:<versionName>"
```

### Usage

You can configure your app to automatically download the model to the device after your app is installed from the Play Store or AppGallery. Add the following statements to the `AndroidManifest.xml` file.

```xml
<manifest
    <!-- ... -->
    <meta-data
        android:name="com.huawei.hms.ml.DEPENDENCY"
        android:value= "label"/>

    <meta-data
        android:name="com.google.mlkit.vision.DEPENDENCIES"
        android:value="ica" >
    <!-- ... -->
</manifest>
```

First, get the instance of image classification by calling `ImageClassification.getClient(....)` The function takes `context` and `confidence threshold` as parameters. The `confidence threshold` can be null. If you give a confidence threshold, ML Kit will use the minimum confidence threshold for image classification, otherwise it will use the default ML Kit settings.

```kt
val imageClassification = ImageClassification.getClient(context,confidenceThreshold?)
```

To classify the image, pass the bitmap to the analyseImage() method.

```kt
imageClassification.analyseImage(bitmap){ classificationResult ->
            when(classificationResult){
                is ClassificationResult.Success -> {
                    Log.d("Image Classification result: ", classificationResult.data)
                }
                is ClassificationResult.Error -> {
                    Log.d("Image Classification result: ", classificationResult.errorMessage)
                }
            }
        }
```

</details>

## Account

<details>

This library provides AccountService interface to handle Google Account Service and Huawei Account Kit with single code base.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:account:<versionName>"
```

### Usage

First, initialize `AccountService`:

```kt
val accountService = AccountService.Factory.create(
        context,
        SignInParams.Builder()
            .requestEmail()
            .create()
)
```

It needs `Context` to check mobile services availability and provide proper mobile service type. Needs `SignInParams` to get permission from user to get extra information like email.

Then call `silentSignIn` to get last signed account. If result is success and there is no signed account it returns `null`.

```kt
accountService.silentSignIn(object : ResultCallback<SignInUser>{
    override fun onSuccess(result: SignInUser?) {}
    override fun onFailure(error: Exception) {}
    override fun onCancelled() {}
})
```

Call `getSignInIntent` to start the `Activity` of the relevant service for the use login.

```kt
accountService.getSignInIntent { intent ->
    startActivityForResult(intent, REQUEST_CODE)
}
```

Then get result from signInIntent by calling `onSignInActivityResult`. Call this function in the `onActivityResult`.

```kt
accountService.onSignInActivityResult(intent,
    object: ResultCallback<SignInUser> {
        override fun onSuccess(result: SignInUser?) {}
        override fun onFailure(error: Exception) {}
        override fun onCancelled() {}
    }
)
```

Call `signOut` to sign out of the account.

```kt
accountService.signOut()
    .addOnSuccessListener {}
    .addOnFailureListener {}
    .addOnCanceledListener {}
```

</details>

## Auth

<details>

This library provides AuthService interface to handle Firebase Auth Service and AGC Auth Service with single code base.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:auth:<versionName>"
```

### Usage

First, initialize `AuthService`:

```kt
val authService = AuthService.Factory.create(context)
```

It needs `Context` to check mobile services availability and provide proper mobile service type.

Then get last signed user by calling `getUser()`. If there is no signed user it will return `null`

#### Facebook Sign in

Call `signInWithFacebook` to sign in with facebook account. It needs token which you get from facebook sdk. If it is success, it returns `AuthUser`.

```kt
authService.signInWithFacebook(token)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```

#### Huawei or Google Sign in

Call `signInWithGoogleOrHuawei` to sign in with google or huawei account. It needs token which you get from account sdk. If it is success, it returns `AuthUser`.

```kt
authService.signInWithGoogleOrHuawei(token)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```

#### Email Sign in

Call `signInWithEmail` to sign in with email. It needs email and password. If it is success, it returns `AuthUser`.

```kt
authService.signInWithEmail(email, password)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```

#### Phone Sign in

Call `signInWithPhone` to sign in with phone. It needs countryCode, phoneNumber, password and verifyCode. If it is success, it returns `AuthUser`.

**Firebase Auth Service** only uses the `verifyCode` parameter to login. You can get the `verifyCode` using the `getPhoneCode` method.
**AGC Auth Service uses** `countryCode`, `phoneNumber` and `password` parameters to login. In order to use the `signInWithPhone` method by the AGC Auth Service, the phone must be registered. You can use the `signUpWithPhone` method to register.

```kt
authService.signInWithPhone(countryCode,phoneNumber,password,verifyCode)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```

#### Twitter Sign in

Call `signInWithTwitter` to sign in with twitter. It needs token and secret. If it is success, it returns `AuthUser`.

```kt
authService.signInWithTwitter(token, secret)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```

#### Anonymous Sign in

Call `anonymousSignIn` to sign in with an anonymous account that generated by Auth Service Server. It doesn't need any information. If it is success, it returns `AuthUser`.

```kt
authService.anonymousSignIn()
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```

#### Email Sign up

Call `signUp` to sign up with email. It needs email and password. If it is success, it returns `VerificationType`.

```kt
authService.signUp(email, password)
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
```

If `VerificationType` is `CODE`, it means that an e-mail containing verification code has been sent to the user. Call `verifyCode` to save user with verification code.

```kt
authService.verifyCode(email, password, verificationCode)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

#### Phone Sign up

Call `signUpWithPhone` to sign up to **AGC Auth Service** with phone. It needs country code, phone number, password and verify code. You can get the verifyCode using the `getPhoneCode` method.

This method is only used for **AGC Auth Service**.
There is no need to register for **Firebase Auth Service.** You can login directly using the `signInWithPhone` method.

```kt
authService.signUp(countryCode, phoneNumber, password, verifyCode)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

If `VerificationType` is `CODE`, it means that an e-mail containing verification code has been sent to the user. Call `verifyCode` to save user with verification code.

```kt
authService.verifyCode(email, password, verificationCode)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

#### Reset password

Call `resetPassword` to reset user' s password. It needs email. If it is success, it returns `VerificationType`.

```kt
authService.resetPassword(email)
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
```

If verification type is `LINK`, it means that an e-mail containing link to reset password has been sent to the user. If `VerificationType` is `CODE`, it means that an e-mail containing verification code has been sent to the user. Call `verifyCodeToResetPassword` to reset user' s password.

```kt
authService.verifyCodeToResetPassword(email, newPassword, verificationCode)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

#### Update Username

Call `updateUsername` to update user' s username. It needs user login.

```kt
authService.updateUsername(email)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

#### Update Photo

Call `updatePhoto` to update user' s photo. It needs user login.

```kt
authService.updatePhoto(photo)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

#### Get Code

Call `getCode` to get verification code. It needs new email or new phone number.

```kt
authService.getCode(newEmail)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

```kt
authService.getCodePassword(newEmail)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

```kt
authService.getPhoneCode(country_code,phone,activity) //country_code ex: like Turkey: +90 then phone: 532xxxxxx
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

#### Update Email

Call `updateEmail` to update user's mail. The e-mail address to be changed is needed. You can get the verifyCode using the `getCode` method.

```kt
authService.updateEmail(newEmail, verificationCode)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

**_Note: Only a user who has signed in within 5 minutes can change their email address. If such a requirement is not met, reauthenticate the user and then try again._**

#### Update Phone

Call `updatePhone` to update user's phone. It needs phone. You can get the `verifyCode` using the `getPhoneCode` method.

```kt
authService.updatePhone(country_code, phone, verificationCode) //country_code ex: like Turkey: +90 then phone: 532xxxxxx
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

#### Update Password

Call `updatePasswordWithPhone` to update user's password with phone. You can get the `verifyCode` using the `getPhoneCode` method.
Call `updatePasswordWithEmail` to update user's password with email. You can get the `verifyCode` using the `getCode` method.

```kt
authService.updatePasswordWithPhone(password, verificationCode)
    .addOnSuccessListener {}
    .addOnFailureListener {}

authService.updatePasswordWithEmail(password, verificationCode)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

#### Link With Twitter

Call `linkWithTwitter` to link account with twitter. It needs `token` and `secret`. If it is successful, it returns `AuthUser`.

```kt
authService.linkWithTwitter(token, secret)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```

#### Link With Facebook

Call `linkWithFacebook` to link account with facebook. It needs `token`. If it is successful, it returns `AuthUser`.

```kt
authService.linkWithFacebook(token)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```

#### Link With Email

Call `linkWithEmail` to link account with email. It needs `email`, `password` and `verifyCode`. If it is successful, it returns `AuthUser`.

```kt
authService.linkWithEmail(email,password,verifyCode)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```

#### Link With Phone

Call `linkWithPhone` to link account with phone. It needs `countryCode`, `phoneNumber`, `password` and verifyCode. If it is successful, it returns `AuthUser`.

```kt
authService.linkWithPhone(countryCode,phoneNumber,password,verifyCode)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```

#### Unlink

Call `unlink` to unlink account from the linked account. It needs `provider`. If it is successful, it returns `AuthUser`.

```kt
authService.unlink(provider)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```

#### ReAuthenticate

Call `reAuthenticate` to re-authenticate users. It needs `email` and `password`.

```kt
authService.reAuthenticate(email,password)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

#### Delete User

Call `deleteUser` to delete users. It needs `email` and `password`.

```kt
authService.deleteUser()
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

</details>

## Safety

<details>

This library includes both Huawei and Google services, allowing you to make your application more secure. This library, it performs necessary checks about whether users are fake users and device security. [Click here](https://developer.huawei.com/consumer/en/hms/huawei-safetydetectkit/) for view service introduction and more description about Safety Detect Kit features.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:safety:<versionName>"
```

### Usage

You need to initialize `SafetyService` interface as follows;

#### User Detection

To check if the user is a fake user, you need to call the `userDetect` method; `AppKey` is same as **AppId** value in Huawei services. In Google services the `appKey` value is same as `SITE_API_KEY`. You can create `SITE_API_KEY` value from **reCAPTCHA** Admin Console. [Click here](https://www.google.com/recaptcha/about/)

```kt
 private var safetyService : SafetyService ?= null
 override fun onCreate(savedInstanceState: Bundle?) {
    safetyService  = SafetyService.Factory.create(applicationContext)
    safetyService?.userDetect(appKey, object: ResultCallback<SafetyServiceResponse>{
            override fun onSuccess(result: SafetyServiceResponse?) {
                  if(result!= null){
                     Log.d("CMS", result.responseToken)
                  }
                }
                override fun onFailure(error: Exception) {
                     Log.e("CMS", error.toString())
                }
                override fun onCancelled() {
                    TODO("Not yet implemented")
                }})
```

#### Root Detection

In this library, you need to call the `rootDetection` method to check whether the device is safe or not. `AppKey` value is **AppId** value in Huawei services. In Google services the `appKey` value is `API_KEY`. You can create `API_KEY` value from Google APIs Console. [Click here](https://console.developers.google.com/apis/library)

```kt
 override fun onCreate(savedInstanceState: Bundle?){
    safetyService  = SafetyService.Factory.create(applicationContext)
    safetyService?.rootDetection(appKey, object: ResultCallback<RootDetectionResponse> {
                override fun onSuccess(result: RootDetectionResponse?) {
                     if(result!=null){
                        if(result.basicIntegrity){
                           Log.d("CMS", result.toString())
                     }
                    }else{
                        Log.d( "CMS","You need to install Google or Huawei Mobile Services to run application.")
                    }
                }
                override fun onFailure(error: Exception) {
                    Log.e("CMS", error.toString())
                }
                override fun onCancelled() {
                    TODO("Not yet implemented")
                }})
```

#### AppChecks

With the AppChecks feature, you can easily and quickly detect harmful applications on your device.
You can check whether the AppChecks feature is active on your device with the `isAppChecksEnabled()` method.

```kt
fun isAppChecksEnabled(callback: ResultCallback<CommonVerifyAppChecksEnabledRes>)
```

```kt
safetyService.isAppChecksEnabled(object: ResultCallback<CommonVerifyAppChecksEnabledRes>{
            override fun onSuccess(appsCheckResp: CommonVerifyAppChecksEnabledRes?) {
                if(appsCheckResp!=null){
                    val result = appsCheckResp.result
                    if(result){
                        Log.d("CMS", "App Checks is enabled")
                    }else{
                        Log.d("CMS", "App Checks is disabled")
                    }
                }
            }
            override fun onFailure(e: Exception) {
                Log.e("CMS", "App Checks Fail: ${e.message}")
            }
        })
```

If the App Checks feature is disabled, you can enable it with the `enableAppsCheck()` method.

```kt
fun enableAppsCheck(callback: ResultCallback<CommonVerifyAppChecksEnabledRes>)
```

```kt
 safetyService.enableAppsCheck(object: ResultCallback<CommonVerifyAppChecksEnabledRes>{
      override fun onSuccess(appsCheckResp: CommonVerifyAppChecksEnabledRes?) {
          if(appsCheckResp!=null){
              val result = appsCheckResp.result
              if(result){
                  Log.d("CMS", "App Checks enabled")
              }else{
                  Log.d("CMS", "App Checks not enabled")
              }
          }
      }
      override fun onFailure(e: Exception) {
          Log.e("CMS", "App Checks Fail: ${e.message}")
      }
  })
```

You can detect malicious applications on your device with the `getMaliciousAppsList()` method.

```kt
fun getMaliciousAppsList(callback: ResultCallback<CommonMaliciousAppResponse>)
```

```kt
safetyService.getMaliciousAppsList(object: ResultCallback<CommonMaliciousAppResponse>{
    override fun onSuccess(maliciousAppResponse: CommonMaliciousAppResponse?){
     if (maliciousAppResponse != null) {
        val appList = maliciousAppResponse.getMaliciousAppsList
        if (appList?.isNotEmpty() == true){
        Log.e("CMS", "Potentially harmful apps are installed!")
        for (harmfulApp in appList){
            Log.e("CMS", "Information about a harmful app:")
            Log.e("CMS", "  APK: ${harmfulApp.apkPackageName}")
            Log.e("CMS", "  SHA-256: ${harmfulApp.apkSha256}")
            Log.e("CMS", "  Category: ${harmfulApp.apkCategory}")
         }
        }else{
          Log.d("CMS", "There are no known harmful apps installed.")
        }
      }
    }
    override fun onFailure(e: Exception){
          Log.e("CMS", "Error code: ${e.localizedMessage} -- Message: ${e.message}")
    }
})
```

#### URLCheck

You can check whether URL addresses are safe with the URLCheck feature.

To activate the URLCheck feature, you must first call the `initURLCheck()` method.

```kt
fun initURLCheck():Work<Unit>
```

```kt
safetyService.initURLCheck().addOnSuccessListener{
    Log.d("CMS", "Url checks activated")
}.addOnFailureListener{
    Log.e("CMS", "Url check fail: ${it.message}")
}
```

You can check whether the URL you will specify is safe with the `urlCheck()` method.

```kt
fun urlCheck(url:String,appKey: String,threatType:Int,callback:ResultCallback<CommonUrlCheckRes>)
```

Appkey value is app id value in Huawei services. In Google services the app key value is API_KEY. You can create API_KEY value from Google APIs Console. [Click here](https://console.developers.google.com/apis/library) You need to activate the SafeBrowsing API feature. You can create new API_KEY from the Credentials tab.

```kt
val url = "https://github.com/Explore-In-HMS/common-mobile-services"
safetyService.urlCheck(url,appKey,CommonUrlCheckThreat().urlThreatType(this,CommonUrlCheckThreat.MALWARE_APPLICATIONS),object:ResultCallback<CommonUrlCheckRes>{
    override fun onSuccess(appsCheckResp: CommonUrlCheckRes?) {
        if(appsCheckResp!=null){
            val result = appsCheckResp.urlCheckThreats
            if(result!!.isNotEmpty()){
            for(urlLists in result){
                Log.d("CMS", "URL Check result: ${urlLists.urlCheckResult}")
            }
            }else{
            Log.d("CMS", "No threads found")
            }
        }
        }
    override fun onFailure(e: Exception) {
        Log.e("CMS", "URLCheck fail : ${e.message}")
    }
})
```

You can use the `shutDownUrlCheck()` method to disable the URLCheck feature.

```kt
fun shutDownUrlCheck(): Work<Unit>
```

```kt
safetyService.shutDownUrlCheck().addOnSuccessListener{
    Log.d("CMS", "Url check is disabled")
}.addOnFailureListener {
    Log.e("CMS", "URLCheck fail: ${it.message}")
}
```

</details>

## Crash

<details>

This library includes both Huawei and Google services, allowing you to make your application more secure. This library, it performs Crash Service. [Click here](https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-Guides/agc-crash-introduction-0000001055732708) for view service introduction and more description about Crash Kit features.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:crash:<versionName>"
```

### Usage

1. declare Crashlytics plugin dependency in `Project` level `build.gradle`:

   ```gradle
   buildscript {
       //..
       dependencies {
           //...
           classpath 'com.google.firebase:firebase-crashlytics-gradle:2.5.2'
       }
   }
   ```

2. Add Crashlytics plugin to `App` level `build.gradle`:

   ```gradle
   apply plugin: 'com.google.firebase.crashlytics'
   ```

3. Create Crash service instance

   ```kt
       val crashService = CrashService.Factory.create(context)
   ```

It needs `Context` to check mobile services availability and provide proper mobile service type.

#### Available functions

you can use the `crashService` instance to call any of the available functions, example `crashService.testIt(context)`

```kt
    testIt(context: Context)

    log(var1: String)

    log(var1: Int, var2: String)

    setCustomKey(var1: String, var2: String)

    setCustomKey(var1: String, var2: Boolean)

    setCustomKey(var1: String, var2: Double)

    setCustomKey(var1: String, var2: Float)

    setCustomKey(var1: String, var2: Int)

    setCustomKey(var1: String, var2: Long)

    setCustomKeys(var1: CustomKeysAndValues)

    setUserId(var1: String)

    recordException(var1: Throwable)

    enableCrashCollection(enable: Boolean)
```

</details>

## Push

<details>

HMS Push Kit is messaging service. Push Kit helps you quickly and efficiently reach users. By integrating Push Kit, you can send messages to your apps on users' devices in real time. [Click here](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/service-introduction-0000001050040060) for view service introduction and more description about Push Kit features.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:push:<versionName>"
```

### Usage

Create services that extends Huawei and Google Messaging services

```kt
class HMSPushService : HmsMessageService() {

    val TAG = "HMS_PUSH"

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.i(TAG, "Token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)
        Log.i(TAG, "Remote Message: ${message?.data}")
    }
}
```

```kt
class GMSPushService : FirebaseMessagingService() {

    val TAG = "GMS_PUSH"

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.i(TAG, "Token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)
        Log.i(TAG, "Remote Message: ${message?.data}")
    }
}
```

Next, declare the services in `AndroidManifest.xml` file, and add meta-data lines to enable automatic initialization

```xml
<!-- HMS Push Service -->
<service android:name="com.hms.lib.commonmobileservices.push.basic.services.HMSPushService" android:exported="false">
    <intent-filter>
        <action android:name="com.huawei.push.action.MESSAGING_EVENT" />
    </intent-filter>
</service>
<meta-data
    android:name="push_kit_auto_init_enabled"
    android:value="true" />

<!-- Firebase Push Service -->
<service android:name="com.hms.lib.commonmobileservices.push.basic.services.GMSPushService" android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
<meta-data
    android:name="firebase_messaging_auto_init_enabled"
    android:value="true" />

<!-- Common Push Service -->
<receiver android:name="com.hms.lib.commonmobileservices.app.services.CommonPushService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.commonmobileservices.action.MESSAGING_EVENT" />
    </intent-filter>
</receiver>
```

#### Slider Push Notification

Common Mobile Services provide a slider push notification in Push service. If you want to use that feature you should send data-message like this

```json
{
  "validate_only": false,
  "message": {
    "data": "{'slider':'true','header':'This is a header','callToAction':'This is a sentence for call to action','items':[{'title':'Title of Slider 1','image':'https://yuklio.com/f/ph1d1-sample1.jpg','url':'https://url_of_slider_1.com'},{'title':'Title of Slider 2','image':'https://yuklio.com/f/aPmbR-sample2.jpg','url':'https://url_of_slider_2.com'},{'title':'Title of Slider 3','image':'https://yuklio.com/f/SKWB0-sample3.jpg','url':'https://url_of_slider_3.com'}]}",
    "token": ["DEVICE_TOKEN"]
  }
}
```

You can use `getToken` method to obtain push registration token.

```kt
        val token = HuaweiPushServiceImpl(this).getToken()
        token.addOnSuccessListener {
            println("token-> ${it.token}")
        }
```

```kt
        val token = GooglePushServiceImpl(this).getToken()
        token.addOnSuccessListener {
            println("token-> ${it.token}")
        }
```

`subscribeToTopic` method subscribes to topics in asynchronous mode. The topic messaging function provided by Push Kit allows you to send messages to multiple devices whose users have subscribed to a specific topic.
You can write messages about the topic as required, and Push Kit determines target devices and then sends messages to the devices in a reliable manner.
The name of the topic to subscribe. Must match the following regular expression: "[a-zA-Z0-9-_.~%]{1,900}".
You need to add a listener to listen to the operation result.

```kt
        HuaweiPushServiceImpl(this).subscribeToTopic("test")
            .addOnSuccessListener { result: Unit ->
                Log.i(TAG, "OK")
            }
            .addOnFailureListener { error: Exception ->
                error.printStackTrace()
            }
            .addOnCanceledListener {
                Log.i(TAG, "Cancelled")
            }
```

```kt
        GooglePushServiceImpl(this).subscribeToTopic("test")
            .addOnSuccessListener { result: Unit ->
                Log.i(TAG, "OK")
            }
            .addOnFailureListener { error: Exception ->
                error.printStackTrace()
            }
            .addOnCanceledListener {
                Log.i(TAG, "Cancelled")
            }
```

`unsubscribeFromTopic` method unsubscribes from topics in asynchronous mode that are subscribed to through the `subscribe` method.

You need to add a listener to listen to the operation result.

```kt
        HuaweiPushServiceImpl(this).unsubscribeFromTopic("test")
            .addOnSuccessListener { result: Unit ->
                Log.i(TAG, "OK")
            }
            .addOnFailureListener { error: Exception ->
                error.printStackTrace()
            }
            .addOnCanceledListener {
                Log.i(TAG, "Cancelled")
            }
```

```kt
        GooglePushServiceImpl(this).unsubscribeFromTopic("test")
            .addOnSuccessListener { result: Unit ->
                Log.i(TAG, "OK")
            }
            .addOnFailureListener { error: Exception ->
                error.printStackTrace()
            }
            .addOnCanceledListener {
                Log.i(TAG, "Cancelled")
            }
```

`setAutoInitEnabled` method lets you enable or disable Push service automatic initialization.
If you enable it, then the SDK automatically generates an **AAID** and obtains a token. The token is returned through the `onNewToken()` callback method.

```kt
HuaweiPushServiceImpl(this).autoInitEnabled(true)
```

```kt
GooglePushServiceImpl(this).autoInitEnabled(true)
```

`isAutoInitEnabled` method checks whether automatic initialization is enabled. The default value is false.

```kt
HuaweiPushServiceImpl(this).isAutoInitEnabled()
```

```kt
GooglePushServiceImpl(this).isAutoInitEnabled()
```

</details>

## Site Kit

<details>

This kit provides different functionalities such as nearby search, text search, place details and place autocomplete. You can use these functions for both Google Services using Places API and Huawei Services using Site Kit.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:site:<versionName>"
```

### Usage

First, create `SiteService` instance

```kt
val siteservice = SiteService.Factory.create(Context, huaweiApiKey, googleApiKey)
```

`Context` is required to check the available mobile services on the device, and `apiKey` is required to access the services of Site Kit or make Google Places API calls.

`getNearbyPlaces` is a function that takes parameters like `latitude`, `longitude`, `query` or `keyword` to search, `hwpoiType` which is the poi type you want to search (in case of using Huawei services you should be careful to enter the poi type matching the **HW Poi** Types from Site Kit, e.g. “RESTAURANT” instead of “restaurant”), `radius` of the area you want the results to be focused, `language` you want the results in, `pageIndex` and `pageSize` for pagination, `strictBounds` to determine whether we want the location bounds to be strict or not, and a `callback` that will receive the results.

```Kotlin
fun getNearbyPlaces(
        siteLat: Double,
        siteLng: Double,
        keyword: String?,
        hwpoiType: String?,
        radius: Int?,
        language: String?,
        pageIndex: Int?,
        pageSize: Int?,
        strictBounds: Boolean?,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    )
```

`getTextSearchPlaces` function uses almost the same parameters and performs search requests based on keyword.

```Kotlin
fun getTextSearchPlaces(
    query: String,
    siteLat: Double?,
    siteLng: Double?,
    hwpoiType: String?,
    radius: Int?,
    language: String?,
    pageIndex: Int?,
    pageSize: Int?,
    callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
)
```

`placeSuggestion` function returns a list of autocompleted places based on the keyword you have entered, the `location` you enter, the `radius` you want your results to be focused on and the `language` of them. `childrenNode` parameter is only used by the Huawei Service to return information on the children nodes.

```Kotlin
fun placeSuggestion(
    keyword: String,
    siteLat: Double?,
    siteLng: Double?,
    childrenNode: Boolean?,
    areaRadius: Int?,
    areaLanguage: String?,
    callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
)
```

`getDetailSearch` function is a function used to get the details of a place based on the place's ID.

```Kotlin
fun getDetailSearch(
    siteID: String,
    areaLanguage: String?,
    childrenNode: Boolean?,
    callback: (SiteToReturnResult: ResultData<SiteServiceReturn>) -> Unit
)
```

`childrenNode` is also only used in case of the Huawei Services.

The results are returned as a data class of `SiteServiceReturn`

```Kotlin
data class SiteServiceReturn (
    val id : String?,
    val name : String?,
    val locationLat : Double?,
    val locationLong : Double?,
    val phoneNumber : String?,
    val formatAddress : String?,
    val distance : Double?,
    val image : ArrayList<String>?,
    val averagePrice : Double?,
    val point : Double?,
)
```

You can reach the data from `SiteServiceReturn` as a result from the callback. Final example of how to call a function is given below.

```Kotlin
SiteService.Factory.create(Context, HuaweiApikey, GoogleApiKey).getNearbyPlaces(siteLat,siteLng,query,hwpoiType,radius,language,pageIndex,pageSize,strictBounds,
    {
        it.handleSuccess {
            it.data!![0].name //get name of first result of data
        }
    })
```

</details>

## Identity Kit

<details>

HUAWEI Identity Kit provides unified address management services for users, including adding, editing, deleting, and querying addresses, and enables the users to authorize apps to access their addresses through a single tap on the screen.

**_Note: Identity Kit only works on HMS devices. GMS Identity service is deprecated._**

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:identity:<versionName>"
```

### Usage

First, create `IdentityServices` instance

```kt
val identityService = IdentityService.Factory.create(context)
```

Then call `getUserAddress` function to obtain user's updated and detailed address information

```kt
identityService!!.getUserAddress()
```

User's address details will be received in `onActivityResult` method

```kt
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
        IdentityService.requestCode** -> when (resultCode) {
            Activity.RESULT_OK -> {
                val userAddress = UserAddressResponse().parseIntent(data)

                if (userAddress != null) {
                    // Do something with user address
                    println("$userAddress")
                } else {
                    println("userAddress is null")
                }

            }
            Activity.RESULT_CANCELED -> {
            }
            else -> {
            }
        }
        else -> {
        }
    }
}
```

</details>

## Remote Config

<details>

Remote config is a service that allows you to make theme changes or in-app changes according to the situation in your application without the need for any updates.

### Download

Add the dependency to your `app` level `build.gradle` if you haven't already

```gradle
implementation "com.github.Explore-In-HMS.common-mobile-services:remoteconfig:<versionName>"
```

### Usage

First, define a set of parameters and their default values in an xml resource file stored in `res/xml` folder

```xml
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">
    <remoteconfig>
        <value key="testValue">testSituations</value>
    </remoteconfig>
</PreferenceScreen>
```

Next, create `RemoteConfigService` instance

```kt
val remoteConfig = IRemoteConfigService.Factory.create(context)
```

then set the default key-value by calling `setDefaultXml` and pass the xml file created in pervious step

```kt
remoteConfig.setDefaultXml(R.xml.remote_config)
```

To fetch the latest values from the server, call the method `fetchAndApply`

```kt
remoteConfig.fetchAndApply({
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
},0)  // Toast message is to show the callback to the user and 0 is fetch interval time in seconds.
```

After fetching and applying the changes, retrieve the values using one the getter methods: `getString(keyValue)`, `getBoolean(keyValue)`, `getLong(keyValue)` and `getDouble(keyValue)`

```kt
var valueFromConsole = remoteConfig.getString("testValue")
```

</details>

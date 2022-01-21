# Common Mobile Services

It is a library that provides a common interface for mobile services for Android developers. Its aim is removing special mobile service dependencies for your app code.
This has mainly two benefits:
1- It removes creation and lifecycle control code from your app code. So your classes get rid of one extra responsibility.(Separation of Concerns)
2- Makes it possible to use different mobile services. For example not all Android devices has Google Mobile Services(GMS). By doing this library you can use
different services without modifying your app code.

This library contains 2 services for now: Google Mobile Services(GMS) and Huawei Mobile Services(HMS). This library will grow with the added services.
If you want to contribute don't hesitate to create PR's :)

Currently added services: `MapKit`, `Location`, `Analytics`, `CreditCardScanner`, `Awareness`, `Scan`, `Translate`, `Speech To Text`, `Account`, `Auth`, `Safety`, `Site`, `Crash`, `Push`, `Scene` and `Identity`.

## How to install

### Step 1. Add the JitPack repository, Huawei repo and classpaths to your build file 
```gradle
buildscript {
    repositories {
    	...
        maven {url 'https://developer.huawei.com/repo/'}
    }
    dependencies {
    	...
        classpath 'com.huawei.agconnect:agcp:1.3.2.301'
        classpath 'com.google.gms:google-services:4.3.4'
    }
}

allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
	maven { url "https://developer.huawei.com/repo/" }
    }
}
```
### Step 2. Get the agconnect-services.json file from the AGC Console and google-services.json file from Firebase Console. Then, place it under the app module. And, add plugins to app level gradle file header.
```gradle
apply plugin: 'com.huawei.agconnect'
apply plugin: 'com.google.gms.google-services'
```
### Step 3. Add the dependency for module(s):
com.github.Explore-In-HMS.common-mobile-services

`latest version 2.1`
### MapKit
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:mapkit:<versionName>'
```
### Location
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:location:<versionName>'
```
### Analytics
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:analytics:<versionName>'
```
### Credit Card Scanner
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:creditcardscanner:<versionName>'
```
### Awareness
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:awareness:<versionName>'
```
### Scan
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:scan:<versionName>'
```
### Translate
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:translate:<versionName>'
```
### Speech To Text 
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:speechtotext:<versionName>'
```
### Account
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:account:<versionName>'
```
### Auth
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:auth:<versionName>'
```
### Scene
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:scene:<versionName>'
```
### Safety
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:safety:<versionName>'
```
### Crash
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:crash:<versionName>'
```
### Push
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:push:<versionName>'
```
### Site
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:site:<versionName>'
```
### Identity
```gradle
implementation 'com.github.Explore-In-HMS.common-mobile-services:identity:<versionName>'
```

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

This library wraps a mapview to use it in application code. It has [CommonMap](https://github.com/Huawei/CommonMobileServices/blob/master/mapkit/src/main/java/com/hms/lib/commonmobileservices/mapkit/factory/CommonMap.kt) interface which can be [GoogleCommonMapImpl](https://github.com/Huawei/CommonMobileServices/blob/master/mapkit/src/main/java/com/hms/lib/commonmobileservices/mapkit/factory/GoogleCommonMapImpl.kt) or [HMSCommonMapImpl](https://github.com/Huawei/CommonMobileServices/blob/master/mapkit/src/main/java/com/hms/lib/commonmobileservices/mapkit/factory/HuaweiCommonMapImpl.kt). A custom view created to hold these map views: [CommonMapView](https://github.com/Huawei/CommonMobileServices/blob/master/mapkit/src/main/java/com/hms/lib/commonmobileservices/mapkit/CommonMapView.kt). This view also manages lifecycle events of its map.

### How to use

First add Google Map api key to Manifest.xml.
```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="xxx-xxx"/>
```

Add CommonMapView to your layout file
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

## Location

This library provides a [CommonLocationClient](https://github.com/Huawei/CommonMobileServices/blob/master/location/src/main/java/com/hms/lib/commonmobileservices/location/CommonLocationClient.kt). It is a base class for GMS `FusedLocationProviderClient` and HMS `FusedLocationProviderClient`. This library handles enabling GPS and getting location permissions at runtime.


### How to use

First, initialize `CommonLocationClient`:
```kt
val locationClient: CommonLocationClient = LocationFactory.getLocationClient(this,lifecycle).apply {
    handlePermanentlyDeniedBlock ={
        // redirect user to settings
        showToast("Open settings and give the location permission")
    }
    permissionsDeniedBlock ={
        // tell user that why this permission is required
        showToast("You must give the location permission!!")
    }
}
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
commonLocationClient?.getLastKnownLocation{
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
 Getting location continuously:
 ```kt
 commonLocationClient?.requestLocationUpdates(priority = Priority.PRIORITY_HIGH_ACCURACY,interval = 1000){
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
commonLocationClient?.removeLocationUpdates()
 ```
  ##### Using the Mock location feature
   To use the mock location function, go to Settings > System & updates > Developer options > Select mock location app and select the desired app. (If Developer options is   unavailable, go to Settings > About phone and tap Build number for seven consecutive times. Then, Developer options will be displayed on System & updates.)
   You need to add necessary permission to use Mock location feature to AndroidManifest.xml file.
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

  ### Geofence
   The usage of the Geofence feature is as follows.
  To use the geofence service APIs of Location Kit, declare the ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION permissions in the AndroidManifest.xml file.
  ```xml
   <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
   <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
  ```

 First you have to initialize the CommonGeofenceService object.
  ```kt
     private var geofenceService: CommonGeofenceService? = null
     private var geofenceList: ArrayList<Geofence>? = null
     private var pendingIntent: PendingIntent?=null

     geofenceService = CommonGeofenceService()
     pendingIntent = getPendingIntent()
     geofenceList = ArrayList()
  ```
  #### Creating and Adding a Geofence

  ```kt
  geofenceList!!.add(Geofence()
    .also { it.uniqueId= "testGeofence" }.also { it.conversions = Geofence.ENTER_GEOFENCE_CONVERSION }
    .also { it.validDuration = Geofence.GEOFENCE_NEVER_EXPIRE }.also { it.latitude = latitude }.also { it.longitude = longitude }
    .also { it.radius = 20F })
  ```

  #### Create a request for adding a geofence

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
  After adding Geofence, we need to create a Broadcast receiver and define it in AndroidManifest file so that Geofence can be triggered.

   ```xml
    <receiver android:name=".GeofenceBroadcastReceiver"
       android:exported="true">
         <intent-filter>
 	   <action android:name="com.hms.commonmobileservices.GEOFENCE" />
         </intent-filter>
    </receiver>
   ```
 We will be able to listen to the Geofence triggering process thanks to the Brodcast receiver.Thanks to our CommonGeofenceData class, you can get all information about the triggered event.

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
  #### Remove a geofence

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

  ### Activity Recognition
  Thanks to the Activity Recognition feature, you can follow the user's activity instantly.

   To use the activity recognition service in versions earlier than Android 10, add the following permission in the AndroidManifest.xml file.
   ```xml
   <uses-permission android:name="com.huawei.hms.permission.ACTIVITY_RECOGNITION"/>
   <uses-permission android:name="com.google.android.gms.permission.ACTIVITY_RECOGNITION"/>
   ```
   To use the activity recognition service in Android 10 and later versions, add the following permission in the AndroidManifest.xml file;
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
    With Common `fetchDataFromIntent()` method you can get  detected activities status list.
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

    #### Activity Transition

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
    #### Stop activity transition update request
    If you want to stop getting activity transition information, you can use `deleteActivityConversionUpdates()` method.
    ```kt
     fun deleteActivityConversionUpdates(context: Context,pendingIntent: PendingIntent):Work<Unit>
    ```

    ```kt
    activityIdentificationService.deleteActivityConversionUpdates(applicationContext,pendingIntent)
          .addOnSuccessListener { }
          .addOnFailureListener { }
    ```
## Analytics
It is made to ease logging for your project. You can log your event with a one line of code. 
### How to use

First, get the instance of common logger by calling `CommonAnalytics.instance(this)`. The function takes a context instance as a parameter. Then returns an implementation of `CommonAnalytics`. It can be the class that uses `Firebase` or `HiAnalyticsTools` for logging. 
If you want to implement your own logger you can just implement the CommonAnalytics interface and use it instead. 

Then you can start logging events by using several methods. For logging single value you can call `saveSingleData` funcition of `CommonAnalytics` interface. You can log values type of `String`, `Int`, `Long`, `Double` and `Float`.
 ```kt
CommonAnalytics.instance(this)?.saveSingleData("cartEvent", "productId", 112233)
 ```
 First, event name is required. Then you must enter a key and value sequentially.

 You can also log more then one key-value pair for a single event by using `saveData` function.
```kt
CommonAnalytics.instance(this)?.saveData(
    "cartEvent",
    "productId", 112233,
    "productName", "socks",
    "quantity", 5,
    "discountPercentage", 23.3
)
 ```
Or you can log your events with classic Bundle.
```kt
val myEvent = Bundle().apply {
    putString("productName", "socks")
    putInt("quantity", 5)
}
CommonAnalytics.instance(this)?.saveEvent("cartEvent", myEvent)
```
## Credit Card Scanner
This library reads a credit card with device camera. It uses Huawei ML-Card-Bcr library to scan image. But it is inherited from `CreditCardScanner` common interface, so you can use your own implementation of credit card reader. HMS library does not require a Huawei device or HMS Core, so this library works well in all devices.

### How to use

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
## Awareness
Awareness SDK provides your app with the ability to obtain contextual information including users' behavior, audio device status, weather, and current time. Depending on the situation, this information can be obtained in certain instantly.

### How to use
To use Google Awareness Api Key, you need to get Google Awareness Api Key. With this Api Key, add the code block to your Manifest.xml file as follows.

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
## Scan 
## Scan 
Scan SDK scans and parses all major 1D and 2D barcodes, helping you quickly barcode scanning functions into your apps.

### How to use
The scan process is started with the following line of code. scanBarcodeResultCode is the variable that is the result of OnActivityResult.
```kt
HuaweiGoogleScanManager(this).performScan(this,scanBarcodeResultCode)
```

After scanning is done, The result of the scanning process gets from onActivityResult. Using the parseScanToTextData function, the data is converted to string format.
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

## Translate 
It provides a very simple use of the translate feature. Currently 39 different languages ​​are supported. [Click here](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides-V5/ml-resource-0000001050038188-V5)  to view instantly supported languages.

### How to use
Parameters that should be used to use the translate feature; context, text to be translated, huawei api key and language code to be translated to whatever language is used.
```kt
HuaweiGoogleTranslateManager(this).performTranslate({
            it.handleSuccess {
                Log.d("Translate result: ", it.data.toString())
            }
        },"How are you today?","fr",this,huaweiAPIKey)
```

The `performTranslate()` function takes a callback lambda function as a parameter. The lambda function gives us a `ResultData` sealed class object.
```kt
fun performTranslate(callback: (translateValue: ResultData<String>) -> Unit, translatingText:String, targetLanguageCode:String, activity: Activity, apiKey:String)
```
## Speech To Text  
Speech to Text can recognize speech not longer than 60s and convert the input speech into text in real time. This service uses industry-leading deep learning technologies to achieve a recognition accuracy of over 95%. Currently, Mandarin Chinese (including Chinese-English bilingual speech), English, French, German, Spanish, and Italian can be recognized. [Click here](https://developer.huawei.com/consumer/en/doc/development/HMS-Plugin-References-V1/language-0000001058780607-V1)  to view instantly supported speech to text languages and language codes.

### How to use
The speech to text process is started with the following line of code. speechToTextResultCode is the variable that is the result of OnActivityResult. The language to be spoken in must be chosen.
```kt
HuaweiGoogleSpeechToTextManager(this).performSpeechToText(this,speechToTextResultCode,"en-US")
```

After speaking is done, The result of the speech to text process gets from onActivityResult. Using the parseSpeechToTextData function, the data is converted to string format.
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

## Scene
This library wraps Scene Kit views to use it in your application easily. It has IArView, IAugmentedFaceView and ISceneView interfaces which can be GoogleArView or HuaweiArView etc. It is related to your used service. Custom views created to hold these views: CommonSceneView, CommonAugmentedFaceView, CommonArView. These views also manages lifecycle events of its child views.

---
> **_Note:_**  *In this version we implemented only Huawei views. But you can implement your own view according to your service.*
---

### How to use
First you need to add ".gltf" type model under the assets folder. Then, you need to add these common views to your layout file according to your usecase. After that you need to pass the params to the init method of this common view. For example, for CommonAugmentedFaceView, you need "FaceViewParams" object to pass your `init()` method of your view. And you can hide or show the render object by using the `load()`, `clear()` functions of common view.

```kt
buttonLoad.setOnClickListener {
    (renderView as CommonView).load()
}
```
Also you can use all views in one layout file according to your usecase.

```kt
when (viewType) {
    ViewType.AR_VIEW -> {
        tempRenderView = CommonArView(requireContext())
        tempRenderView.init(commonData = renderParams.params)
        makeToastMessage("Move the camera around yourself until the plane dots appear")
    }
    ViewType.FACE_VIEW -> {
        tempRenderView = CommonAugmentedFaceView(requireContext())
        tempRenderView.init(commonData = renderParams.params)
    }
    ViewType.SCENE_VIEW -> {
        tempRenderView = CommonSceneView(requireContext())
        tempRenderView.init(commonData = renderParams.params)
    }
}
```
If you want to listen OpenGL functions of these views, you can set the callback for these views.
```kt
commonArView.onArViewCallback(object : ArViewCallback, () -> Unit {
    override fun onSurfaceChanged(gL10: GL10?, width: Int, height: Int) {}

    override fun onDrawFrame(gL10: GL10?) {}

        .
        .
        .
})
```

## Safety
This library includes both Huawei and Google services, allowing you to make your application more secure. This library, it performs necessary checks about whether users are fake users and device security. [Click here](https://developer.huawei.com/consumer/en/hms/huawei-safetydetectkit/) for view service introduction and more description about Safety Detect Kit features.

### How To Use
You need to initialize `SafetyService` interface as follows;

#### User Detection
To check if the user is a fake user, you need to call the `userDetect` method; Appkey value is app id value in Huawei services. In Google services the app key value is SITE_API_KEY. You can create SITE_API_KEY value from reCAPTCHA Admin Console. [Click here](https://www.google.com/recaptcha/about/)

```kt
 private var safetyService : SafetyService ?= null
 override fun onCreate(savedInstanceState: Bundle?) {
    safetyService  = SafetyService.Factory.create(applicationContext)
    safetyService?.userDetect(appKey, object : SafetyService.SafetyServiceCallback<SafetyServiceResponse> {
            override fun onFailUserDetect(e: Exception) {

            }
            override fun onSuccessUserDetect(result: SafetyServiceResponse?) {

            }
        })}
```

#### Root Detection
In this library, you need to call the `rootDetection` method to check whether the device is safe or not. Appkey value is app id value in Huawei services. In Google services the app key value is API_KEY. You can create API_KEY value from Google APIs Console. [Click here](https://console.developers.google.com/apis/library)

```kt
 override fun onCreate(savedInstanceState: Bundle?){
    safetyService  = SafetyService.Factory.create(applicationContext)
    safetyService?.rootDetection(appKey, object : SafetyService.SafetyRootDetectionCallback<RootDetectionResponse> {
            override fun onFailRootDetect(e: Exception) {
                Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_SHORT).show()
            }

            override fun onSuccessRootDetect(result: RootDetectionResponse?) {
                if(result!= null){
                    if(result.basicIntegrity){
                        Log.i("rootDetectionResult",result.basicIntegrity.toString())
                    }
                    else{
                        Toast.makeText(applicationContext,"You need to install Google or Huawei Mobile Services to run application.",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })}
```

## Crash
This library includes both Huawei and Google services, allowing you to make your application more secure. This library, it performs Crash Service. [Click here](https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-Guides/agc-crash-introduction-0000001055732708) for view service introduction and more description about Crash Kit features.
### How To Use
First of all, you have to add root gradle 'classpath 'com.google.firebase:firebase-crashlytics-gradle:2.5.2'' & app gradle 'apply plugin: 'com.google.firebase.crashlytics'';

```kt
val crashService = CrashService.Factory.create(context)
```
It needs `Context` to check mobile services availability and provide proper mobile service type.

### Functions
```kt
    //You can reach just write with "crashService." all of functions:

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


## Push
HMS Push Kit is messaging service. Push Kit helps you quickly and efficiently reach users. By integrating Push Kit, you can send messages to your apps on users' devices in real time. [Click here](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/service-introduction-0000001050040060) for view service introduction and more description about Push Kit features.

### How To Use
First of all, you have to create Services extends by HMS and GMS Messaging;

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

After that you have to add your services into AndroidManifest.xml file and don't forget to add meta-data lines for auto init enabled;
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

Common Mobile Services provide a slider push notification in Push service. If you want to use that feature you should send data-message like this;

![Slider Push Notification - Sample](https://git.huawei.com/hms---turkey-dtse-branch/team-2/CommonMobileServices/uploads/d4649d6cc7d70901829c9bc32dc116a7/spn_gif.gif)

```json
{
    "validate_only": false,
    "message":
    {
        "data": "{'slider':'true','header':'This is a header','callToAction':'This is a sentence for call to action','items':[{'title':'Title of Slider 1','image':'https://yuklio.com/f/ph1d1-sample1.jpg','url':'https://url_of_slider_1.com'},{'title':'Title of Slider 2','image':'https://yuklio.com/f/aPmbR-sample2.jpg','url':'https://url_of_slider_2.com'},{'title':'Title of Slider 3','image':'https://yuklio.com/f/SKWB0-sample3.jpg','url':'https://url_of_slider_3.com'}]}",
        "token": ["DEVICE_TOKEN"]
    }
}
```

## Account
This library provides AccountService interface to handle Google Account Service and Huawei Account Kit with single code base.

### How to use

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
Call `getSignInIntent` to start the `Activity` of he relevant service for the use login.
```kt
accountService.getSignInIntent { intent ->
    startActivityForResult(intent, REQUEST_CODE)
}
```
Then get result from signInIntent by calling `onSignInActivityResult`. Call this function in the `onActivityResult`.
```kt
accountService.onSignInActivityResult(intent, 
    object ResultCallback<SignInUser> {
        override fun onSuccess(result: SignInUser?) {}
        override fun onFailure(error: Exception) {}
        override fun onCancelled() {}
    }
)
```
## Auth
This library provides AuthService interface to handle Firebase Auth Service and AGC Auth Service with single code base.

### How to use

First, initialize `AuthService`:
```kt
val authService = AuthService.Factory.create(context)
```
It needs `Context` to check mobile services availability and provide proper mobile service type.

Then get last signed user by calling `getUser()`. If there is no signed user it will return `null`

### Facebook Sign in
Call `signInWithFacebook` to sign in with facebook account. It needs token which you get from facebook sdk. If it is success, it returns `AuthUser`.
```kt
authService.signInWithFacebook(token)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```
### Huawei or Google Sign in
Call `signInWithGoogleOrHuawei` to sign in with google or huawei account. It needs token which you get from account sdk. If it is success, it returns `AuthUser`.
```kt
authService.signInWithGoogleOrHuawei(token)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```
### Email Sign in
Call `signInWithEmail` to sign in with email. It needs email and password. If it is success, it returns `AuthUser`.
```kt
authService.signInWithEmail(email, password)
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
```
### Anonymous Sign in
Call `anonymousSignIn` to sign in with an anonymous account that generated by Auth Service Server. It doesn't need any information. If it is success, it returns `AuthUser`.
```kt
authService.anonymousSignIn()
    .addOnSuccessListener {authUser -> }
    .addOnFailureListener {}
``` 
### Email Sign up
Call `signUp` to sign up with email. It needs email and password. If it is success, it returns `VerificationType`.
```kt
authService.signUp(email, password)
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
``` 

If `VerificationType` is `CODE`, it means that an e-mail containing verificaton code has been sent to the user. Call `verifyCode` to save user with verification code. 
```kt
authService.verifyCode(email, password, verificationCode)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```
### Reset password
Call `resetPassword` to reset user' s password. It needs email. If it is success, it returns `VerificationType`.
```kt
authService.resetPassword(email)
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
``` 

If verification type is `LINK`, it means that an e-mail containing link to reset password has been sent to the user. If `VerificationType` is `CODE`, it means that an e-mail containing verificaton code has been sent to the user. Call `verifyCodeToResetPassword` to reset user' s password. 
```kt
authService.verifyCodeToResetPassword(email, newPassword, verificationCode)
    .addOnSuccessListener {}
    .addOnFailureListener {}
```

### Update Username
Call `updateUsername` to update user' s username. It needs user login. If it is success, it returns `VerificationType`.
```kt
authService.updateUsername(email)
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
```

### Update Photo
Call `updatePhoto` to update user' s photo. It needs user login. If it is success, it returns `VerificationType`.
```kt
authService.updatePhoto(photo)
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
```

### Get Code
Call `getCode` to get verification code. It needs email or phone number. If it is success, it returns `VerificationType`.
```kt
authService.getCode(email)
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
```

```kt
authService.getCodePassword(email)
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
```

```kt
authService.getPhoneCode(country_code,phone) //country_code ex: like Turkey: +90 then phone: 532xxxxxx
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
```

### Update Email
Call `updateEmail` to update user's mail. It needs email. If it is success, it returns `VerificationType`.
```kt
authService.updateEmail(email, verificationCode)
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
```

### Update Phone
Call `updatePhone` to update user's phone. It needs phone. If it is success, it returns `VerificationType`.
```kt
authService.updateEmail(country_code, phone, verificationCode) //country_code ex: like Turkey: +90 then phone: 532xxxxxx
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
```
### Update Password
Call `updatePassword` to update user's password. It needs password. If it is success, it returns `VerificationType`.
```kt
authService.updateEmail(password, verificationCode)
    .addOnSuccessListener {verificationType -> }
    .addOnFailureListener {}
```

## Site Kit

This kit provides different functionalities such as nearby search, text search, place details and place autocomplete. You can use these functions for both Google Services using Places API and Huawei Services using Site Kit.

### How to use

At first we initialize `SiteService` interface:

```
val siteservice = SiteService.Factory.create( Context, apikey)
```

`Context` serves to check the mobile services availability and decide what service to run and the `apikey` serves to use the services of Site Kit or make the Places API calls.

`getNearbyPlaces` is a function that takes parameters like latitude, longitude, query or the keyword to search, hwpoiType which is the poi type you want to search (in case of using Huawei services you should be careful to enter the poi type matching the HW Poi Types from Site Kit eg. “RESTAURANT” instead of “restaurant”), radius of the area you want the results to be focused, language you want the results in, page index and page size, strict bounds to determine whether we want the location bounds to be strict or not.

``` Kotlin
fun getNearbyPlaces(siteLat: Double?,
                            siteLng: Double?,
                            query: String?,
                            hwpoiType: String?,
                            radius: Int?,
                            language: String?,
                            pageIndex: Int?,
                            pageSize: Int?,
                            strictBounds: Boolean?,
                    callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit)
 ```
`getTextSearchPlaces` function uses almost the same parameters and performs search requests based on keyword.

```Kotlin
fun getTextSearchPlaces(siteLat: Double?,
                                    siteLng: Double?,
                                    query: String?,
                                    hwpoiType: String?,
                                    radius: Int?,
                                    language: String?,
                                    pageIndex: Int?,
                                    pageSize: Int?,
                            callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit)
```

`placeSuggestion` function returns a list of autocompleted places based on the keyword you have entered, the location you enter, the radius you want your results to be focused on and the language of them. `childrenNode` parameter is only used by the Huawei Service to return information on the children nodes.

```Kotlin
 fun placeSuggestion(siteLat: Double?,
                                siteLng: Double?,
                                keyword: String?,
                                childrenNode: Boolean?,
                                areaRadius: Int?,
                                areaLanguage: String?,
                        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit)
```
`getDetailSearch` function is a function used to get the details of a place based on the place's ID.

```Kotlin
fun getDetailSearch(siteID: String,
                                areaLanguage: String,
                                childrenNode: Boolean,
                        callback: (SiteToReturnResult: ResultData<SiteServiceReturn>) -> Unit)
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
SiteService.Factory.create( context, ApiKey).getNearbyPlaces(siteLat,siteLng,query,hwpoiType,radius,language,pageIndex,pageSize,strictBounds,
    {
        it.handleSuccess {
            it.data!![0].name //get name of first result of data
        }
    })
```


## Identity Kit

HUAWEI Identity Kit provides unified address management services for users, including adding, editing, deleting, and querying addresses, and enables the users to authorize apps to access their addresses through a single tap on the screen.

Note: Identity Kit just working on HMS devices. GMS Identity service was deprecated.


### How to use

At first we initialize `IdentityServices` interface as like this:

```
val identityService = IdentityService.Factory.create(context)
```


Than we can call `getUserAddress` function for obtain user's updated and detailed address information:

```
identityService!!.getUserAddress()
```

For catch the address details we have to listen `onActivityResult` override function with our response code:

```
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

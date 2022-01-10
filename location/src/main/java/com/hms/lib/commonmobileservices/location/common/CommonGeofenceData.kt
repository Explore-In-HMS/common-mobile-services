package com.hms.lib.commonmobileservices.location.common

import android.content.Intent

interface CommonGeofenceData {
      fun fetchDataFromIntent(intent : Intent): GeofencingData
}
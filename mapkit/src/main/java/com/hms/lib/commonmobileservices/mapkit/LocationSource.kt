package com.hms.lib.commonmobileservices.mapkit

import android.location.Location

interface LocationSource {
    fun activate(listener: OnLocationChangedListener)
    fun deactivate()

    interface OnLocationChangedListener{
        fun onLocationChanged(location: Location)
    }
}
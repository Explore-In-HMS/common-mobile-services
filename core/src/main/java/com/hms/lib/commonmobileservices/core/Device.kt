// Copyright 2020. Explore in HMS. All rights reserved.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

// http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.hms.lib.commonmobileservices.core

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.api.HuaweiApiAvailability
import java.lang.reflect.AccessibleObject
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

private const val TAG = "MobileService"

enum class MobileServiceType {
    HMS,
    GMS,
    NON
}

data class EmuiVersion(
    val name: String,
    val code: Int
)

object Device {

    /**
     * Mobile services availability of devices
     *
     * @return Device mobile service type enum
     */
    fun getMobileServiceType(
        context: Context,
        firstPriority: MobileServiceType? = null
    ): MobileServiceType {
        val gms: Boolean = GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(context) == com.google.android.gms.common.ConnectionResult.SUCCESS
        val hms: Boolean = HuaweiApiAvailability.getInstance()
            .isHuaweiMobileServicesAvailable(context) == com.huawei.hms.api.ConnectionResult.SUCCESS


        val app: ApplicationInfo = context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        val bundle = app.metaData

        return if (gms && hms) {
            firstPriority
                ?: when {
                    bundle.getString("mobile_service").isNullOrEmpty() -> {
                        MobileServiceType.GMS
                    }
                    bundle.getString("mobile_service") == "gms" -> {
                        MobileServiceType.GMS
                    }
                    else -> {
                        MobileServiceType.HMS
                    }
                }

        } else if (gms) {
            MobileServiceType.GMS
        } else if (hms) {
            MobileServiceType.HMS
        } else {
            MobileServiceType.NON
        }
    }

    /**
     * Emui information of Huawei Devices
     *
     * @return EmuiVersion object contains name and code fields name format is EmotionUI_X.X.X and code is integer
     */
    fun getDeviceEmuiVersion(): EmuiVersion {
        return EmuiVersion(getEmuiVersionName(), getEmuiVersionCode())
    }

    @SuppressLint("PrivateApi")
    private fun getEmuiVersionName(): String {
        val classType: Class<*>
        var emuiVerName = ""
        try {
            classType = Class.forName("android.os.SystemProperties")
            val getMethod: Method =
                classType.getDeclaredMethod("get", String::class.java)
            emuiVerName =
                getMethod.invoke(classType, "ro.build.version.emui") as String
        } catch (e: ClassNotFoundException) {
            Log.e(TAG, "ClassNotFoundException")
        } catch (e: NoSuchMethodException) {
            Log.e(TAG, "NoSuchMethodException")
        } catch (e: IllegalAccessException) {
            Log.e(TAG, "IllegalAccessException")
        } catch (e: InvocationTargetException) {
            Log.e(TAG, "InvocationTargetException")
        }
        Log.i(TAG, "Emui version name: $emuiVerName")
        return emuiVerName
    }

    private fun getEmuiVersionCode(): Int {
        var returnObj: Any? = null
        var emuiVersionCode = 0
        try {
            val targetClass = Class.forName("com.huawei.android.os.BuildEx\$VERSION")
            val field: Field = targetClass.getDeclaredField("EMUI_SDK_INT")
            AccessibleObject.setAccessible(arrayOf(field), true)
            returnObj = field.get(targetClass)
            if (null != returnObj) {
                emuiVersionCode = (returnObj as Int?)!!
            }
        } catch (e: ClassNotFoundException) {
            Log.e(TAG, "ClassNotFoundException: ")
        } catch (e: NoSuchFieldException) {
            Log.e(TAG, "NoSuchFieldException: ")
        } catch (e: IllegalAccessException) {
            Log.e(TAG, "IllegalAccessException: ")
        } catch (e: ClassCastException) {
            Log.e(
                TAG,
                "ClassCastException: getEMUIVersionCode is not a number" + returnObj.toString()
            )
        }
        Log.i(TAG, "emuiVersionCodeValue: $emuiVersionCode")
        return emuiVersionCode
    }
}
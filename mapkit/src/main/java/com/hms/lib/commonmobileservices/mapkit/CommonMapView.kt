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
package com.hms.lib.commonmobileservices.mapkit

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.mapkit.factory.CommonMap
import com.hms.lib.commonmobileservices.mapkit.factory.MapFactory
import com.hms.lib.commonmobileservices.mapkitcommon.R
import com.hms.lib.commonmobileservices.mapkitcommon.databinding.CommonMapViewBinding

/**
 * Custom view representing a common map view that can be used with either Huawei Maps or Google Maps.
 *
 * @param lifecycleContext The context associated with the lifecycle of the map view.
 * @param attrs The attribute set for the view.
 * @param defStyle An attribute in the current theme that contains a reference to a style resource to apply to this view.
 * @param defStyleRes A resource identifier of a style resource that supplies default values for the view, used only if defStyleAttr is 0 or can not be found in the theme. Can be 0 to not look for defaults.
 */
class CommonMapView @JvmOverloads constructor(
    lifecycleContext: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(lifecycleContext, attrs, defStyle, defStyleRes) {

    /**
     * The underlying CommonMap instance associated with this CommonMapView.
     */
    private lateinit var _myCommonMap: CommonMap

    /**
     * Gets the underlying CommonMap instance associated with this CommonMapView.
     */
    val mapImpl: CommonMap
        get() = _myCommonMap

    /**
     * The binding instance for the CommonMapView, used for accessing layout elements and binding data.
     */
    private val binding: CommonMapViewBinding

    /**
     * The context associated with the lifecycle of the CommonMapView.
     */
    private val currentContext = lifecycleContext


    /**
     * Initializes the CommonMapView.
     * - Retrieves the API key for Huawei Maps from the provided attributes.
     * - Determines whether to use a custom map based on the provided attributes.
     * - Inflates the view binding.
     * - If not using a custom map:
     *   - Creates and initializes the underlying CommonMap instance using MapFactory.
     *   - Sets layout parameters for the map view.
     *   - Adds the map view to the root layout.
     *
     * @param attrs The attribute set for the view.
     */
    init {
        // Getting the API key for Huawei Maps
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CommonMapViewParams, 0, 0
        )
        val apiKey = typedArray.getString(R.styleable.CommonMapViewParams_hms_api_key)
        val useCustomMap =
            typedArray.getBoolean(R.styleable.CommonMapViewParams_use_custom_map, false)

        typedArray.recycle()

        // Inflating the view binding
        binding = CommonMapViewBinding.inflate(LayoutInflater.from(currentContext), this, true)

        if (!useCustomMap) {
            // Creating and initializing the CommonMap instance if not using a custom map
            _myCommonMap =
                MapFactory.createAndGetMap(
                    context,
                    Device.getMobileServiceType(context, preferredDistributeType),
                    apiKey
                )
            // Setting layout parameters for the map view
            _myCommonMap.getMapView().layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            // Adding the map view to the root layout
            binding.rlRootCommonMapView.addView(_myCommonMap.getMapView())
        }
    }

    /**
     * Sets the underlying CommonMap instance for this CommonMapView.
     *
     * @param commonMap The CommonMap instance to set.
     */
    fun setCommonMap(commonMap: CommonMap) {
        _myCommonMap = commonMap
        binding.rlRootCommonMapView.addView(_myCommonMap.getMapView())
    }

    /**
     * Initializes the CommonMapView with the provided bundle and lifecycle.
     *
     * @param bundle The bundle containing saved state information.
     * @param lifecycle The lifecycle of the CommonMapView.
     * @return The initialized CommonMap instance.
     */
    fun onCreate(bundle: Bundle?, lifecycle: Lifecycle): CommonMap {
        _myCommonMap.onCreate(bundle)
        lifecycle.addObserver(MyObserver(_myCommonMap))
        return mapImpl
    }

    /**
     * Companion object to hold the preferred distribution type for mobile services.
     */
    companion object {
        /**
         * The preferred distribution type for mobile services.
         */
        var preferredDistributeType: MobileServiceType? = null
    }

    /**
     * Observer class to handle lifecycle events for the associated CommonMap instance.
     *
     * @property commonMap The CommonMap instance to observe.
     */
    internal class MyObserver(private val commonMap: CommonMap) : LifecycleObserver {
        /**
         * Handles the ON_START lifecycle event by calling [CommonMap.onStart].
         */
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun start() {
            commonMap.onStart()
        }

        /**
         * Handles the ON_RESUME lifecycle event by calling [CommonMap.onResume].
         */
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun resume() {
            commonMap.onResume()
        }

        /**
         * Handles the ON_PAUSE lifecycle event by calling [CommonMap.onPause].
         */
        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun pause() {
            commonMap.onPause()
        }

        /**
         * Handles the ON_STOP lifecycle event by calling [CommonMap.onStop].
         */
        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun stop() {
            commonMap.onStop()
        }

        /**
         * Handles the ON_DESTROY lifecycle event by calling [CommonMap.onDestroy].
         */
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun destroy() {
            commonMap.onDestroy()
        }
    }
}
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

class CommonMapView @JvmOverloads constructor(lifecycleContext: Context,
                    attrs: AttributeSet? = null,
                    defStyle: Int =0 ,
                    defStyleRes: Int = 0
                    )
    : RelativeLayout(lifecycleContext, attrs,defStyle,defStyleRes){
    private lateinit var _myCommonMap: CommonMap
    val mapImpl : CommonMap
        get() = _myCommonMap

    private val binding: CommonMapViewBinding

    private val currentContext=lifecycleContext

    init {
        // getting api key for huawei
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CommonMapViewParams, 0, 0
        )
        val apiKey = typedArray.getString(R.styleable.CommonMapViewParams_hms_api_key)
        val useCustomMap = typedArray.getBoolean(R.styleable.CommonMapViewParams_use_custom_map,false)

        typedArray.recycle()

        binding = CommonMapViewBinding.inflate(LayoutInflater.from(currentContext),this,true)

        if(!useCustomMap){
            _myCommonMap =
                MapFactory.createAndGetMap(context,
                    Device.getMobileServiceType(context, preferredDistributeType),
                    apiKey
                )
            _myCommonMap.getMapView().layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            binding.rlRootCommonMapView.addView(_myCommonMap.getMapView())
        }
    }


    fun setCommonMap(commonMap: CommonMap){
        _myCommonMap=commonMap
        binding.rlRootCommonMapView
            .addView(_myCommonMap.getMapView())
    }

    fun onCreate(bundle: Bundle?,lifecycle: Lifecycle) : CommonMap {
        _myCommonMap.onCreate(bundle)
        lifecycle.addObserver(MyObserver(_myCommonMap))
        return mapImpl
    }

    companion object{
        var preferredDistributeType : MobileServiceType?=null
    }

    internal class MyObserver(private val commonMap: CommonMap) : LifecycleObserver{
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun start(){
            commonMap.onStart()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun resume(){
            commonMap.onResume()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun pause(){
            commonMap.onPause()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun stop(){
            commonMap.onStop()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun destroy(){
            commonMap.onDestroy()
        }

    }

}
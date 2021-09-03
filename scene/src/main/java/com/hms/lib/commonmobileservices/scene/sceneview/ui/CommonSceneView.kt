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
package com.hms.lib.commonmobileservices.scene.sceneview.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.hms.lib.commonmobileservices.scene.common.CommonData
import com.hms.lib.commonmobileservices.scene.common.CommonView
import com.hms.lib.commonmobileservices.scene.sceneview.ISceneView
import com.hms.lib.commonmobileservices.scene.sceneview.SceneViewCallback
import com.hms.lib.commonmobileservices.scene.sceneview.model.SceneViewParams
import com.hms.lib.commonmobileservices.scene.sceneview.service.SceneViewFactory
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType

class CommonSceneView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes),
    CommonView {

    private lateinit var mSceneView: ISceneView

    override fun init(commonData: CommonData) {
        SceneViewFactory.createAndGetSceneView(
            context,
            Device.getMobileServiceType(context,
                preferredDistributeType
            ),
            commonData as SceneViewParams
        ).let {
            if (it != null) {
                mSceneView = it
            }
        }
        addView(mSceneView.getSceneView())
    }

    override fun load() {
        mSceneView.load()
    }

    override fun clear() {
        mSceneView.clear()
    }

    override fun onPause() {
        //Not necessary to implement
    }

    override fun destroy() {
        //Not necessary to implement
    }

    override fun onResume() {
        //Not necessary to implement
    }

    override fun isSupport(deviceMarketName: String): Boolean? {
        return if (Device.getMobileServiceType(
                context,
                preferredDistributeType
            ) == MobileServiceType.HMS
        ) {
            true
        } else {
            // You can implement the support logic of other services here.
            null
        }
    }

    companion object {
        var preferredDistributeType: MobileServiceType? = null
    }

    fun onSceneViewCallback(sceneViewCallback: SceneViewCallback?) {
        mSceneView.onSceneViewCallback(sceneViewCallback)
    }
}
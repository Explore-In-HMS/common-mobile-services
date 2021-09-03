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
package com.hms.lib.commonmobileservices.scene.faceview.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.hms.lib.commonmobileservices.scene.common.CommonData
import com.hms.lib.commonmobileservices.scene.common.CommonView
import com.hms.lib.commonmobileservices.scene.faceview.FaceViewCallback
import com.hms.lib.commonmobileservices.scene.faceview.IAugmentedFaceView
import com.hms.lib.commonmobileservices.scene.faceview.model.FaceViewParams
import com.hms.lib.commonmobileservices.scene.faceview.service.FaceViewFactory
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.scene.common.SceneConstants.Companion.AR_VIEW_FACE_VIEW_SUPPORTED_DEVICES

class CommonAugmentedFaceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes),
    CommonView {

    private lateinit var mAugmentedFaceView: IAugmentedFaceView

    init {
        inflateLayout()
    }

    private fun inflateLayout() {
        FaceViewFactory.createAndGetAugmentedFaceView(
            context,
            Device.getMobileServiceType(context,
                preferredDistributeType
            )
        ).let {
            if (it != null) {
                mAugmentedFaceView = it
            }
        }
        addView(mAugmentedFaceView.getFaceView())
    }

    override fun init(commonData: CommonData) {
        mAugmentedFaceView.init(commonData as FaceViewParams)
    }

    override fun load() {
        mAugmentedFaceView.loadView()
    }

    override fun clear() {
        mAugmentedFaceView.clearView()
    }

    override fun destroy() {
        mAugmentedFaceView.destroyView()
    }

    override fun onResume() {
        mAugmentedFaceView.resumeView()
    }

    override fun isSupport(deviceMarketName: String): Boolean? {
        return if (Device.getMobileServiceType(
                context,
                preferredDistributeType
            ) == MobileServiceType.HMS
        ) {
            AR_VIEW_FACE_VIEW_SUPPORTED_DEVICES.contains(deviceMarketName.removePrefix("HUAWEI "))
        } else {
            // You can implement the support logic of other services here.
            null
        }
    }

    override fun onPause() {
        mAugmentedFaceView.pauseView()
    }

    companion object {
        var preferredDistributeType: MobileServiceType? = null
    }

    fun onFaceViewCallback(faceViewCallback: FaceViewCallback?) {
        mAugmentedFaceView.onFaceViewCallback(faceViewCallback)
    }
}


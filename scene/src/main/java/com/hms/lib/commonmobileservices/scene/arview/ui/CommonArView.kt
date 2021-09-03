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
package com.hms.lib.commonmobileservices.scene.arview.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.hms.lib.commonmobileservices.scene.arview.ArViewCallback
import com.hms.lib.commonmobileservices.scene.arview.IArView
import com.hms.lib.commonmobileservices.scene.arview.model.ArViewParams
import com.hms.lib.commonmobileservices.scene.arview.service.ArViewFactory
import com.hms.lib.commonmobileservices.scene.common.CommonData
import com.hms.lib.commonmobileservices.scene.common.CommonView
import com.hms.lib.commonmobileservices.scene.faceview.ui.CommonAugmentedFaceView
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.scene.common.SceneConstants.Companion.AR_VIEW_FACE_VIEW_SUPPORTED_DEVICES

class CommonArView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes),
    CommonView {

    private lateinit var mArView: IArView

    init {
        inflateLayout()
    }

    private fun inflateLayout() {
        ArViewFactory.createAndGetArView(
            context,
            Device.getMobileServiceType(
                context,
                CommonAugmentedFaceView.preferredDistributeType
            )
        ).let {
            if (it != null) {
                mArView = it
            }
        }
        addView(mArView.getArView())
    }

    override fun init(commonData: CommonData) {
        mArView.init(commonData as ArViewParams)
    }

    override fun load() {
        mArView.loadView()
    }

    override fun clear() {
        mArView.clearView()
    }

    override fun destroy() {
        mArView.destroyView()
    }

    override fun onResume() {
        mArView.resumeView()
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
        mArView.pauseView()
    }

    companion object {
        var preferredDistributeType: MobileServiceType? = null
    }

    fun onArViewCallback(arViewCallback: ArViewCallback?) {
        mArView.onArViewCallback(arViewCallback)
    }
}

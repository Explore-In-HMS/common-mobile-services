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
package com.hms.lib.commonmobileservices.scene.faceview.service

import android.content.Context
import android.graphics.SurfaceTexture
import android.view.SurfaceHolder
import android.view.View
import android.widget.Toast
import com.huawei.hms.scene.sdk.FaceView
import com.huawei.hms.scene.sdk.common.LandmarkType
import com.huawei.hms.scene.sdk.common.SdkType
import com.hms.lib.commonmobileservices.scene.faceview.FaceViewCallback
import com.hms.lib.commonmobileservices.scene.faceview.IAugmentedFaceView
import com.hms.lib.commonmobileservices.scene.faceview.model.FaceViewParams
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class HuaweiAugmentedFaceView(context: Context, sdkType: SdkType) : FaceView(context,sdkType),
    IAugmentedFaceView {
    private var mContext: Context = context
    private var mFaceViewParams: FaceViewParams? = null
    private var mFaceViewCallback: FaceViewCallback? = null
    private var isLoaded = false

    override fun getFaceView(): View {
        return this
    }


    override fun init(faceViewParams: FaceViewParams) {
        mFaceViewParams = faceViewParams
    }

    override fun loadView() {
        if (!isLoaded) {
            // Load materials.
            val index = loadAsset(mFaceViewParams?.resourceName, LandmarkType.TIP_OF_NOSE)
            if (index < 0) {
                Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_LONG).show()
            }
            // (Optional) Set the initial status.
            setInitialPose(
                index,
                mFaceViewParams?.position.let { it?.toFloatArray()},
                mFaceViewParams?.scale.let { it?.toFloatArray()},
                mFaceViewParams?.rotation.let { it?.toFloatArray()})
            isLoaded = true
        }
    }

    override fun clearView() {
        if (isLoaded) {
            clearResource()
            loadAsset("", LandmarkType.TIP_OF_NOSE)
            isLoaded = false
        }
    }

    override fun resumeView() {
        super.onResume()
    }

    override fun pauseView() {
        super.onPause()
    }

    override fun destroyView() {
        super.destroy()
    }

    override fun onFaceViewCallback(faceViewCallback: FaceViewCallback?) {
        mFaceViewCallback = faceViewCallback
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)
        mFaceViewCallback?.onSurfaceCreated(gl,config)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
        mFaceViewCallback?.onSurfaceChanged(gl,width,height)
    }

    override fun onDrawFrame(gl: GL10?) {
        super.onDrawFrame(gl)
        mFaceViewCallback?.onDrawFrame(gl)
    }

    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {
        super.onFrameAvailable(surfaceTexture)
        mFaceViewCallback?.onFrameAvailable(surfaceTexture)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        super.surfaceDestroyed(holder)
        mFaceViewCallback?.surfaceDestroyed(holder)
    }
}
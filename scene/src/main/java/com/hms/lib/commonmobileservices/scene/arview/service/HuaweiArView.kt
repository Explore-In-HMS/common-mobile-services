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
package com.hms.lib.commonmobileservices.scene.arview.service

import android.content.Context
import android.graphics.SurfaceTexture
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import android.widget.Toast
import com.huawei.hms.scene.sdk.ARView
import com.hms.lib.commonmobileservices.scene.arview.ArViewCallback
import com.hms.lib.commonmobileservices.scene.arview.IArView
import com.hms.lib.commonmobileservices.scene.arview.model.ArViewParams
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class HuaweiArView(context: Context) : ARView(context),
    IArView {
    private var mContext: Context = context
    private var mArViewParams: ArViewParams? = null
    private var mArViewCallback: ArViewCallback? = null
    private var isLoaded = false

    override fun getArView(): View {
        return this
    }

    override fun init(arViewParams: ArViewParams) {
        mArViewParams = arViewParams
        isLoaded = false
    }

    override fun loadView() {
        if (!isLoaded) {
            // Load materials.
            val index = loadAsset(mArViewParams!!.resourceName)
            if (index < 0) {
                Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_LONG).show()
            }
            // (Optional) Set the initial status.
            setInitialPose(mArViewParams!!.scale!!.toFloatArray(), mArViewParams!!.rotation!!.toFloatArray())
            isLoaded = true
        }
    }

    override fun clearView() {
        if (isLoaded) {
            clearResource()
            loadAsset("")
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

    override fun onArViewCallback(arViewCallback: ArViewCallback?) {
        mArViewCallback = arViewCallback
    }

    override fun performClick(): Boolean {
        return mArViewCallback?.performClick() ?: super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return mArViewCallback?.onTouchEvent(event) ?: super.onTouchEvent(event)
    }


    override fun surfaceDestroyed(holder: SurfaceHolder) {
        super.surfaceDestroyed(holder)
        mArViewCallback?.surfaceDestroyed(holder)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)
        mArViewCallback?.onSurfaceCreated(gl,config)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
        mArViewCallback?.onSurfaceChanged(gl,width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        super.onDrawFrame(gl)
        mArViewCallback?.onDrawFrame(gl)
    }

    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {
        super.onFrameAvailable(surfaceTexture)
        mArViewCallback?.onFrameAvailable(surfaceTexture)
    }
}
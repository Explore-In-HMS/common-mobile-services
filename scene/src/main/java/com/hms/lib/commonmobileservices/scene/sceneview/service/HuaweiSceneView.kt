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
package com.hms.lib.commonmobileservices.scene.sceneview.service

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import com.huawei.hms.scene.sdk.SceneView
import com.hms.lib.commonmobileservices.scene.sceneview.ISceneView
import com.hms.lib.commonmobileservices.scene.sceneview.SceneViewCallback
import com.hms.lib.commonmobileservices.scene.sceneview.model.SceneViewParams

class HuaweiSceneView : SceneView,
    ISceneView {

    private val mContext: Context
    private var mSceneViewParams: SceneViewParams? = null
    private var mSceneViewCallback: SceneViewCallback? = null

    constructor(
        context: Context,
        sceneViewParams: SceneViewParams?
    )
            : super(context) {
        mContext = context
        init(sceneViewParams)
    }

    constructor(
        context: Context,
        attrs: AttributeSet? = null
    )
            : super(context, attrs) {
        mContext = context
    }

    override fun getSceneView(): View? {
        return this
    }

    override fun init(sceneViewParams: SceneViewParams?) {
        mSceneViewParams = sceneViewParams
    }


    override fun load() {
        surfaceCreated(holder)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        super.surfaceCreated(holder)
        mSceneViewCallback?.surfaceCreated(holder)

        loadScene(mSceneViewParams!!.resourceName)

        loadSpecularEnvTexture(mSceneViewParams!!.specularEnvTexturePath)

        loadDiffuseEnvTexture(mSceneViewParams!!.diffuseEnvTexturePath)

        loadSkyBox(mSceneViewParams!!.skyboxPath)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        super.surfaceChanged(holder, format, width, height)
        mSceneViewCallback?.surfaceChanged(holder, format, width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mSceneViewCallback?.onDraw(canvas)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        super.surfaceDestroyed(holder)
        mSceneViewCallback?.surfaceDestroyed(holder)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()
        return mSceneViewCallback?.onTouchEvent(event) ?: super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return mSceneViewCallback?.performClick() ?: super.performClick()
    }

    override fun clear() {
        clearScene()
    }

    override fun onSceneViewCallback(sceneViewCallback: SceneViewCallback?) {
        mSceneViewCallback = sceneViewCallback
    }
}
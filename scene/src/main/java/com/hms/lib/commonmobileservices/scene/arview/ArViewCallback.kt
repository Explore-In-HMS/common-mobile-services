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
package com.hms.lib.commonmobileservices.scene.arview

import android.graphics.SurfaceTexture
import android.view.MotionEvent
import android.view.SurfaceHolder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

interface ArViewCallback {

    fun onSurfaceCreated(gL10: GL10?, config: EGLConfig?)

    fun onSurfaceChanged(gL10: GL10?, width: Int, height: Int)

    fun onDrawFrame(gL10: GL10?)

    fun surfaceDestroyed(holder: SurfaceHolder?)

    fun onFrameAvailable(surfaceTexture: SurfaceTexture?)

    fun onTouchEvent(motionEvent: MotionEvent?): Boolean

    fun performClick(): Boolean

}
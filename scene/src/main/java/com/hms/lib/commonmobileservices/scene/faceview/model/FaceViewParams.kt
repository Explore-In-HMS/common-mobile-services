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
package com.hms.lib.commonmobileservices.scene.faceview.model

import android.os.Parcelable
import com.hms.lib.commonmobileservices.scene.common.CommonData
import com.hms.lib.commonmobileservices.scene.common.ViewType
import kotlinx.android.parcel.Parcelize

@Parcelize
class FaceViewParams(
    override val resourceName: String,
    override val viewType: ViewType,
    var drawable: Int? = null,
    var texture: Int? = null,
    var position: List<Float>? = null,
    var scale: List<Float>? = null,
    var rotation: List<Float>? = null
): CommonData, Parcelable
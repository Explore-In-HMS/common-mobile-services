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

package com.hms.lib.commonmobileservices.awareness.model

import java.io.Serializable

/**
 * Represents awareness data associated with a particular type of awareness.
 *
 * @property awarenessValueData The data associated with the awareness, which can be of any type.
 * @property awarenessType The type of awareness represented by this data.
 * @constructor Creates an instance of [AwarenessData] with the provided awareness value data and type.
 */
data class AwarenessData(
    val awarenessValueData: Any,
    val awarenessType: AwarenessType
) : Serializable
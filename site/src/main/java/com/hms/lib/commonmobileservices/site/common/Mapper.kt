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

package com.hms.lib.commonmobileservices.site.common

import android.content.Entity
import com.huawei.hms.site.api.model.Coordinate

abstract class Mapper<I, O> {
    //abstract fun map(from: I): O
    abstract fun mapToEntity(from: O): I
    fun mapToEntityList(from: List<O>): List<I> = from.map { mapToEntity(it) }
}



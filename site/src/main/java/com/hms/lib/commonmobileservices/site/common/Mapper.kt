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

/**
 * Abstract class for mapping objects from one type to another.
 *
 * @param I The input type to be mapped to.
 * @param O The output type to be mapped from.
 */
abstract class Mapper<I, O> {
    /**
     * Maps an object of type [O] to an object of type [I].
     *
     * @param from The object to be mapped from.
     * @return The mapped object of type [I].
     */
    abstract fun mapToEntity(from: O): I

    /**
     * Maps a list of objects of type [O] to a list of objects of type [I].
     *
     * @param from The list of objects to be mapped from.
     * @return The list of mapped objects of type [I].
     */
    fun mapToEntityList(from: List<O>): List<I> = from.map { mapToEntity(it) }
}
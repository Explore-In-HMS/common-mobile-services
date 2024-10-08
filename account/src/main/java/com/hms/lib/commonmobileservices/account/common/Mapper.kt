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
package com.hms.lib.commonmobileservices.account.common

/**
 * Abstract class representing a mapper that converts input of type [I] to output of type [O].
 * Subclasses must implement the [map] function to define the mapping logic.
 *
 * @param I the type of the input to be mapped
 * @param O the type of the output after mapping
 */
interface Mapper<I, O> {
    /**
     * Maps the input [from] of type [I] to an output of type [O].
     *
     * @param from the input to be mapped
     * @return the output after mapping
     */
    fun map(from: I): O
}
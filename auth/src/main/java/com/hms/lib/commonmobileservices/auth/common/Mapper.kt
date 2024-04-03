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

package com.hms.lib.commonmobileservices.auth.common

/**
 * Abstract class representing a mapper that transforms input of type [I] to output of type [O].
 * Implementations of this class should provide a concrete implementation for the [map] function.
 * @param I the input type.
 * @param O the output type.
 */
abstract class Mapper<I, O> {
    /**
     * Maps input of type [I] to output of type [O].
     * @param from the input to be mapped.
     * @return the mapped output.
     */
    abstract fun map(from: I): O
}
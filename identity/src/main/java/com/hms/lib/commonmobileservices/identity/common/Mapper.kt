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
package com.hms.lib.commonmobileservices.identity.common

/**
 * Abstract class representing a mapper that converts objects of type [I] to objects of type [O].
 * @param I The input type to be mapped from.
 * @param O The output type to be mapped to.
 */
abstract class Mapper<I, O> {

    /**
     * Maps an object of type [I] to an object of type [O].
     * @param from The object to be mapped.
     * @return The mapped object of type [O].
     */
    abstract fun map(from: I): O
}
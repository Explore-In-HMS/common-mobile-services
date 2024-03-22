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

package com.hms.lib.commonmobileservices.push.model

/**
 * The abstract class Mapper is a generic class that defines a mapping function from one data type to another.
 * It is designed to be extended by concrete mapper classes that implement the map method.
 *
 * @param <From> the input data type that needs to be mapped
 * @param <To> the output data type that the input is mapped to
 */
abstract class Mapper<From, To> {
    /**
     * This method maps the input data of type From to the output data of type To.
     *
     * @param from the input data that needs to be mapped
     * @return the output data that the input is mapped to
     */
    abstract fun map(from: From): To
}

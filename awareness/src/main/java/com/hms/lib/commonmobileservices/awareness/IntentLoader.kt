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

package com.hms.lib.commonmobileservices.awareness

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Helper class to load an Intent with extras or return null if the class name is not found.
 */
class IntentLoader {
    /**
     * Loads an Intent with extras or returns null if the class name is not found.
     *
     * @param context The context to use for loading the Intent.
     * @param fullClassName The fully qualified class name of the target component.
     * @param extras The extras to be added to the Intent.
     * @return An Intent with extras, or null if the class name is not found.
     */
    fun loadIntentWithExtrasOrReturnNull(
        context: Context,
        fullClassName: String,
        extras: Bundle
    ): Intent? =
        try {
            // Constructing an Intent with the given extras and setting the class using the full class name
            Intent(Intent.ACTION_VIEW).apply { putExtras(extras) }
                .setClass(context, Class.forName(fullClassName))
        } catch (e: ClassNotFoundException) {
            // If the class is not found, print the stack trace and return null
            e.printStackTrace()
            null
        }
}
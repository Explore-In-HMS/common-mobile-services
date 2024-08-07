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
package com.hms.lib.commonmobileservices.account.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Helper class for managing shared preferences related to account service.
 *
 * @property context The application context.
 */
class SharedPrefHelper(private val context: Context) {
    /**
     * Retrieves the shared preferences instance.
     */
    private val sharedPreferences: SharedPreferences
        get() {
            return context.getSharedPreferences("account_service", Context.MODE_PRIVATE)
        }

    /**
     * Sets the stored email address in shared preferences.
     *
     * @param email The email address to be stored.
     */
    fun setEmail(email: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("email", email)
        editor.apply()
    }

    /**
     * Retrieves the stored email address from shared preferences.
     *
     * @return The stored email address, or an empty string if not found.
     */
    fun getEmail(): String {
        return sharedPreferences.getString("email", "")!!
    }
}
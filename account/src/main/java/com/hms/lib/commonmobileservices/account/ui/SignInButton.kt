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
package com.hms.lib.commonmobileservices.account.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.gms.common.SignInButton
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType

/**
 * Custom sign-in button view.
 *
 * This view provides a sign-in button for integrating with Google or Huawei sign-in services.
 * It automatically detects the mobile service type and displays the appropriate sign-in button.
 *
 * @property listener The callback function to be invoked when the sign-in button is clicked.
 */
class SignInButton : LinearLayout {
    private var listener: (() -> Unit)? = null

    /**
     * Sets the click listener for the sign-in button.
     *
     * @param listener The callback function to be invoked when the sign-in button is clicked.
     */
    fun setSignInButtonClickListener(listener: (() -> Unit)) {
        this.listener = listener
    }

    /**
     * Constructor for creating a SignInButton instance.
     *
     * @param context The context in which the button will be created.
     */
    constructor(context: Context?) : super(context)

    /**
     * Constructor for creating a SignInButton instance with attributes.
     *
     * @param context The context in which the button will be created.
     * @param attrs The attribute set.
     */
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        // Determine the appropriate sign-in button based on the mobile service type
        val view: View = when (Device.getMobileServiceType(context!!)) {
            MobileServiceType.GMS -> {
                SignInButton(context)
            }
            MobileServiceType.HMS -> {
                HuaweiIdAuthButton(context)
            }
            else -> {
                HuaweiIdAuthButton(context)
            }
        }

        // Set layout parameters and add the sign-in button view
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = params
        this@SignInButton.addView(view)

        // Set click listener to invoke the callback function
        view.setOnClickListener { listener?.invoke() }
    }
}
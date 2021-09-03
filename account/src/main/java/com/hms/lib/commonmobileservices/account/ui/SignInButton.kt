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

class SignInButton : LinearLayout {
    private var listener: (() -> Unit)? = null
    fun setSignInButtonClickListener(listener: (() -> Unit)) {
        this.listener = listener
    }

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

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
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = params
        this@SignInButton.addView(view)
        view.setOnClickListener { listener?.invoke() }
    }
}
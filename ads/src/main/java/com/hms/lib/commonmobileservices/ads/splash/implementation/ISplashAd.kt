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

package com.hms.lib.commonmobileservices.ads.splash.implementation

import android.app.Activity

/**
 * Interface for displaying Splash Ads.
 */
interface ISplashAd {
    /**
     * Shows the Splash Ad in the specified activity context.
     *
     * @param activity The activity context in which to show the ad.
     */
    fun show(activity: Activity)
}
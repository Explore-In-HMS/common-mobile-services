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

package com.hms.lib.commonmobileservices.ads.rewarded.implementation

import android.app.Activity
import android.os.Bundle
import com.hms.lib.commonmobileservices.ads.rewarded.common.UserRewardEarnedListener
import com.hms.lib.commonmobileservices.ads.rewarded.common.MetaDataChangedListener

/**
 * Interface representing a rewarded ad.
 */
interface IRewardedAd {
    /**
     * Retrieves the metadata associated with the rewarded ad.
     *
     * @return The metadata bundle.
     */
    fun getMetaData(): Bundle

    /**
     * Sets a listener for metadata changes on the rewarded ad.
     *
     * @param callback The callback to be invoked when metadata changes.
     */
    fun setOnMetadataChangedListener(callback: MetaDataChangedListener)

    /**
     * Sets whether the rewarded ad should be shown in immersive mode.
     *
     * @param value Boolean indicating whether to enable immersive mode.
     */
    fun setImmersive(value: Boolean)

    /**
     * Shows the rewarded ad to the user.
     *
     * @param activity The activity context in which to show the ad.
     * @param callback The callback for handling user's earned rewards.
     */
    fun show(activity: Activity, callback: UserRewardEarnedListener)
}
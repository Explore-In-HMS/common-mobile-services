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
package com.hms.lib.commonmobileservices.push.slider.models

/**
 * Represents a push notification containing slider items.
 * @property callToAction The call-to-action message associated with the notification.
 * @property header The header text of the notification.
 * @property items The list of slider items included in the notification.
 */
data class SliderPushNotification(
    val callToAction: String,
    val header: String,
    val items: List<SliderItem>
)
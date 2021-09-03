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

package com.hms.lib.commonmobileservices.translate.manager

import com.hms.lib.commonmobileservices.translate.HuaweiTranslate
import com.hms.lib.commonmobileservices.core.MobileServiceType

class TranslateFactory {
    fun getTranslateService(type: MobileServiceType): ITranslateAPI? {
        return HuaweiTranslate()
    }
}
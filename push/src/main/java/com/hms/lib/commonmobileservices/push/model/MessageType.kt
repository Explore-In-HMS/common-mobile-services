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
 * The MessageType enum class represents the type of message that can be sent or received by the system.
 * Each message type is associated with a unique integer value.
 */
enum class MessageType(val value: Int) {
    MessageReceived(0),
    DeletedMessages(1),
    MessageSent(2),
    SendError(3),
    NewToken(4),
}

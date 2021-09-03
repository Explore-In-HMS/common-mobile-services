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

package com.hms.lib.commonmobileservices.awareness.model

import java.io.Serializable

data class WeatherAwarenessData(
    val weatherDataArray: MutableList<Int>
):Serializable

enum class WeatherDataValue(val value:Int) {
    WEATHER_SUNNY(1),
    WEATHER_MOSTLY_SUNNY(2),
    WEATHER_PARTLY_SUNNY(3),
    WEATHER_INTERMITTENT_CLOUD(4),
    WEATHER_HAZY_SUNSHINE(5),
    WEATHER_MOSTLY_CLOUDY(6),
    WEATHER_CLOUDS(7),
    WEATHER_DEARY(8),
    WEATHER_FOG(11),
    WEATHER_SHOWERS(12),
    WEATHER_MOSTLY_CLOUDY_WITH_SHOWERS(13),
    WEATHER_PARTLY_SUNNY_WITH_SHOWERS(14),
    WEATHER_T_STORMS(15),
    WEATHER_MOSTLY_CLOUDY_WITH_T_STORMS(16),
    WEATHER_PARTLY_SUNNY_WITH_T_STORMS(17),
    WEATHER_RAIN(18),
    WEATHER_FLURRIES(19),
    WEATHER_MOSTLY_CLOUDY_WITH_FLURRIES(20),
    WEATHER_PARTLY_SUNNY_WITH_FLURRIES(21),
    WEATHER_SNOW(22),
    WEATHER_MOSTLY_CLOUDY_WITH_SNOW(23),
    WEATHER_ICE(24),
    WEATHER_SLEET(25),
    WEATHER_FREEZING_RAIN(26),
    WEATHER_RAIN_AND_SNOW(29),
    WEATHER_HOT(30),
    WEATHER_COLD(31),
    WEATHER_WINDY(32),
    WEATHER_CLEAR(33),
    WEATHER_MOSTLY_CLEAR(34),
    WEATHER_PARTLY_CLOUDY(35),
    WEATHER_INTERMITTENT_CLOUDS_2(36),
    WEATHER_HAZY_MOONLIGHT(37),
    WEATHER_MOSTLY_CLOUDY_2(38),
    WEATHER_PARTLY_CLOUDY_WITH_SHOWERS(39),
    WEATHER_MOSTLY_CLOUDY_WITH_SHOWERS_2(40),
    WEATHER_PARTLY_CLOUDY_WITH_T_STORMS(41),
    WEATHER_MOSTLY_CLOUDY_WITH_T_STORMS_2(42),
    WEATHER_MOSTLY_CLOUDY_WITH_FLURRIES_2(43),
    WEATHER_MOSTLY_CLOUDY_WITH_SNOW_2(44);
    companion object {
        fun valueOf(value: Int) = values().find { it.value == value }
    }
}
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

/**
 * Represents awareness data related to weather conditions.
 *
 * @property weatherDataArray A mutable list containing integer values representing weather data.
 * @constructor Creates an instance of [WeatherAwarenessData] with the provided weather data array.
 */
data class WeatherAwarenessData(
    val weatherDataArray: MutableList<Int>
) : Serializable

/**
 * Enumerates different values representing weather conditions.
 *
 * @property value The integer value associated with the weather condition.
 */
enum class WeatherDataValue(val value: Int) {
    /**
     * Represents sunny weather.
     */
    WEATHER_SUNNY(1),

    /**
     * Represents mostly sunny weather.
     */
    WEATHER_MOSTLY_SUNNY(2),

    /**
     * Represents partly sunny weather.
     */
    WEATHER_PARTLY_SUNNY(3),

    /**
     * Represents intermittent cloud cover.
     */
    WEATHER_INTERMITTENT_CLOUD(4),

    /**
     * Represents hazy sunshine.
     */
    WEATHER_HAZY_SUNSHINE(5),

    /**
     * Represents mostly cloudy weather.
     */
    WEATHER_MOSTLY_CLOUDY(6),

    /**
     * Represents cloudy weather.
     */
    WEATHER_CLOUDS(7),

    /**
     * Represents dreary weather.
     */
    WEATHER_DEARY(8),

    /**
     * Represents foggy weather.
     */
    WEATHER_FOG(11),

    /**
     * Represents showers.
     */
    WEATHER_SHOWERS(12),

    /**
     * Represents mostly cloudy weather with showers.
     */
    WEATHER_MOSTLY_CLOUDY_WITH_SHOWERS(13),

    /**
     * Represents partly sunny weather with showers.
     */
    WEATHER_PARTLY_SUNNY_WITH_SHOWERS(14),

    /**
     * Represents thunderstorms.
     */
    WEATHER_T_STORMS(15),

    /**
     * Represents mostly cloudy weather with thunderstorms.
     */
    WEATHER_MOSTLY_CLOUDY_WITH_T_STORMS(16),

    /**
     * Represents partly sunny weather with thunderstorms.
     */
    WEATHER_PARTLY_SUNNY_WITH_T_STORMS(17),

    /**
     * Represents rainy weather.
     */
    WEATHER_RAIN(18),

    /**
     * Represents flurries.
     */
    WEATHER_FLURRIES(19),

    /**
     * Represents mostly cloudy weather with flurries.
     */
    WEATHER_MOSTLY_CLOUDY_WITH_FLURRIES(20),

    /**
     * Represents partly sunny weather with flurries.
     */
    WEATHER_PARTLY_SUNNY_WITH_FLURRIES(21),

    /**
     * Represents snowy weather.
     */
    WEATHER_SNOW(22),

    /**
     * Represents mostly cloudy weather with snow.
     */
    WEATHER_MOSTLY_CLOUDY_WITH_SNOW(23),

    /**
     * Represents icy weather.
     */
    WEATHER_ICE(24),

    /**
     * Represents sleet.
     */
    WEATHER_SLEET(25),

    /**
     * Represents freezing rain.
     */
    WEATHER_FREEZING_RAIN(26),

    /**
     * Represents a mix of rain and snow.
     */
    WEATHER_RAIN_AND_SNOW(29),

    /**
     * Represents hot weather.
     */
    WEATHER_HOT(30),

    /**
     * Represents cold weather.
     */
    WEATHER_COLD(31),

    /**
     * Represents windy weather.
     */
    WEATHER_WINDY(32),

    /**
     * Represents clear weather.
     */
    WEATHER_CLEAR(33),

    /**
     * Represents mostly clear weather.
     */
    WEATHER_MOSTLY_CLEAR(34),

    /**
     * Represents partly cloudy weather.
     */
    WEATHER_PARTLY_CLOUDY(35),

    /**
     * Represents intermittent clouds.
     */
    WEATHER_INTERMITTENT_CLOUDS_2(36),

    /**
     * Represents hazy moonlight.
     */
    WEATHER_HAZY_MOONLIGHT(37),

    /**
     * Represents mostly cloudy weather variant 2.
     */
    WEATHER_MOSTLY_CLOUDY_2(38),

    /**
     * Represents partly cloudy weather with showers.
     */
    WEATHER_PARTLY_CLOUDY_WITH_SHOWERS(39),

    /**
     * Represents mostly cloudy weather with showers variant 2.
     */
    WEATHER_MOSTLY_CLOUDY_WITH_SHOWERS_2(40),

    /**
     * Represents partly cloudy weather with thunderstorms.
     */
    WEATHER_PARTLY_CLOUDY_WITH_T_STORMS(41),

    /**
     * Represents mostly cloudy weather with thunderstorms variant 2.
     */
    WEATHER_MOSTLY_CLOUDY_WITH_T_STORMS_2(42),

    /**
     * Represents mostly cloudy weather with flurries variant 2.
     */
    WEATHER_MOSTLY_CLOUDY_WITH_FLURRIES_2(43),

    /**
     * Represents mostly cloudy weather with snow variant 2.
     */
    WEATHER_MOSTLY_CLOUDY_WITH_SNOW_2(44);

    /**
     * Provides a way to retrieve a [WeatherDataValue] enum instance based on its integer value.
     *
     * @param value The integer value associated with the weather condition.
     * @return The [WeatherDataValue] enum instance corresponding to the given value, or null if not found.
     */
    companion object {
        fun valueOf(value: Int) = values().find { it.value == value }
    }
}
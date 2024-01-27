package com.nimdokai.pet.core.data.fakes

import com.nimdokai.pet.core_network.model.AvailableUnitTypesHolderJson
import com.nimdokai.pet.core_network.model.CurrentConditionsJson
import com.nimdokai.pet.core_network.model.DirectionJson
import com.nimdokai.pet.core_network.model.MeasureUnitJson
import com.nimdokai.pet.core_network.model.PastHoursRangeJson
import com.nimdokai.pet.core_network.model.PrecipitationSummaryJson
import com.nimdokai.pet.core_network.model.PressureTendencyJson
import com.nimdokai.pet.core_network.model.TemperatureSummaryJson
import com.nimdokai.pet.core_network.model.WindGust
import com.nimdokai.pet.core_network.model.WindJson

val past24HourRange = PastHoursRangeJson(
    minimum = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 1.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 2.0,
            unit = "C",
            unitType = 9,
        )
    ),
    maximum = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 10.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 20.0,
            unit = "C",
            unitType = 9,
        )
    )
)

val past12HourRange = PastHoursRangeJson(
    minimum = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 3.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 4.0,
            unit = "C",
            unitType = 9,
        )
    ),
    maximum = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 30.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 40.0,
            unit = "C",
            unitType = 9,
        )
    )
)

val past6HourRange = PastHoursRangeJson(
    minimum = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 5.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 6.0,
            unit = "C",
            unitType = 9,
        )
    ),
    maximum = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 50.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 60.0,
            unit = "C",
            unitType = 9,
        )
    )
)

val temperatureSummary = TemperatureSummaryJson(
    past6HourRange = past6HourRange,
    past12HourRange = past12HourRange,
    past24HourRange = past24HourRange,
)

val apparentTemperature = AvailableUnitTypesHolderJson(
    imperial = MeasureUnitJson(
        value = 11.0,
        unit = "F",
        unitType = -1,
    ),
    metric = MeasureUnitJson(
        value = 22.0,
        unit = "C",
        unitType = 9,
    )
)
val ceiling = AvailableUnitTypesHolderJson(
    imperial = MeasureUnitJson(
        value = 12.0,
        unit = "F",
        unitType = -1,
    ),
    metric = MeasureUnitJson(
        value = 23.0,
        unit = "C",
        unitType = 9,
    )
)
val dewPoint = AvailableUnitTypesHolderJson(
    imperial = MeasureUnitJson(
        value = 13.0,
        unit = "F",
        unitType = -1,
    ),
    metric = MeasureUnitJson(
        value = 24.0,
        unit = "C",
        unitType = 9,
    )
)
val past24HourTemperatureDeparture = AvailableUnitTypesHolderJson(
    imperial = MeasureUnitJson(
        value = 14.0,
        unit = "F",
        unitType = -1,
    ),
    metric = MeasureUnitJson(
        value = 25.0,
        unit = "C",
        unitType = 9,
    )
)
val precip1hr = AvailableUnitTypesHolderJson(
    imperial = MeasureUnitJson(
        value = 15.0,
        unit = "F",
        unitType = -1,
    ),
    metric = MeasureUnitJson(
        value = 26.0,
        unit = "C",
        unitType = 9,
    )
)
val precipitationSummary = PrecipitationSummaryJson(
    pastHour = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 16.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 27.0,
            unit = "C",
            unitType = 9,
        )
    ),
    past3Hours = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 17.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 28.0,
            unit = "C",
            unitType = 9,
        )
    ),
    past6Hours = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 18.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 29.0,
            unit = "C",
            unitType = 9,
        )
    ),
    past9Hours = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 19.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 30.0,
            unit = "C",
            unitType = 9,
        )
    ),
    past12Hours = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 20.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 31.0,
            unit = "C",
            unitType = 9,
        )
    ),
    past18Hours = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 21.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 32.0,
            unit = "C",
            unitType = 9,
        )
    ),
    past24Hours = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 22.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 33.0,
            unit = "C",
            unitType = 9,
        )
    ),
    precipitation = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 23.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 34.0,
            unit = "C",
            unitType = 9,
        )
    ),
)
val pressure = AvailableUnitTypesHolderJson(
    imperial = MeasureUnitJson(
        value = 23.0,
        unit = "F",
        unitType = -1,
    ),
    metric = MeasureUnitJson(
        value = 34.0,
        unit = "C",
        unitType = 9,
    )
)
val pressureTendency = PressureTendencyJson(
    localizedText = "localizedText",
    code = "code",
)
val realFeelTemperature = AvailableUnitTypesHolderJson(
    imperial = MeasureUnitJson(
        value = 24.0,
        unit = "F",
        unitType = -1,
    ),
    metric = MeasureUnitJson(
        value = 35.0,
        unit = "C",
        unitType = 9,
    )
)
val realFeelTemperatureShade = AvailableUnitTypesHolderJson(
    imperial = MeasureUnitJson(
        value = 25.0,
        unit = "F",
        unitType = -1,
    ),
    metric = MeasureUnitJson(
        value = 36.0,
        unit = "C",
        unitType = 9,
    )
)
val temperature = AvailableUnitTypesHolderJson(
    imperial = MeasureUnitJson(
        value = 26.0,
        unit = "F",
        unitType = -1,
    ),
    metric = MeasureUnitJson(
        value = 37.0,
        unit = "C",
        unitType = 9,
    )
)
val wetBulbTemperature = AvailableUnitTypesHolderJson(
    imperial = MeasureUnitJson(
        value = 27.0,
        unit = "F",
        unitType = -1,
    ),
    metric = MeasureUnitJson(
        value = 38.0,
        unit = "C",
        unitType = 9,
    )
)
val wind = WindJson(
    direction = DirectionJson(
        degrees = 1,
        localized = "localized",
        english = "english",
    ),
    speed = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 27.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 38.0,
            unit = "C",
            unitType = 9,
        )
    )
)
val windChillTemperature = AvailableUnitTypesHolderJson(
    imperial = MeasureUnitJson(
        value = 28.0,
        unit = "F",
        unitType = -1,
    ),
    metric = MeasureUnitJson(
        value = 39.0,
        unit = "C",
        unitType = 9,
    )
)
val windGust = WindGust(
    speed = AvailableUnitTypesHolderJson(
        imperial = MeasureUnitJson(
            value = 29.0,
            unit = "F",
            unitType = -1,
        ),
        metric = MeasureUnitJson(
            value = 40.0,
            unit = "C",
            unitType = 9,
        )
    )
)
val fakeCurrentConditionsJson = CurrentConditionsJson(
    epochTime = 1706388021,
    hasPrecipitation = true,
    isDayTime = true,
    temperatureSummary = temperatureSummary,
    weatherText = "weatherText",
    weatherIcon = 1,
    apparentTemperature = apparentTemperature,
    ceiling = ceiling,
    cloudCover = 2,
    dewPoint = dewPoint,
    indoorRelativeHumidity = 3,
    link = "link",
    localObservationDateTime = "localObservationDateTime",
    mobileLink = "mobileLink",
    obstructionsToVisibility = "obstructionsToVisibility",
    past24HourTemperatureDeparture = past24HourTemperatureDeparture,
    precip1hr = precip1hr,
    precipitationSummary = precipitationSummary,
    precipitationType = "precipitationType",
    pressure = pressure,
    pressureTendency = pressureTendency,
    realFeelTemperature = realFeelTemperature,
    realFeelTemperatureShade = realFeelTemperatureShade,
    relativeHumidity = 4,
    temperature = temperature,
    uVIndex = 5,
    uVIndexText = "uVIndexText",
    visibility = pressure,
    wetBulbTemperature = wetBulbTemperature,
    wind = wind,
    windChillTemperature = windChillTemperature,
    windGust = windGust,
)
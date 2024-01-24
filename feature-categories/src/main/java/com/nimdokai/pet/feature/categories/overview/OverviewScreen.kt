package com.nimdokai.pet.feature.categories.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nimdokai.pet.core.resources.R
import com.nimdokai.pet.core.resources.UnicodeDegreeSings
import com.nimdokai.pet.feature.categories.list.CurrentConditionsUiState
import com.nimdokai.pet.feature.categories.list.CurrentWeatherUi
import com.nimdokai.pet.feature.categories.list.HourlyForecastUi
import com.nimdokai.pet.feature.categories.list.HourlyForecastUiState
import com.nimdokai.pet.feature.categories.list.OverviewViewModel

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val currentConditionsUiState by viewModel.currentConditionsUiState.collectAsState()
    val hourlyForecastUiState by viewModel.hourlyForecastUiState.collectAsState()
    Column {
        CurrentConditionsCard(modifier, currentConditionsUiState)
        HourlyForecastList(modifier, hourlyForecastUiState)
    }

}

@Composable
fun HourlyForecastList(modifier: Modifier, hourlyForecastUiState: HourlyForecastUiState) {
    LazyRow(modifier = modifier.padding(16.dp)) {
        items(hourlyForecastUiState.forecasts) {
            HourlyForecastCard(modifier, it)
        }
    }
}

@Composable
fun HourlyForecastCard(modifier: Modifier, forecastUi: HourlyForecastUi) {
    Column(modifier = modifier.padding(horizontal = 4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = forecastUi.temperature)
        Image(
            painter = painterResource(id = forecastUi.icon),
            contentDescription = "Weather icon",
            modifier = Modifier.size(40.dp)
        )
        Text(text = forecastUi.time)
    }
}

@Composable
fun CurrentConditionsCard(modifier: Modifier = Modifier, currentConditionsUiState: CurrentConditionsUiState) {
    val currentConditions = currentConditionsUiState.currentConditions
    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    text = currentConditions.temperature
                )
                if (currentConditions.icon != 0) {
                    Image(
                        painter = painterResource(id = currentConditions.icon),
                        contentDescription = "Weather icon",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.wrapContentHeight()
            ) {
                Text(
                    fontSize = 20.sp,
                    text = currentConditions.description
                )
            }
        }

        Row {
            Text(
                fontSize = 14.sp,
                text = stringResource(id = R.string.temperature_high, currentConditions.pastMaxTemp),
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                fontSize = 14.sp,
                text = stringResource(id = R.string.temperature_low, currentConditions.pastMinTemp)
            )
        }
    }

}

@Preview
@Composable
fun PreviewCurrentConditionsCard() {
    CurrentConditionsCard(
        modifier = Modifier,
        currentConditionsUiState = CurrentConditionsUiState(
            isLoading = false,
            currentConditions = CurrentWeatherUi(
                epochTime = 0,
                hasPrecipitation = true,
                isDayTime = true,
                temperature = "20${UnicodeDegreeSings.Celsius}",
                pastMaxTemp = "31${UnicodeDegreeSings.Celsius}",
                pastMinTemp = "-5${UnicodeDegreeSings.Celsius}",
                icon = R.drawable.weather_icon_01,
                description = "Sunny day",
            ),
        )
    )
}

@Preview
@Composable
fun PreviewHourlyForecastList() {
    HourlyForecastList(
        modifier = Modifier,
        hourlyForecastUiState = HourlyForecastUiState(
            isLoading = false,
            forecasts = listOf(
                HourlyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}",
                    time = "now",
                    icon = R.drawable.weather_icon_01
                ),
                HourlyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}",
                    time = "11:00",
                    icon = R.drawable.weather_icon_02
                ),
                HourlyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}",
                    time = "12:00",
                    icon = R.drawable.weather_icon_03
                ),
                HourlyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}",
                    time = "13:00",
                    icon = R.drawable.weather_icon_04
                ),
                HourlyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}",
                    time = "14:00",
                    icon = R.drawable.weather_icon_05
                ),
                HourlyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}",
                    time = "15:00",
                    icon = R.drawable.weather_icon_06
                ),
                HourlyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}",
                    time = "16:00",
                    icon = R.drawable.weather_icon_07
                ),
                HourlyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}",
                    time = "17:00",
                    icon = R.drawable.weather_icon_08
                ),
            )
        )
    )
}
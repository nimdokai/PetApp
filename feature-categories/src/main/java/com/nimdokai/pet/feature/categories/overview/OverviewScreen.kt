package com.nimdokai.pet.feature.categories.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nimdokai.pet.core.resources.R
import com.nimdokai.pet.core.resources.UnicodeDegreeSings
import com.nimdokai.pet.core.resources.theme.AppTheme
import com.nimdokai.pet.core.resources.theme.Dimens
import com.nimdokai.pet.feature.categories.list.CurrentWeatherUi
import com.nimdokai.pet.feature.categories.list.DailyForecastUi
import com.nimdokai.pet.feature.categories.list.HourlyForecastUi

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val currentConditionsUiState by viewModel.currentConditionsUiState.collectAsState()
    val hourlyForecastUiState by viewModel.hourlyForecastUiState.collectAsState()
    val dailyForecastUiState by viewModel.dailyForecastUiState.collectAsState()
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CurrentConditionsCard(modifier, currentConditionsUiState)
        HourlyForecast(modifier, hourlyForecastUiState)
        DailyForecast(modifier, dailyForecastUiState)
    }

}


@Composable
fun CurrentConditionsCard(modifier: Modifier = Modifier, currentConditionsUiState: CurrentConditionsUiState) {
    val currentConditions = currentConditionsUiState.currentConditions
    Column(modifier = modifier.padding(Dimens.m)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    style = MaterialTheme.typography.headlineLarge,
                    text = currentConditions.temperature,
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
                    text = currentConditions.description,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        Row {
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = R.string.temperature_high, currentConditions.pastMaxTemp),
                modifier = Modifier.padding(end = Dimens.xs)
            )
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = R.string.temperature_low, currentConditions.pastMinTemp)
            )
        }
    }
}

@Composable
fun HourlyForecast(modifier: Modifier, hourlyForecastUiState: HourlyForecastUiState) {
    Column(
        modifier = modifier.padding(Dimens.m)
    ) {
        Text(
            modifier = modifier.padding(top = Dimens.m, bottom = Dimens.xs),
            text = stringResource(id = hourlyForecastUiState.title)
        )
        HourlyForecastList(modifier, hourlyForecastUiState)
    }
}

@Composable
fun HourlyForecastList(modifier: Modifier, hourlyForecastUiState: HourlyForecastUiState) {
    LazyRow(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.shapes.small)
            .padding(vertical = Dimens.xs)
    ) {
        items(hourlyForecastUiState.forecasts) {
            HourlyForecastCard(modifier, it)
        }
    }
}

@Composable
fun HourlyForecastCard(modifier: Modifier, forecastUi: HourlyForecastUi) {
    Column(modifier = modifier.padding(horizontal = Dimens.xs), horizontalAlignment = Alignment.CenterHorizontally) {
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
fun DailyForecast(modifier: Modifier, dailyForecastUiState: DailyForecastUiState) {
    Column(
        modifier = modifier.padding(Dimens.m)
    ) {
        Text(
            modifier = modifier.padding(top = Dimens.m, bottom = Dimens.xs),
            text = stringResource(id = dailyForecastUiState.title)
        )
        DailyForecastList(modifier, dailyForecastUiState)
    }

}

@Composable
fun DailyForecastList(modifier: Modifier, dailyForecastUiState: DailyForecastUiState) {
    LazyColumn(
        modifier = modifier
    ) {
        items(dailyForecastUiState.forecasts) {
            DailyForecastCard(modifier, it)
        }
    }
}

@Composable
fun DailyForecastCard(modifier: Modifier, dailyForecastUi: DailyForecastUi) {
    Row(
        modifier = modifier
            .padding(vertical = Dimens.xxxs)
            .background(MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.shapes.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier
                .weight(1f)
                .padding(horizontal = Dimens.xs),
            text = dailyForecastUi.title
        )
        Image(
            painter = painterResource(id = dailyForecastUi.icon),
            contentDescription = "Weather icon",
            modifier = Modifier
                .size(40.dp)
                .padding(horizontal = Dimens.xs)
        )
        Text(
            modifier = modifier
                .weight(1f)
                .padding(horizontal = Dimens.xs),
            text = dailyForecastUi.temperature,
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true)
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

@Preview(showBackground = true)
@Composable
fun PreviewHourlyForecast() {
    HourlyForecast(
        modifier = Modifier,
        hourlyForecastUiState = HourlyForecastUiState(
            isLoading = false,
            title = R.string.hourly_forecast,
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


@Preview(showBackground = true)
@Composable
fun PreviewDailyForecast() {
    DailyForecast(
        modifier = Modifier,
        dailyForecastUiState = DailyForecastUiState(
            title = R.string.daily_forecast,
            isLoading = false,
            forecasts = listOf(
                DailyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}/-5${UnicodeDegreeSings.Celsius}",
                    title = "Today",
                    icon = R.drawable.weather_icon_01
                ),
                DailyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}/-5${UnicodeDegreeSings.Celsius}",
                    title = "Tomorrow",
                    icon = R.drawable.weather_icon_02
                ),
                DailyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}/-5${UnicodeDegreeSings.Celsius}",
                    title = "Monday",
                    icon = R.drawable.weather_icon_03
                ),
                DailyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}/-5${UnicodeDegreeSings.Celsius}",
                    title = "Tuesday",
                    icon = R.drawable.weather_icon_04
                ),
                DailyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}/-5${UnicodeDegreeSings.Celsius}",
                    title = "Wednesday",
                    icon = R.drawable.weather_icon_05
                ),
                DailyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}/-5${UnicodeDegreeSings.Celsius}",
                    title = "Thursday",
                    icon = R.drawable.weather_icon_06
                ),
                DailyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}/-5${UnicodeDegreeSings.Celsius}",
                    title = "Friday",
                    icon = R.drawable.weather_icon_07
                ),
                DailyForecastUi(
                    temperature = "20${UnicodeDegreeSings.Celsius}/-5${UnicodeDegreeSings.Celsius}",
                    title = "Saturday",
                    icon = R.drawable.weather_icon_08
                ),
            )
        )
    )
}
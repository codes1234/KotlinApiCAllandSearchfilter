package com.example.project.Models


data class UserProfileResponse(
    val location : Rohit,
    val current : Current,
    val condition: Result,
    val forecast: Forecast,
)


data class Rohit(
    val country : String,
    val name : String
)

data class Current(
    val temp_c : Double,
    val temp_f : Double,
    val humidity : Double,
    val uv : Double,
    val wind_kph : Double,
    val precip_mm : Double,
    val feelslike_c : Double,
    val pressure_mb  : Double,
    val vis_km : Double,



    val condition: Result
)

data class Result(
    var text : String
)
data class Forecast(
    val forecastday : Array<Forecastday>


)

data class Forecastday(
    val day : Day,
    val hour : Array<Hour>,
    val astro : Astro,
)

data class Astro(
    val sunrise : String,
    val sunset : String,
)

data class Day(
    val maxtemp_c : Double,
    val mintemp_c : Double
)

data class Hour(
    val time : String,
    val temp_c : Double
)

package com.github.shk0da.yahoofinance.model

data class TickerCandle(
    val symbol: String,
    val date: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val adjClose: Double,
    val volume: Int
)
package com.github.shk0da.yahoofinance.model

data class FundamentalValue(
    val asOfDate: String,
    val periodType: String,
    val currencyCode: String,
    val valueRaw: Double,
    val valueFmt: String
)
package com.github.shk0da.yahoofinance.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.shk0da.yahoofinance.model.FundamentalResult
import com.github.shk0da.yahoofinance.model.TickerCandle
import com.github.shk0da.yahoofinance.model.TickerInfo
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.Collectors

class YahooClient {

    companion object {
        const val BALANCE_SHEET_URI =
            "https://query1.finance.yahoo.com/ws/fundamentals-timeseries/v1/finance/timeseries"
        const val HISTORY_DATA_URI = "https://query1.finance.yahoo.com/v7/finance/download"
        const val TICKER_INFO_URI = "https://query1.finance.yahoo.com/v8/finance/chart"
    }

    private val httpClient = HttpClient.newHttpClient()
    private val objectMapper = ObjectMapper().registerModule(KotlinModule())

    fun tickerInfo(symbol: String, region: String = "US"): TickerInfo {
        val common =
            "&lang=en-US&includePrePost=false&interval=2m&range=1d&corsDomain=in.finance.yahoo.com&.tsrc=finance"
        val uri = "$TICKER_INFO_URI/$symbol?region=$region$common"
        val balanceRequest = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .timeout(Duration.of(10, ChronoUnit.SECONDS))
            .GET()
            .build()
        val response = httpClient.send(balanceRequest, HttpResponse.BodyHandlers.ofString())
        return objectMapper.readValue(response.body())
    }

    fun fundamental(symbol: String, types: List<String>, region: String = "US"): FundamentalResult {
        val common = "&lang=en-US&padTimeSeries=true&merge=false&period2=2589901779&corsDomain=finance.yahoo.com"
        val uri = "$BALANCE_SHEET_URI/$symbol?region=$region&symbol=$symbol&type=${types.joinToString(",")}$common"
        val balanceRequest = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .timeout(Duration.of(10, ChronoUnit.SECONDS))
            .GET()
            .build()
        val response = httpClient.send(balanceRequest, HttpResponse.BodyHandlers.ofString())
        return objectMapper.readValue(response.body())
    }

    fun historyData(symbol: String, periodStart: Date, periodEnd: Date, interval: String = "1d"): List<TickerCandle> {
        val common = "&events=history"
        val uri ="$HISTORY_DATA_URI/$symbol?period1=${periodStart.time/1000}&period2=${periodEnd.time/1000}&interval=${interval}$common"
        val historyRequest = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .timeout(Duration.of(10, ChronoUnit.SECONDS))
            .GET()
            .build()
        val response = httpClient.send(historyRequest, HttpResponse.BodyHandlers.ofString())
        return response.body().lines().stream()
            .skip(1)
            .map { it.split(",") }
            .map {
                TickerCandle(
                    symbol,
                    it[0],
                    it[1].toDouble(),
                    it[2].toDouble(),
                    it[3].toDouble(),
                    it[4].toDouble(),
                    it[5].toDouble(),
                    it[6].toInt()
                )
            }.collect(Collectors.toList())
    }
}
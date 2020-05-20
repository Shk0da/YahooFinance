package com.github.shk0da.yahoofinance.model

data class TickerInfo(val chart: Chart) {
    data class Chart(val result: List<Ticker>, val error: Any? = null) {
        data class Ticker(val meta: Meta, val timestamp: List<Long>, val indicators: Indicators) {
            data class Meta(
                val currency: String,
                val symbol: String,
                val exchangeName: String,
                val instrumentType: String,
                val firstTradeDate: Long,
                val regularMarketTime: Long,
                val gmtoffset: Long,
                val timezone: String,
                val exchangeTimezoneName: String,
                val regularMarketPrice: Double,
                val chartPreviousClose: Double,
                val previousClose: Double,
                val scale: Int,
                val priceHint: Int,
                val currentTradingPeriod: Any,
                val tradingPeriods: Any,
                val dataGranularity: String,
                val range: String,
                val validRanges: List<String>
            )

            data class Indicators(val quote: List<Quote>) {
                data class Quote(
                    val volume: List<Int>,
                    val open: List<Double>,
                    val close: List<Double>,
                    val high: List<Double>,
                    val low: List<Double>
                )
            }
        }
    }

    fun getSymbol(): String {
        return chart.result.first().meta.symbol
    }

    fun getCurrency(): String {
        return chart.result.first().meta.currency
    }

    fun getInstrumentType(): String {
        return chart.result.first().meta.instrumentType
    }

    fun getExchangeName(): String {
        return chart.result.first().meta.exchangeName
    }

    fun getPreviousClosePrice(): Double {
        return chart.result.first().meta.previousClose
    }

    fun getLastClosePrice(): Double {
        return chart.result.first().indicators.quote.first().close.last()
    }

    override fun toString(): String {
        return "TickerInfo(" +
                "symbol=${getSymbol()}, " +
                "currency=${getCurrency()}, " +
                "instrumentType=${getInstrumentType()}, " +
                "exchangeName=${getExchangeName()}, " +
                "previousClosePrice=${getPreviousClosePrice()}, " +
                "lastClosePrice=${getLastClosePrice()}" +
                ")"
    }
}
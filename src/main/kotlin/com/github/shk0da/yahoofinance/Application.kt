package com.github.shk0da.yahoofinance

import com.github.shk0da.yahoofinance.client.TradingViewClient
import com.github.shk0da.yahoofinance.client.YahooClient
import com.github.shk0da.yahoofinance.model.ScanRequest
import com.github.shk0da.yahoofinance.model.ScanRequest.*
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.concurrent.TimeUnit.DAYS
import java.util.logging.Logger

class Application {

    private val log = Logger.getAnonymousLogger()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val example = Application()
            example.history()
            example.tickerInfo()
            example.fundamental()
            example.scanMarket()
        }
    }

    fun history() {
        val history = YahooClient().historyData("AAPL", Date(currentTimeMillis() - DAYS.toMillis(180)), Date())
        log.info("history: $history")
    }

    fun tickerInfo() {
        val tickerInfo = YahooClient().tickerInfo("AAPL")
        log.info("tickerInfo: $tickerInfo")
    }

    fun fundamental() {
        val yahooFundamental = YahooClient().fundamental(
            "VRTX", listOf(
                "quarterlyCurrentDebtAndCapitalLeaseObligation",
                "quarterlyLongTermDebt",
                "quarterlyTotalDebt",
                "quarterlyCommonStockEquity",
                "quarterlyStockholdersEquity",
                "quarterlyRetainedEarnings",
                "quarterlyLongTermDebtAndCapitalLeaseObligation"
            )
        )
        log.info("yahooFundamental[quarterlyCurrentDebtAndCapitalLeaseObligation]: ${yahooFundamental.getLastValue("quarterlyCurrentDebtAndCapitalLeaseObligation")}")
        log.info("yahooFundamental[quarterlyLongTermDebt]: ${yahooFundamental.getLastValue("quarterlyLongTermDebt")}")
        log.info("yahooFundamental[quarterlyTotalDebt]: ${yahooFundamental.getLastValue("quarterlyTotalDebt")}")
        log.info("yahooFundamental[quarterlyCommonStockEquity]: ${yahooFundamental.getLastValue("quarterlyCommonStockEquity")}")
        log.info("yahooFundamental[quarterlyStockholdersEquity]: ${yahooFundamental.getLastValue("quarterlyStockholdersEquity")}")
        log.info("yahooFundamental[quarterlyRetainedEarnings]: ${yahooFundamental.getLastValue("quarterlyRetainedEarnings")}")
        log.info("yahooFundamental[quarterlyLongTermDebtAndCapitalLeaseObligation]: ${yahooFundamental.getLastValue("quarterlyLongTermDebtAndCapitalLeaseObligation")}")
    }

    fun scanMarket() {
        val scanRequest = ScanRequest(
            filter = listOf(
                Filter("debt_to_equity", "nempty"),
                Filter("type", "in_range", listOf("stock")),
                Filter("subtype", "in_range", listOf("common")),
                Filter("exchange", "in_range", listOf("AMEX", "NASDAQ", "NYSE")),
                Filter("country", "equal", "United States"),
                Filter("market_cap_basic", "egreater", 50_000_000),
                Filter("debt_to_equity", "in_range", listOf(-50, 3)),
                Filter("total_revenue", "egreater", 0),
                Filter("number_of_employees", "egreater", 1000)
            ),
            options = Options(lang = "en"),
            symbols = Symbols(
                // tickers = arrayListOf("NASDAQ:PRPL")
            ),
            columns = arrayListOf(
                "name",
                "description",
                "total_debt",
                "debt_to_equity",
                "type",
                "subtype",
                "Recommend.MA|1M",
                "Recommend.Other|1M",
                "Recommend.All|1M"
            ),
            sort = Sort("debt_to_equity", "asc"),
            range = intArrayOf(0, 200)
        )
        val marketScan = TradingViewClient().scan(scanRequest)
        log.info("marketScan: $marketScan")
    }
}
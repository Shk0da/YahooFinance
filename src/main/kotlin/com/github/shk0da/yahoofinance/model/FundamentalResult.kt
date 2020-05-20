package com.github.shk0da.yahoofinance.model

import java.util.stream.Collectors

data class FundamentalResult(val timeseries: Timeseries) {

    data class Timeseries(val result: List<Map<String, Any>>, val error: Any?)

    fun getLastValue(name: String): FundamentalValue? {
        return getValues(name).maxBy { it.asOfDate }!!
    }

    @Suppress("unchecked_cast")
    fun getValues(name: String): List<FundamentalValue> {
        val data = timeseries.result.stream().filter { it.containsKey(name) }.map { it[name] }.findFirst()
        if (data.isEmpty) return ArrayList()

        val item = data.get() as List<LinkedHashMap<String, Any>>
        return item.stream().map {
            FundamentalValue(
                asOfDate = it["asOfDate"] as String,
                periodType = it["periodType"] as String,
                currencyCode = it["currencyCode"] as String,
                valueRaw = (it["reportedValue"] as LinkedHashMap<String, Any>)["raw"] as Double,
                valueFmt = (it["reportedValue"] as LinkedHashMap<String, Any>)["fmt"] as String
            )
        }.collect(Collectors.toList())
    }
}
package com.github.shk0da.yahoofinance.model

data class ScanRequest(
    val filter: List<Filter>? = null,
    val options: Options? = null,
    val symbols: Symbols? = null,
    val columns: List<String>,
    val sort: Sort? = null,
    val range: IntArray = intArrayOf(0, 100)
) {

    data class Filter(val left: String, val operation: String, val right: Any? = null)

    data class Options(val lang: String?)

    data class Symbols(val query: Query? = null, val tickers: List<String>? = null) {
        data class Query(val types: List<String>)
    }

    data class Sort(val sortBy: String, val sortOrder: String)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScanRequest

        if (filter != other.filter) return false
        if (options != other.options) return false
        if (symbols != other.symbols) return false
        if (columns != other.columns) return false
        if (sort != other.sort) return false
        if (!range.contentEquals(other.range)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = filter?.hashCode() ?: 0
        result = 31 * result + (options?.hashCode() ?: 0)
        result = 31 * result + (symbols?.hashCode() ?: 0)
        result = 31 * result + columns.hashCode()
        result = 31 * result + (sort?.hashCode() ?: 0)
        result = 31 * result + range.contentHashCode()
        return result
    }
}
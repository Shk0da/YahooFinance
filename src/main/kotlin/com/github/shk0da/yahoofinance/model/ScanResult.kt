package com.github.shk0da.yahoofinance.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ScanResult(val data: List<Data>, val totalCount: Int) {

    data class Data(
        @JsonProperty("s") val name: String,
        @JsonProperty("d") val values: List<Any>
    )
}
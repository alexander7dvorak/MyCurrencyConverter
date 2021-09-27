package com.currencyconverter.data.models

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("base")
    var base: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("rates")
    var rates: Rates,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("timestamp")
    var timestamp: Int
)
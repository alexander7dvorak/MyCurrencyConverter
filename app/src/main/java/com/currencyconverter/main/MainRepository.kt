package com.currencyconverter.main

import com.currencyconverter.data.models.CurrencyResponse
import com.currencyconverter.util.Resource

interface MainRepository {

    suspend fun getRates(base: String): Resource<CurrencyResponse>
}
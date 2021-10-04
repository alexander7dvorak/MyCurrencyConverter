package com.currencyconverter.data.models

class CountryCurrency {
    private var mCountryCode: String? = null
    private var mCountryName: String? = null

    fun Country(
        countryCode: String?,
        countryName: String?
    ) {
        mCountryCode = countryCode
        mCountryName = countryName
    }

    fun getCountryCode(): String? {
        return mCountryCode
    }

    fun getCountryName(): String? {
        return mCountryName
    }
}
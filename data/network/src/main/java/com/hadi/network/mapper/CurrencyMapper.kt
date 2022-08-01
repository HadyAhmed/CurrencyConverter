package com.hadi.network.mapper

import com.hadi.model.LatestCurrencies
import com.hadi.model.Rate
import com.hadi.network.model.LatestCurrenciesApiModel
import org.json.JSONObject

fun LatestCurrenciesApiModel.toDomain(): LatestCurrencies {
    return LatestCurrencies(
        base = base,
        rateApiModel = rateApiModel.convertRates(),
        success = success
    )
}

private fun Any.convertRates(): List<Rate> {
    val rateList = mutableListOf<Rate>()
    val jsonResponse = JSONObject(this.toString())
    jsonResponse.keys().forEachRemaining {
        rateList.add(Rate(it, jsonResponse.getDouble(it)))
    }
    return rateList
}
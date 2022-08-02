package com.hadi.network.mapper

import com.hadi.model.HistoricalData
import com.hadi.model.HistoricalRate
import com.hadi.model.LatestCurrencies
import com.hadi.model.Rate
import com.hadi.network.model.response.HistoricalDataApiModel
import com.hadi.network.model.response.LatestCurrenciesApiModel
import org.json.JSONObject

fun LatestCurrenciesApiModel.toDomain(): LatestCurrencies {
    return LatestCurrencies(
        base = base,
        rateApiModel = rateApiModel.convertRates(),
    )
}

/**
 * mapping response to domain object
 * convert the json response object to list of Rate
 * this will be helpful to use the data in the drop down menu
 * @see Rate
 */
private fun Any.convertRates(): List<Rate> {
    val rateList = mutableListOf<Rate>()
    val jsonResponse = JSONObject(this.toString())
    jsonResponse.keys().forEachRemaining {
        rateList.add(Rate(it, jsonResponse.optDouble(it, 1.0)))
    }
    return rateList
}

fun HistoricalDataApiModel.toDomain(): HistoricalData {
    return HistoricalData(
        base = base,
        startDate = startDate,
        endDate = endDate,
        rates = rates.convertHistoricalRates(),
    )
}

/**
 * mapping response to domain object
 * convert the json response object to list of Rate
 * this will be helpful to use the data in the drop down menu
 * @see Rate
 */
private fun Any.convertHistoricalRates(): List<HistoricalRate> {
    val rateList = mutableListOf<HistoricalRate>()
    val jsonResponse = JSONObject(this.toString())

    jsonResponse.keys().forEachRemaining {
        rateList.add(
            HistoricalRate(
                label = it,
                change = jsonResponse.getJSONObject(it).optDouble("change", 0.0),
                changePct = jsonResponse.getJSONObject(it).optDouble("change_pct", 0.0),
                endRate = jsonResponse.getJSONObject(it).optDouble("end_rate", 0.0),
                startRate = jsonResponse.getJSONObject(it).optDouble("start_rate", 0.0),
            ),
        )
    }
    return rateList
}
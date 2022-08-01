package com.hadi.network.adapter

import com.hadi.network.model.NetworkResponse
import com.hadi.resources.ResProvider
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type
import javax.inject.Inject

class NetworkResponseAdapter<S : Any, E : Any> @Inject constructor(
    private val resProvider: ResProvider,
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
) : CallAdapter<S, Call<NetworkResponse<S, E>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S, E>> {
        return NetworkResponseCall(
            resProvider = resProvider,
            delegate = call,
            errorConverter = errorBodyConverter
        )
    }
}
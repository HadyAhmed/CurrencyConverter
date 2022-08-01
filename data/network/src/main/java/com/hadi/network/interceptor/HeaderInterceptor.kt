package com.hadi.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("apikey", "asdasd3eKPD4rPmtJOatf8kLHGzaYZNfoQGYVX")
            .build()
        return chain.proceed(request)
    }
}
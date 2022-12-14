package com.hadi.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("apikey", "7C1VE1xyS1JojlWWfZqK4hSluD1ZpvIn")
            .build()
        return chain.proceed(request)
    }
}
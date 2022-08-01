package com.hadi.network.adapter

import com.hadi.network.R
import com.hadi.network.model.NetworkResponse
import com.hadi.resources.ResProvider
import okhttp3.Request
import okhttp3.ResponseBody
import okio.IOException
import okio.Timeout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

internal class NetworkResponseCall<S : Any, E : Any> @Inject constructor(
    private val resProvider: ResProvider,
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<NetworkResponse<S, E>> {

    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                val etag = response.headers()["etag"] ?: ""

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Success(body = body, eTag = etag))
                        )
                    } else {
                        // Response is successful but the body is null
                        try {
                            val jsonObject = JSONObject(error?.string() ?: "{}")
                            val errorMessage: String = jsonObject.getString("error")

                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(
                                    NetworkResponse.UnknownError(
                                        RuntimeException(errorMessage)
                                    )
                                )
                            )

                        } catch (e: Exception) {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(
                                    NetworkResponse.UnknownError(
                                        RuntimeException(
                                            error?.string()
                                                ?: resProvider.getStringRes(R.string.an_error_occurred)
                                        )
                                    )
                                )
                            )

                        }


                    }
                } else {
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(
                            NetworkResponse.ApiError(
                                body = code.getCodeErrorMsg() as E,
                                code = code,
                                eTag = etag,
                                jsonObjectString = response.errorBody().toString()
                            )
                        )
                    )
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is UnknownHostException -> NetworkResponse.NetworkError(
                        IOException(
                            resProvider.getStringRes(R.string.no_internet_connection)
                        )
                    )
                    is IOException -> NetworkResponse.NetworkError(throwable)
                    else -> NetworkResponse.UnknownError(throwable)
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(
        resProvider = resProvider,
        delegate = delegate.clone(),
        errorConverter = errorConverter
    )

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    private fun Int.getCodeErrorMsg(): String {
        return if (this == 401) {
            resProvider.getStringRes(R.string.unauthorized_error)
        } else if (this == 429) {
            resProvider.getStringRes(R.string.too_many_requests_error)
        } else if (this >= 500) {
            resProvider.getStringRes(R.string.server_error)
        } else resProvider.getStringRes(R.string.error_with_code, this)
    }
}
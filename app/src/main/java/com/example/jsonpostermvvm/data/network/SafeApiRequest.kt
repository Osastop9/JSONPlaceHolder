package com.example.jsonpostermvvm.data.network

import com.example.jsonpostermvvm.util.ApiException
import retrofit2.Response

abstract class SafeApiRequest{

    suspend fun<T : Any> apiRequest(call : suspend () -> Response<T>) : T{
        val response = call.invoke()
        if (response.isSuccessful){
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            message.append(error)
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }


}
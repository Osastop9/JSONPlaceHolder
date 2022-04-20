package com.example.jsonpostermvvm.data.worker

import android.content.Context
import androidx.work.*
import com.example.jsonpostermvvm.data.repository.PostRepository
import com.example.jsonpostermvvm.util.ApiException

class PostWorker(
    private val context: Context,
    private val params: WorkerParameters,
    private val repository: PostRepository
) :
    CoroutineWorker(context, params){


    override suspend fun doWork(): Result {
        try {
            val title = inputData.getString("title")
            val body = inputData.getString("body")
            try {
                repository.addPost(title!!, body!!)
            } catch (e: ApiException) {
                return Result.failure()
            }
            return Result.success()
        } catch (error: Throwable) {
            return Result.failure()
        }
    }

}
package com.example.jsonpostermvvm.ui.create

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.jsonpostermvvm.data.repository.PostRepository
import com.example.jsonpostermvvm.data.worker.PostWorker
import com.example.jsonpostermvvm.util.ApiException
import com.example.jsonpostermvvm.util.Coroutines
import com.example.jsonpostermvvm.util.NoInternetException
import java.util.concurrent.TimeUnit

class CreatePostViewModel(
    private val repository: PostRepository
) : ViewModel() {
    var title: String? = null
    var body: String? = null
    var createPostInterface: CreatePostInterface? = null

    fun onClickCreate(view: View) {
        createPostInterface?.onRequest()

        if (title.isNullOrEmpty()) {
            createPostInterface?.onFailed("Title is empty")
            return

        }
        if (body.isNullOrEmpty()) {
            createPostInterface?.onFailed("Body is empty")
            return
        }


        Coroutines.main {
            try {
//                repository.addOfflinePost(title!!,body!!)
                repository.addPost(title!!,body!!)
                createPostInterface?.onSuccess()
            } catch (e: ApiException){
                createPostInterface?.onFailed(e.message!!)
            } catch(e: NoInternetException){

                val constrains = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
                val data = Data.Builder()
                    .putString("title",title!!)
                    .putString("body",body!!)
                    .build()
                val request = OneTimeWorkRequest.Builder(PostWorker::class.java)
                    .setConstraints(constrains)
                    .setInputData(data)
                    .setBackoffCriteria(
                        BackoffPolicy.LINEAR,
                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                        TimeUnit.MILLISECONDS)
                    .build()
                WorkManager.getInstance(view.context.applicationContext).enqueue(request)

                createPostInterface?.onPostPone()
            }
        }

    }


}
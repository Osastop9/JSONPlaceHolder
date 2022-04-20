package com.example.jsonpostermvvm.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jsonpostermvvm.data.db.AppDatabase
import com.example.jsonpostermvvm.data.db.entities.Post
import com.example.jsonpostermvvm.data.network.Api
import com.example.jsonpostermvvm.data.network.SafeApiRequest
import com.example.jsonpostermvvm.data.preferences.PreferenceProvider
import com.example.jsonpostermvvm.util.Coroutines
import com.example.jsonpostermvvm.util.now
import com.example.jsonpostermvvm.util.timediff
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


const val MINIMUM_INTERVAL = 300 // 5 menit

class PostRepository(
    private val api: Api,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {
    private val posts = MutableLiveData<List<Post>>()

    init {
        posts.observeForever {
            savePosts(it)
        }
    }

    suspend fun getPosts(): LiveData<List<Post>> {
        return withContext(Dispatchers.IO) {
            fetchPosts()
            db.getPostDao().getPosts()
        }
    }
    suspend fun addPost(title : String, body: String){
        return withContext(Dispatchers.IO){
            val response = apiRequest { api.addPost(Post(null, null, title, body)) }
            db.getPostDao().addPost(response)
        }
    }


    private suspend fun fetchPosts() {
        val lastSavedAt = prefs.getLastSavedAt()
        if (lastSavedAt == null || isFetchNeeded(lastSavedAt)) {
            val response = apiRequest { api.getPosts() }
            posts.postValue(response)
        }
    }

    private fun savePosts(posts: List<Post>) {
        Coroutines.io {
            prefs.saveLastSavedAt(now())
            db.getPostDao().savePosts(posts)
        }
    }

    private fun isFetchNeeded(lastSavedTime: String): Boolean {
        return timediff(lastSavedTime, now()) > MINIMUM_INTERVAL
    }


}
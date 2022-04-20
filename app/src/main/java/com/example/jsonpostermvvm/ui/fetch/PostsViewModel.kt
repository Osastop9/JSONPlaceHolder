package com.example.jsonpostermvvm.ui.fetch

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.jsonpostermvvm.R
import com.example.jsonpostermvvm.data.repository.PostRepository
import com.example.jsonpostermvvm.util.lazyDeferred

class PostsViewModel (
    private val repository: PostRepository
) : ViewModel() {
    val quotes by lazyDeferred {
        repository.getPosts()
    }

    fun onFabClick(view : View){
//        Intent(view.context, CreatePostActivity::class.java).also{
//            view.context.startActivity(it)
//        }
        view.findNavController().navigate(R.id.createPostFragment)
    }
}
package com.example.jsonpostermvvm.ui.fetch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jsonpostermvvm.data.repository.PostRepository

class PostsViewModelFactory (
    private val repository: PostRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostsViewModel(repository) as T
    }
}
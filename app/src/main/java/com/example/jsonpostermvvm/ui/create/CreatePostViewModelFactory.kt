package com.example.jsonpostermvvm.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jsonpostermvvm.data.repository.PostRepository

class CreatePostViewModelFactory(
    private val repository: PostRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreatePostViewModel(repository) as T
    }
}
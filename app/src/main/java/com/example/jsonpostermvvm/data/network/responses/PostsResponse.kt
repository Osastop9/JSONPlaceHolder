package com.example.jsonpostermvvm.data.network.responses

import com.example.jsonpostermvvm.data.db.entities.Post

data class PostsResponse(
    val posts : List<Post>
)
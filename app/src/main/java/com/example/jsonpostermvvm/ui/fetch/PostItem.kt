package com.example.jsonpostermvvm.ui.fetch

import com.example.jsonpostermvvm.R
import com.example.jsonpostermvvm.data.db.entities.Post
import com.example.jsonpostermvvm.databinding.ItemPostBinding
import com.xwray.groupie.databinding.BindableItem

class PostItem (
    private val post : Post
) : BindableItem<ItemPostBinding>(){
    override fun getLayout(): Int = R.layout.item_post

    override fun bind(viewBinding: ItemPostBinding, position: Int) {
        viewBinding.post = post
    }
}
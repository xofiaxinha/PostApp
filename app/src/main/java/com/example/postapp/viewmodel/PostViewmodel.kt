package com.example.postapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postapp.data.models.CreatePostRequest
import com.example.postapp.data.models.Post
import com.example.postapp.data.models.RetroFitInstance
import com.example.postapp.data.models.User
import com.example.postapp.data.models.UserCreateRequest
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class PostViewmodel : ViewModel() {
    var post: List<Post> by mutableStateOf(listOf())
    var users: List<User> by mutableStateOf(listOf())

    private val userId = 1

    fun fetchUsers(){
        viewModelScope.launch {
            try {
                users = RetroFitInstance.api.getUsers()
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun fetchPosts(){
        viewModelScope.launch {
            try {
                post = RetroFitInstance.api.getPost(userId)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun createUser(
        name: String,
        email: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ){
        viewModelScope.launch {
            try {
                val newUser = UserCreateRequest(name, email)
                RetroFitInstance.api.createUser(newUser)
                fetchUsers()
                onSuccess()
            }catch (e: Exception){
                e.printStackTrace()
                onFail()
            }
        }
    }

    fun createPost(
        title: String,
        content: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ){
        viewModelScope.launch {
            try {
                val newPost = CreatePostRequest(title, content)
                RetroFitInstance.api.createPost(userId, newPost)
                fetchPosts()
                onSuccess()
            }catch (e: Exception){
                e.printStackTrace()
                onFail()
            }
        }
    }

    fun deletePost(postId: Int){
        viewModelScope.launch {
            try {
                RetroFitInstance.api.deletePost(postId)
                fetchPosts()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun updatePost(
        postId: Int,
        title: String,
        content: String
    ){
        viewModelScope.launch {
            try {
                val updatePost = CreatePostRequest(title, content)
                RetroFitInstance.api.updatePost(postId, updatePost)
                fetchPosts()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
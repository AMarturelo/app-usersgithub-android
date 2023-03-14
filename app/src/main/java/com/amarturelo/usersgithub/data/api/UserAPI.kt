package com.amarturelo.usersgithub.data.api

import com.amarturelo.usersgithub.data.model.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {
    @GET("/users")
    suspend fun users(): Response<List<UserModel>>
}

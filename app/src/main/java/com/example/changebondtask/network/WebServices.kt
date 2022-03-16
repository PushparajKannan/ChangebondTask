package com.example.changebondtask.network


import com.example.changebondtask.view.model.UsersListResponseModel
import retrofit2.http.*

interface WebServices {


    /**
     * Coroutines API Call
     * Use Suspend KEY Word
     * **/

    @GET("users")
    suspend fun apiUsersResponseList(): UsersListResponseModel

}
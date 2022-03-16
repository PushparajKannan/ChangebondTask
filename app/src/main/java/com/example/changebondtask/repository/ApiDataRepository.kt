package com.example.changebondtask.repository

import com.example.changebondtask.database.AppDatabase
import com.example.changebondtask.network.WebServices
import com.example.changebondtask.repository.ApiDataRepository.ApiError.Companion.EMPTY_API_ERROR
import com.example.changebondtask.utilities.AuthState
import com.example.changebondtask.view.model.Users
import com.example.changebondtask.view.model.UsersListResponseModel
import com.example.changebondtask.view.model.toUserDb
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class ApiDataRepository @Inject constructor(
    val apiLists: WebServices
    ) {

    fun apiUserList() = flow<AuthState<List<Users>>> {
        emit(AuthState.loading())

        apiLists.apiUsersResponseList().let {



            if(it.userList.isNotEmpty()){

            emit(AuthState.authenticated(it.userList))
            }else{
                emit(AuthState.authenticationFailed(""))
            }


        }


    }.catch {

        if (it.getApiError()!!.result!! != null) {
            emit(AuthState.authenticationFailed(it.getApiError()!!.result!!))
        } else if (it.getApiError()!!.message!! != null) {
            emit(AuthState.authenticationFailed(it.getApiError()!!.message!!))
        }

    }.flowOn(Dispatchers.IO)

    /**
     * When 400 Error Response
     */

    data class ApiError(
        val code: Int,
        @SerializedName("response") var result: String? = "",
        @SerializedName("message") var message: String? = ""
    ) {
        companion object {
            val EMPTY_API_ERROR = ApiError(-1, "", "")
        }
    }


    fun Throwable.getApiError(): ApiError? {
        if (this is HttpException) {
            try {
                val errorJsonString = this.response()?.errorBody()?.string()

                //Log.e("Responese", "-->$errorJsonString")
                val error = Gson().fromJson(errorJsonString, ApiError::class.java)
                return ApiError(400, error.result, error.message)
            } catch (exception: Exception) {
                // Ignore
                return EMPTY_API_ERROR
            }
        }
        return EMPTY_API_ERROR
    }

}
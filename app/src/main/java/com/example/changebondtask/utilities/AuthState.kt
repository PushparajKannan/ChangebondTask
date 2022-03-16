package com.example.changebondtask.utilities

sealed class State<T> {
    class Loading<T> : State<T>()
    data class Success<T>(val data: T) : State<T>()
    data class Failed<T>(val message: String) : State<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }
}

sealed class AuthState<T> {
    class Loading<T> : AuthState<T>()
    data class Authenticated<T>(val data: T) : AuthState<T>()
    data class AuthenticationFailed<T>(val message: String) : AuthState<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> authenticated(data: T) = Authenticated(data)
        fun <T> authenticationFailed(message: String) = AuthenticationFailed<T>(message)
    }
}
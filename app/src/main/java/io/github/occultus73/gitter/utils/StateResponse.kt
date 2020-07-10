package io.github.occultus73.gitter.utils

sealed class StateResponse<T> {
    class Loading<T> : StateResponse<T>()
    data class Success<T>(val data: T) : StateResponse<T>()
    data class Failed<T>(val message : String) : StateResponse<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data : T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }

}


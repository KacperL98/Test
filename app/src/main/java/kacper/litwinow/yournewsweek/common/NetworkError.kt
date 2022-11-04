package kacper.litwinow.yournewsweek.common

sealed class NetworkError(message: String) {
    object UnKnown : NetworkError(message = "")
}
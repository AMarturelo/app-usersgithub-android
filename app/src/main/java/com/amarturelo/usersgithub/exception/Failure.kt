package com.amarturelo.usersgithub.exception

sealed class Failure : Error() {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object UnknownError : Failure()
}

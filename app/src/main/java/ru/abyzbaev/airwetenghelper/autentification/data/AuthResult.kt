package ru.abyzbaev.airwetenghelper.autentification.data

import ru.abyzbaev.airwetenghelper.autentification.domain.User

sealed class AuthResult {

    class Success(val user: User) : AuthResult()

    class Error(val e: Exception) : AuthResult()

    object Loading : AuthResult()
}

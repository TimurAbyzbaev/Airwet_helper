package ru.abyzbaev.airwetenghelper.autentification.domain

import ru.abyzbaev.airwetenghelper.autentification.data.AuthResult


interface AuthRepository {

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult

    suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult
}
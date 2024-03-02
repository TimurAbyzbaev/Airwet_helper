package ru.abyzbaev.airwetenghelper.autentification.data

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import ru.abyzbaev.airwetenghelper.autentification.domain.AuthRepository
import ru.abyzbaev.airwetenghelper.autentification.domain.User
import javax.inject.Inject
import kotlinx.coroutines.awaitAll

class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {

    override suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult {
        return try {
            val authResultTask = auth.signInWithEmailAndPassword(email, password)
            Tasks.await(authResultTask) // Дождитесь завершения операции аутентификации
            val user = auth.currentUser
            if (user != null) {
                AuthResult.Success(User.Base(user.email ?: " ", user.uid))
            } else {
                AuthResult.Error(Exception("User is null"))
            }
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult {
        return try {
            val authResultTask = auth.createUserWithEmailAndPassword(email, password)
            Tasks.await(authResultTask) // Дождитесь завершения операции создания пользователя
            val user = auth.currentUser
            if (user != null) {
                AuthResult.Success(User.Base(user.email ?: " ", user.uid))
            } else {
                AuthResult.Error(Exception("User is null"))
            }
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }
}
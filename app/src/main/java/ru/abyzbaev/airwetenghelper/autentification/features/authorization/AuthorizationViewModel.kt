package ru.abyzbaev.airwetenghelper.autentification.features.authorization

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.abyzbaev.airwetenghelper.autentification.data.AuthResult
import ru.abyzbaev.airwetenghelper.autentification.domain.AuthRepository
import ru.abyzbaev.airwetenghelper.core.ui.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    override val sendRequest: suspend (String, String) -> AuthResult =
        { email, password -> authRepository.signInWithEmailAndPassword(email, password) }

}
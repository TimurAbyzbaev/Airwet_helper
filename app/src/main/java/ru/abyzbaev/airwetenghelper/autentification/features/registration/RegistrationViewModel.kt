package ru.abyzbaev.airwetenghelper.autentification.features.registration

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.abyzbaev.airwetenghelper.autentification.data.AuthResult
import ru.abyzbaev.airwetenghelper.autentification.domain.AuthRepository
import ru.abyzbaev.airwetenghelper.core.ui.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    override val sendRequest: suspend (String, String) -> AuthResult =
        { email, password -> authRepository.signUpWithEmailAndPassword(email, password) }

}
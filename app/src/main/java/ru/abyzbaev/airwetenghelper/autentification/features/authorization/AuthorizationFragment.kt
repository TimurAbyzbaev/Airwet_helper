package ru.abyzbaev.airwetenghelper.autentification.features.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.abyzbaev.airwetenghelper.R
import ru.abyzbaev.airwetenghelper.autentification.data.AuthResult
import ru.abyzbaev.airwetenghelper.core.ui.BaseFragment
import ru.abyzbaev.airwetenghelper.databinding.AuthorizationFragmentBinding

@AndroidEntryPoint
class AuthorizationFragment : BaseFragment<AuthorizationFragmentBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?) -> AuthorizationFragmentBinding =
        { inflater, container ->
            AuthorizationFragmentBinding.inflate(inflater, container, false)
        }

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputList = listOf(
            binding.authMail,
            binding.authPassword
        )

        viewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                AuthResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                is AuthResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.e.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                is AuthResult.Success -> {
                    findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                }
            }
        }

        binding.signIn.setOnClickListener {
            val allValidation = inputList.map { it.isValid() }

            if (allValidation.all { it }) {
                viewModel.sendCredentials(
                    email = binding.authMail.text(),
                    password = binding.authPassword.text()
                )
            }
        }
        binding.navigateToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }

    }
}
package ru.abyzbaev.airwetenghelper.autentification.features.registration

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
import ru.abyzbaev.airwetenghelper.databinding.RegistrationFragmentBinding

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<RegistrationFragmentBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?) -> RegistrationFragmentBinding =
        { inflater, container ->
            RegistrationFragmentBinding.inflate(inflater, container, false)

        }
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputList = listOf(
            binding.signUpEmail,
            binding.signUpPasswordLayout
        )

        viewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                AuthResult.Loading -> binding.progressBarRegistration.visibility = View.VISIBLE
                is AuthResult.Error -> {
                    binding.progressBarRegistration.visibility = View.GONE
                    Toast.makeText(requireContext(), it.e.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                is AuthResult.Success -> {
                    findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
                }
            }
        }

        binding.startSignUp.setOnClickListener {
            val allValidation = inputList.map { it.isValid() }
            if (allValidation.all { it }) {
                viewModel.sendCredentials(
                    email = binding.signUpEmail.text(),
                    password = binding.signUpPasswordLayout.text()
                )
            }
        }
    }
}
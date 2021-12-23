package com.bartoszdrozd.mediapp.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.auth.dtos.RegisterUserDTO
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.viewmodels.RegisterViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentRegisterFormPageOneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFormPageOneFragment : Fragment() {
    private val viewModel: RegisterViewModel by hiltNavGraphViewModels(R.id.nav_graph_register)
    private lateinit var navController: NavController
    private var _binding: FragmentRegisterFormPageOneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterFormPageOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setListeners()
    }

    private fun getErrorString(error: AuthErrorCode?): String? {
        return if (error != null) {
            resources.getString(error.messageResId)
        } else {
            null
        }
    }

    private fun setListeners() {
        with(viewModel) {
            emailError.observe(viewLifecycleOwner, { error ->
                binding.email.error = getErrorString(error)
            })

            confirmEmailError.observe(viewLifecycleOwner, { error ->
                binding.confirmEmail.error = getErrorString(error)
            })

            passwordError.observe(viewLifecycleOwner, { error ->
                binding.password.error = getErrorString(error)
            })

            confirmPasswordError.observe(viewLifecycleOwner, { error ->
                binding.confirmPassword.error = getErrorString(error)
            })
        }

        with(binding) {
            buttonNext.setOnClickListener {
                validatePage()
                if (viewModel.isAccountPageValid) {
                    val email = binding.emailText.text.toString().trim()
                    val password = binding.passwordText.text.toString().trim()
                    viewModel.saveAccountDetails(
                        RegisterUserDTO(email, password)
                    )
                    navController.navigate(R.id.action_registerPageOneToPageTwo)
                }
            }

            signInInsteadButton.setOnClickListener {
                navController.navigate(R.id.action_global_nav_graph_signin)
            }

            emailText.doAfterTextChanged {
                validateEmail()
            }

            confirmEmailText.doAfterTextChanged {
                validateConfirmEmail()
            }

            passwordText.doAfterTextChanged {
                validatePassword()
            }

            confirmPasswordText.doAfterTextChanged {
                validateConfirmPassword()
            }
        }
    }

    private fun validatePage() {
        validateEmail()
        validateConfirmEmail()
        validatePassword()
        validateConfirmPassword()
    }

    private fun validateConfirmPassword() {
        val password = binding.passwordText.text.toString().trim()
        viewModel.validateConfirmPassword(
            password,
            binding.confirmPasswordText.text.toString().trim()
        )
    }

    private fun validatePassword() {
        val passwd = binding.passwordText.text.toString().trim()
        val confirmPasswd = binding.confirmPasswordText.text.toString().trim()
        viewModel.validatePassword(binding.passwordText.text.toString().trim())
        if (confirmPasswd.isNotBlank()) {
            viewModel.validateConfirmPassword(passwd, confirmPasswd)
        }
    }

    private fun validateConfirmEmail() {
        val email = binding.emailText.text.toString().trim()
        viewModel.validateConfirmEmail(email, binding.confirmEmailText.text.toString().trim())
    }

    private fun validateEmail() {
        val email = binding.emailText.text.toString().trim()
        val confirmEmail = binding.confirmEmailText.text.toString().trim()
        viewModel.validateEmail(email)
        if (confirmEmail.isNotBlank()) {
            viewModel.validateConfirmEmail(email, confirmEmail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
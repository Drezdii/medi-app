package com.bartoszdrozd.mediapp.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.viewmodels.RegisterViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentRegisterFormPageOneBinding

class RegisterFormPageOneFragment : Fragment() {
    private val viewModel: RegisterViewModel by navGraphViewModels(R.id.nav_graph_register)
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

    private fun setListeners() {
        viewModel.validationErrorsPageOne.observe(viewLifecycleOwner, { errorList ->
            clearErrors()
            errorList.forEach { code ->
                when (code) {
                    AuthErrorCode.EMAIL_IN_USE -> binding.email.error =
                        resources.getString(R.string.email_exists)
                    AuthErrorCode.PASSWORDS_NOT_EQUAL -> binding.confirmPassword.error =
                        resources.getString(R.string.passwords_not_equal)
                    AuthErrorCode.EMAIL_NOT_SET -> binding.email.error =
                        resources.getString(R.string.email_required)
                    AuthErrorCode.EMAILS_NOT_EQUAL -> binding.confirmEmail.error =
                        resources.getString(R.string.emails_not_equal)
                    AuthErrorCode.PASSWORD_TOO_SHORT -> binding.password.error =
                        resources.getString(R.string.password_too_short)
                }
            }
        })

        with(binding) {
            buttonNext.setOnClickListener {
                if (viewModel.validationErrorsPageOne.value!!.isEmpty()) {
                    navController.navigate(R.id.action_registerPageOneToPageTwo)
                }
            }

            emailText.doAfterTextChanged { emailText ->
                val email = emailText.toString().trim()
                val confirmEmail = confirmEmailText.text.toString().trim()
                viewModel.validateEmail(email)
                if (confirmEmail.isNotBlank()) {
                    viewModel.validateConfirmEmail(email, confirmEmail)
                }
            }

            confirmEmailText.doAfterTextChanged { confirmEmail ->
                val email = emailText.text.toString().trim()
                viewModel.validateConfirmEmail(email, confirmEmail.toString().trim())
            }

            passwordText.doAfterTextChanged { password ->
                val passwd = password.toString().trim()
                val confirmPasswd = confirmPasswordText.text.toString().trim()
                viewModel.validatePassword(password.toString().trim())
                if (confirmPasswd.isNotBlank()) {
                    viewModel.validateConfirmPassword(passwd, confirmPasswd)
                }
            }


            confirmPasswordText.doAfterTextChanged { confirmPassword ->
                val password = passwordText.text.toString().trim()
                viewModel.validateConfirmPassword(password, confirmPassword.toString().trim())
            }
        }
    }

    private fun clearErrors() {
        with(binding) {
            email.error = ""
            confirmEmail.error = ""
            password.error = ""
            confirmPassword.error = ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
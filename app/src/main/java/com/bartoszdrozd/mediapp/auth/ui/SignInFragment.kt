package com.bartoszdrozd.mediapp.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.viewmodels.SignInViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private val viewmodel: SignInViewModel by viewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setListeners()
    }

    private fun setListeners() {
        binding.signUpLink.setOnClickListener {
            navController.navigate(R.id.action_global_nav_graph_register)
        }

        binding.signInButton.setOnClickListener {
            val email = binding.loginText.text.toString()
            val password = binding.passwordText.text.toString()
            viewmodel.signIn(email, password)
        }

        viewmodel.validationErrors.observe(viewLifecycleOwner, { errors ->
            for (error in errors) {
                when (error) {
                    AuthErrorCode.GENERIC_SIGN_IN_ERROR -> binding.login.error =
                        resources.getString(R.string.generic_auth_error)
                    else -> ""
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
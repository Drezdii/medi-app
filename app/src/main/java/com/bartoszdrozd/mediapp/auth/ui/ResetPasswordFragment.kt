package com.bartoszdrozd.mediapp.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.auth.viewmodels.ResetPasswordViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentResetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {
    private val viewModel: ResetPasswordViewModel by viewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setListeners()
    }

    private fun setListeners() {
        binding.buttonFinish.setOnClickListener {
            val email = binding.emailText.text.toString().trim()

            if (email.isNotBlank()) {
                viewModel.resetPassword(email)
            } else {
                binding.email.error = resources.getString(R.string.required_field)
            }
        }

        viewModel.resetError.observe(viewLifecycleOwner, { error ->
            binding.email.error = resources.getString(error.messageResId)
        })

        viewModel.resetSuccess.onEach {
            if (it == 1) {
                Toast.makeText(
                    context,
                    resources.getText(R.string.reset_email_sent),
                    Toast.LENGTH_LONG
                ).show()
                navController.popBackStack()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
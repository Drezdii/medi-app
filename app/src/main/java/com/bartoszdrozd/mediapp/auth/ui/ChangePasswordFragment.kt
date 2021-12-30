package com.bartoszdrozd.mediapp.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.auth.models.ChangePasswordErrorCode
import com.bartoszdrozd.mediapp.auth.viewmodels.ChangePasswordViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {
    private val viewModel: ChangePasswordViewModel by viewModels()
    private val args: ChangePasswordFragmentArgs by navArgs()
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.signedAsText.text = args.signedInName

        binding.changePasswordButton.setOnClickListener {
            changePassword()
        }

        viewModel.error.observe(viewLifecycleOwner, { error ->
            when (error) {
                ChangePasswordErrorCode.REAUTHENTICATION_WRONG_CREDENTIALS -> binding.currentPassword.error =
                    resources.getString(R.string.wrong_password)
                ChangePasswordErrorCode.UPDATE_FAILED -> binding.newPassword.error =
                    resources.getString(R.string.generic_error)
                else -> {}
            }
        })
    }

    private fun changePassword() {
        val currentPassword = binding.currentPasswordText.text.toString().trim()
        val newPassword = binding.newPasswordText.text.toString().trim()

        binding.currentPassword.error =
            if (currentPassword.isBlank()) resources.getString(R.string.required_field) else null

        binding.newPassword.error =
            if (newPassword.isBlank()) resources.getString(R.string.required_field) else null

        if (currentPassword.isBlank() || newPassword.isBlank()) {
            return
        }

        viewModel.changePassword(currentPassword, newPassword)
    }
}
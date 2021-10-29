package com.bartoszdrozd.mediapp.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.auth.viewmodels.ResetPasswordViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentResetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

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
            viewModel.resetPassword(email)
        }

        viewModel.resetError.observe(viewLifecycleOwner, { error ->
            binding.email.error = resources.getString(error.messageResId)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
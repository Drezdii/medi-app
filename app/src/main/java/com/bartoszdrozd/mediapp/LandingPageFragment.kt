package com.bartoszdrozd.mediapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.databinding.FragmentLandingPageBinding
import dagger.hilt.android.AndroidEntryPoint

class LandingPageFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: FragmentLandingPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setListeners()
    }

    private fun setListeners() {
        binding.signInButton.setOnClickListener {
            navController.navigate(R.id.landingPage_to_signin)
        }

        binding.registerButton.setOnClickListener {
            navController.navigate(R.id.landingPage_to_register)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
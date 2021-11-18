package com.bartoszdrozd.mediapp.healthforms.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentHealthFormsBinding

class HealthFormsFragment : Fragment() {
    private var _binding: FragmentHealthFormsBinding? = null
    private val binding: FragmentHealthFormsBinding get() = _binding!!

    override

    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHealthFormsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        with(binding) {
            heartFormButton.setOnClickListener {
                navController.navigate(R.id.action_healthFormsFragment_to_nav_graph_heart_form)
            }

            diabetesFormButton.setOnClickListener {
                navController.navigate(R.id.action_healthFormsFragment_to_diabetesFormFragment)
            }

            alzheimersFormButton.setOnClickListener {
                navController.navigate(R.id.action_healthFormsFragment_to_alzheimersFormFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.bartoszdrozd.mediapp.predictions.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentPredictionsPageBinding

class PredictionsPageFragment : Fragment() {
    private var _binding: FragmentPredictionsPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPredictionsPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        with(binding) {
            heartButton.setOnClickListener {
                navController.navigate(R.id.action_predictionsPageFragment_to_heartPredictionFragment)
            }

            diabetesButton.setOnClickListener {
            }

            alzheimersButton.setOnClickListener {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
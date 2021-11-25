package com.bartoszdrozd.mediapp.predictions.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bartoszdrozd.mediapp.databinding.FragmentHeartPredictionBinding
import com.bartoszdrozd.mediapp.predictions.viewmodels.HeartPredictionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeartPredictionFragment : Fragment() {
    private val viewModel: HeartPredictionViewModel by viewModels()
    private var _binding: FragmentHeartPredictionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHeartPredictionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
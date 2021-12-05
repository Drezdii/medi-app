package com.bartoszdrozd.mediapp.predictions.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentPredictionBinding
import com.bartoszdrozd.mediapp.predictions.viewmodels.PredictionViewModel
import com.bartoszdrozd.mediapp.utils.DiseaseType.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PredictionFragment : Fragment() {
    private val viewModel: PredictionViewModel by viewModels()
    private var _binding: FragmentPredictionBinding? = null
    private val binding get() = _binding!!
    private val args: PredictionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPredictionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val icon = when (args.predictionType) {
            HEART -> R.drawable.ic_heart_icon
            ALZHEIMERS -> R.drawable.ic_head_icon
            DIABETES -> R.drawable.ic_hand_blood_icon
        }

        // Set the drawable for the prediction button
        binding.predictButton.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), icon)
        )

        // ImageView was saving the tint as restoring it when navigating back to this destination
        // So we reset the tint here
        DrawableCompat.setTint(
            binding.predictButton.drawable,
            ContextCompat.getColor(requireContext(), R.color.neutral_grey)
        )

        binding.predictButton.setOnClickListener {
            viewModel.predict(args.predictionType)
        }

        viewModel.prediction.observe(viewLifecycleOwner, { prediction ->
            val color = if (prediction.value < 0.5) {
                R.color.success
            } else if (prediction.value >= 0.5 && prediction.value < 0.75) {
                R.color.warning
            } else {
                R.color.error
            }

            DrawableCompat.setTint(
                binding.predictButton.drawable,
                ContextCompat.getColor(requireContext(), color)
            )
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
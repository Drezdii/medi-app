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
import com.bartoszdrozd.mediapp.MainActivity
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentPredictionBinding
import com.bartoszdrozd.mediapp.predictions.viewmodels.PredictionViewModel
import com.bartoszdrozd.mediapp.utils.DiseaseType.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
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

        val icon: Int
        val fragmentTitle: Int
        when (args.predictionType) {
            HEART -> {
                icon = R.drawable.ic_heart_icon
                fragmentTitle = R.string.predict_heart_disease
            }
            ALZHEIMERS -> {
                icon = R.drawable.ic_head_icon
                fragmentTitle = R.string.predict_alzheimers
            }
            DIABETES -> {
                icon = R.drawable.ic_hand_blood_icon
                fragmentTitle = R.string.predict_diabetes
            }
        }

        (activity as MainActivity).supportActionBar?.title = resources.getString(fragmentTitle)

        // Set the drawable for the prediction button
        binding.predictButton.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), icon)
        )

        // ImageView was saving the tint and was restoring it when navigating back to this destination
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
                R.color.prediction_good
            } else if (prediction.value >= 0.5 && prediction.value < 0.75) {
                R.color.prediction_warning
            } else {
                R.color.prediction_serious
            }

            DrawableCompat.setTint(
                binding.predictButton.drawable.mutate(),
                ContextCompat.getColor(requireContext(), color)
            )
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
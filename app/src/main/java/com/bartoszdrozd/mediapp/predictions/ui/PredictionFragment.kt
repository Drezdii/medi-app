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
import com.bartoszdrozd.mediapp.databinding.PredictionButtonBinding
import com.bartoszdrozd.mediapp.predictions.adapters.PredictionHistoryAdapter
import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.predictions.models.PredictionType.*
import com.bartoszdrozd.mediapp.predictions.viewmodels.PredictionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PredictionFragment : Fragment() {
    private val viewModel: PredictionViewModel by viewModels()
    private var _binding: FragmentPredictionBinding? = null
    private var _predButtonBinding: PredictionButtonBinding? = null
    private val binding get() = _binding!!
    private val predButtonBinding get() = _predButtonBinding!!
    private val args: PredictionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPredictionBinding.inflate(inflater, container, false)
        _predButtonBinding = PredictionButtonBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val test = listOf(
            Prediction("11", 0.35f, HEART, 1638378837),
            Prediction("12", 0.55f, HEART, 1638318837),
            Prediction("13", 0.10f, HEART, 1638218837),
            Prediction("14", 0.05f, HEART, 1638118837),
            Prediction("15", 0.99f, HEART, 1638418837),
            Prediction("16", 0.65f, HEART, 1638316837),
            Prediction("17", 0.73f, HEART, 1638312837)
        )

        val adapter = PredictionHistoryAdapter()

        binding.predictionsRecyclerview.adapter = adapter

        adapter.submitList(test)

        val icon = when (args.predictionType) {
            HEART -> R.drawable.ic_heart_icon
            ALZHEIMERS -> R.drawable.ic_head_icon
            DIABETES -> R.drawable.ic_hand_blood_icon
        }

        // Set the drawable for the prediction button
        predButtonBinding.predictButton.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), icon)
        )

        // ImageView was saving the tint as restoring it when navigating back to this destination
        // So we reset the tint here
        DrawableCompat.setTint(
            predButtonBinding.predictButton.drawable,
            ContextCompat.getColor(requireContext(), R.color.neutral_grey)
        )

        predButtonBinding.predictButton.setOnClickListener {
            viewModel.predict()
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
                predButtonBinding.predictButton.drawable,
                ContextCompat.getColor(requireContext(), color)
            )
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
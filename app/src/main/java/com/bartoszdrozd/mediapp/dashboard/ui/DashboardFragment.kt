package com.bartoszdrozd.mediapp.dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.dashboard.viewmodels.DashboardViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentDashboardBinding
import com.bartoszdrozd.mediapp.utils.DiseaseType
import com.bartoszdrozd.mediapp.utils.toLocalDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setListeners()
    }

    private fun setListeners() {
        viewModel.latestPrediction.observe(viewLifecycleOwner, { prediction ->
            if (prediction != null) {
                binding.predictionDate.text =
                    prediction.date.toLocalDate().toString()

                val icon = when (prediction.predictionType) {
                    DiseaseType.HEART -> R.drawable.ic_heart_icon
                    DiseaseType.ALZHEIMERS -> R.drawable.ic_head_icon
                    DiseaseType.DIABETES -> R.drawable.ic_hand_blood_icon
                }

                // Set the drawable for the prediction button
                binding.predictionIcon.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), icon)
                )
            }

            viewModel.latestFormEntry.observe(viewLifecycleOwner, { entry ->
                if (entry != null) {
                    binding.formEntryDate.text = entry.date.toLocalDate().toString()

                    // Change icon to something else
                    val icon = when (entry.diseaseType) {
                        DiseaseType.HEART -> R.drawable.ic_heart_icon
                        DiseaseType.ALZHEIMERS -> R.drawable.ic_head_icon
                        DiseaseType.DIABETES -> R.drawable.ic_hand_blood_icon
                    }

                    // Set the drawable for the prediction button
                    binding.formEntryIcon.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), icon)
                    )
                }
            })

//            binding.callInsuranceButton.setOnClickListener {
//                doAfterConfirmation { dialInsuranceCompany() }
//            }

            binding.medicalHistoryCard.setOnClickListener {
                navController.navigate(R.id.action_dashboard_to_healthFormsHistory)
            }

            binding.predictionsHistoryCard.setOnClickListener {
                navController.navigate(R.id.action_dashboard_to_predictionsHistory)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
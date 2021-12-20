package com.bartoszdrozd.mediapp.dashboard.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
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
import java.util.*

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
        with(binding) {
//            gpButton.setOnClickListener {
//                navController.navigate(R.id.action_dashboard_to_gpPicker)
//            }

//            insuranceButton.setOnClickListener {
//                navController.navigate(R.id.action_dashboard_to_insurancePicker)
//            }
//
//            predictionsButton.setOnClickListener {
//                navController.navigate(R.id.action_dashboard_to_predictionsHistory)
//            }
//
//            healthFormsButton.setOnClickListener {
//                navController.navigate(R.id.action_dashboard_to_healthFormsHistory)
//            }
        }

        viewModel.gp.observe(viewLifecycleOwner, { gp ->
            if (gp != null) {
                binding.gpCardContent.visibility = VISIBLE
                binding.name.text =
                    resources.getString(R.string.name_string, gp.firstName, gp.lastName)

                if (!gp.picture.isNullOrBlank()) {
                    val imageBytes = Base64.getDecoder().decode(gp.picture)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    binding.avatarImage.setImageBitmap(decodedImage)
                }
            } else {
                binding.gpCardContent.visibility = INVISIBLE
            }
        })

        viewModel.insuranceCompany.observe(viewLifecycleOwner, { company ->
            if (company != null) {
                binding.insuranceCompanyCardContent.visibility = VISIBLE
                binding.companyName.text =
                    resources.getString(R.string.name_string, company.name, "")

                if (!company.logo.isNullOrBlank()) {
                    val imageBytes = Base64.getDecoder().decode(company.logo)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    binding.logoImage.setImageBitmap(decodedImage)
                }
            } else {
                binding.insuranceCompanyCardContent.visibility = INVISIBLE
            }
        })

        viewModel.latestPrediction.observe(viewLifecycleOwner, { prediction ->
            if (prediction != null) {
                binding.predictionDate.text =
                    toLocalDate(prediction.date).toString()

                val icon = when (prediction.predictionType) {
                    DiseaseType.HEART -> R.drawable.ic_heart_icon
                    DiseaseType.ALZHEIMERS -> R.drawable.ic_head_icon
                    DiseaseType.DIABETES -> R.drawable.ic_hand_blood_icon
                }

                // Set the drawable for the prediction button
                binding.predictionIcon.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), icon)
                )
            } else {
                // Hide prediction content
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
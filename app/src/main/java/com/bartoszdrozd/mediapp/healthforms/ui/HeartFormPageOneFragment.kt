package com.bartoszdrozd.mediapp.healthforms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentHeartFormPageOneBinding
import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.HeartFormField.*
import com.bartoszdrozd.mediapp.healthforms.viewmodels.HeartFormPageOneViewModel
import com.bartoszdrozd.mediapp.healthforms.viewmodels.HeartFormViewModel
import com.bartoszdrozd.mediapp.utils.FormFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeartFormPageOneFragment : FormFragment() {
    private val pageViewModel: HeartFormPageOneViewModel by viewModels()
    private val formViewModel: HeartFormViewModel by hiltNavGraphViewModels(R.id.nav_graph_heart_form)
    private var _binding: FragmentHeartFormPageOneBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeartFormPageOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        setListeners()
    }

    override fun onResume() {
        super.onResume()

        // Workaround for a bug in the Material Components library
        val ecgItems = resources.getStringArray(R.array.resting_ecg_items)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, ecgItems)
        binding.restingEcgDropdown.setAdapter(adapter)
    }

    private fun setListeners() {
        val ecgItems = resources.getStringArray(R.array.resting_ecg_items)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, ecgItems)

        with(binding) {
            restingEcgDropdown.setAdapter(adapter)

            var selectedECG = -1
            restingEcgDropdown.setOnItemClickListener { _, _, index, _ ->
                selectedECG = index
            }

            buttonNext.setOnClickListener {
                val formData = HeartFormDTO(
                    serumCholesterol = serumCholesterolText.text.toString().trim().toIntOrNull(),
                    fastingBloodSugar = fastingBsText.text.toString().trim().toIntOrNull(),
                    restingECG = selectedECG,
                    restingBloodPressure = restingBpText.text.toString().trim().toIntOrNull(),
                    maxHR = maxHrText.text.toString().trim().toIntOrNull(),
                    stDepression = stDepressionText.text.toString().trim().toIntOrNull()
                )
                if (pageViewModel.validateForm(formData)) {
                    formViewModel.saveFirstPage(formData)
                    navController.navigate(R.id.action_heartFormPageOneFragment_to_heartFormPageTwoFragment)
                }
            }

            pageViewModel.validationErrors.observe(viewLifecycleOwner, { errors ->
                clearErrors()
                for (error in errors) {
                    when (error.first) {
                        SERUM_CHOLESTEROL -> serumCholesterol.error = getErrorString(error.second)
                        FASTING_BLOOD_SUGAR -> fastingBs.error = getErrorString(error.second)
                        RESTING_ECG -> restingEcg.error = getErrorString(error.second)
                        RESTING_BLOOD_PRESSURE -> restingBp.error = getErrorString(error.second)
                        MAXIMUM_HR -> maxHr.error = getErrorString(error.second)
                        ST_DEPRESSION -> stDepression.error = getErrorString(error.second)
                    }
                }
            })
        }
    }

    private fun clearErrors() {
        with(binding) {
            serumCholesterol.error = null
            fastingBs.error = null
            restingEcg.error = null
            restingBp.error = null
            maxHr.error = null
            stDepression.error = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
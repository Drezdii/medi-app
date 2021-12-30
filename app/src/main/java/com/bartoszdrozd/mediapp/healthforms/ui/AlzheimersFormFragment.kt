package com.bartoszdrozd.mediapp.healthforms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentAlzheimersFormBinding
import com.bartoszdrozd.mediapp.healthforms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.healthforms.models.AlzheimersFormField.*
import com.bartoszdrozd.mediapp.healthforms.viewmodels.AlzheimersFormViewModel
import com.bartoszdrozd.mediapp.utils.FormFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AlzheimersFormFragment : FormFragment() {
    private val viewModel: AlzheimersFormViewModel by viewModels()
    private var _binding: FragmentAlzheimersFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlzheimersFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        val eduItems = resources.getStringArray(R.array.education_items)
        val eduAdapter = ArrayAdapter(requireContext(), R.layout.list_item, eduItems)
        binding.educationDropdown.setAdapter(eduAdapter)

        val socioItems = resources.getStringArray(R.array.socioeconomic_items)
        val socioAdapter = ArrayAdapter(requireContext(), R.layout.list_item, socioItems)
        binding.socioDropdown.setAdapter(socioAdapter)

        with(binding) {
            var selectedEducation = -1
            educationDropdown.setOnItemClickListener { _, _, index, _ ->
                selectedEducation = index
            }

            var selectedSocioStatus = -1
            socioDropdown.setOnItemClickListener { _, _, index, _ ->
                selectedSocioStatus = index
            }

            buttonSave.setOnClickListener {
                val formState = AlzheimersFormDTO(
                    educationLevel = selectedEducation,
                    dominantHand = if (dominantHand.checkedButtonId == R.id.right_hand_button) 0 else 1,
                    miniMentalState = miniMentalText.text.toString().trim().toFloatOrNull(),
                    clinicalDementiaRating = clinicalDementiaText.text.toString().trim()
                        .toFloatOrNull(),
                    socioEconomicStatus = selectedSocioStatus,
                    estTotalIntracranial = estTotalIntraValueText.text.toString().trim()
                        .toFloatOrNull(),
                    normalizeWholeBrain = normalizeWholeBrainText.text.toString().trim()
                        .toFloatOrNull()
                )

                viewModel.saveForm(formState)
            }

            viewModel.saveSuccess.onEach {
                if (it == 1) {
                    Toast.makeText(requireContext(), R.string.saved_success, Toast.LENGTH_LONG)
                        .show()
                    findNavController().popBackStack()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel.validationErrors.observe(viewLifecycleOwner, { errors ->
                clearErrors()
                for (error in errors) {
                    // Check for an error for the given form field
                    when (error.first) {
                        EDUCATION -> education.error = getErrorString(error.second)
                        MINI_MENTAL_STATE -> miniMental.error = getErrorString(error.second)
                        CLINICAL_RATING -> clinicalDementia.error = getErrorString(error.second)
                        SOCIOECONOMIC_STATUS -> socioeconomic.error = getErrorString(error.second)
                        EST_TOTAL_INTRACRANIAL -> estTotalIntraValue.error =
                            getErrorString(error.second)
                        NORMALIZE_WHOLE_BRAIN -> normalizeWholeBrain.error =
                            getErrorString(error.second)
                    }
                }
            })

            viewModel.generalError.observe(viewLifecycleOwner, { error ->
                errorBox.text = getErrorString(error)
            })
        }
    }

    private fun clearErrors() {
        with(binding) {
            education.error = null
            miniMental.error = null
            clinicalDementia.error = null
            socioeconomic.error = null
            estTotalIntraValue.error = null
            normalizeWholeBrain.error = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
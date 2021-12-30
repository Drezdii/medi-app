package com.bartoszdrozd.mediapp.healthforms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentDiabetesFormBinding
import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.healthforms.viewmodels.DiabetesFormViewModel
import com.bartoszdrozd.mediapp.utils.FormFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DiabetesFormFragment : FormFragment() {
    private val viewModel: DiabetesFormViewModel by viewModels()
    private var _binding: FragmentDiabetesFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiabetesFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        with(viewModel) {
            isPregnanciesFieldVisible.observe(viewLifecycleOwner, { isVisible ->
                binding.pregnancies.visibility = if (isVisible) VISIBLE else GONE
            })

            pregnanciesError.observe(viewLifecycleOwner, { error ->
                binding.pregnancies.error = getErrorString(error)
            })

            glucoseError.observe(viewLifecycleOwner, { error ->
                binding.glucose.error = getErrorString(error)
            })

            insulinError.observe(viewLifecycleOwner, { error ->
                binding.insulin.error = getErrorString(error)
            })

            bloodPressureError.observe(viewLifecycleOwner, { error ->
                binding.bloodPressure.error = getErrorString(error)
            })

            skinThicknessError.observe(viewLifecycleOwner, { error ->
                binding.skinThickness.error = getErrorString(error)
            })

            bmiError.observe(viewLifecycleOwner, { error ->
                binding.bmi.error = getErrorString(error)
            })

            pedigreeFuncError.observe(viewLifecycleOwner, { error ->
                binding.pedigreeFunc.error = getErrorString(error)
            })

            generalError.observe(viewLifecycleOwner, { error ->
                binding.errorBox.text = getErrorString(error)
            })

            viewModel.saveSuccess.onEach {
                if (it == 1) {
                    Toast.makeText(requireContext(), R.string.saved_success, Toast.LENGTH_LONG)
                        .show()
                    findNavController().popBackStack()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            with(binding) {
                binding.buttonSave.setOnClickListener {
                    viewModel.saveForm(
                        DiabetesFormDTO(
                            pregnanciesText.text.toString().trim().toIntOrNull(),
                            glucoseText.text.toString().trim().toFloatOrNull(),
                            insulinText.text.toString().trim().toFloatOrNull(),
                            bloodPressureText.text.toString().trim().toFloatOrNull(),
                            skinThicknessText.text.toString().trim().toFloatOrNull(),
                            bmiText.text.toString().trim().toFloatOrNull(),
                            pedigreeFuncText.text.toString().trim().toFloatOrNull()
                        )
                    )
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
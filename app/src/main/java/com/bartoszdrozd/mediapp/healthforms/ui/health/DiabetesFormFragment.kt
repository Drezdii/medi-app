package com.bartoszdrozd.mediapp.healthforms.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bartoszdrozd.mediapp.databinding.FragmentDiabetesFormBinding
import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.healthforms.viewmodels.DiabetesFormViewModel
import com.bartoszdrozd.mediapp.utils.FormFragment
import dagger.hilt.android.AndroidEntryPoint

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

            generalError.observe(viewLifecycleOwner, { error ->
                binding.errorBox.text = getErrorString(error)
            })

            with(binding) {
                binding.buttonSave.setOnClickListener {
                    viewModel.saveForm(
                        DiabetesFormDTO(
                            pregnanciesText.text.toString().trim().toIntOrNull(),
                            glucoseText.text.toString().trim().toIntOrNull(),
                            insulinText.text.toString().trim().toIntOrNull(),
                            bloodPressureText.text.toString().trim().toIntOrNull(),
                            skinThicknessText.text.toString().trim().toIntOrNull(),
                            bmiText.text.toString().trim().toIntOrNull()
                        )
                    )
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
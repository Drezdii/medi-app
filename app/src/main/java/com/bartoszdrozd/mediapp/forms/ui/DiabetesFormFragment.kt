package com.bartoszdrozd.mediapp.forms.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bartoszdrozd.mediapp.databinding.FragmentDiabetesFormBinding
import com.bartoszdrozd.mediapp.forms.DiabetesFormViewModel
import com.bartoszdrozd.mediapp.forms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.forms.models.DiabetesFormField.*
import com.bartoszdrozd.mediapp.forms.models.FormErrorCode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiabetesFormFragment : Fragment() {
    private val viewModel: DiabetesFormViewModel by viewModels()
    private var _binding: FragmentDiabetesFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiabetesFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun getErrorString(error: FormErrorCode?): String? {
        return if (error != null) {
            resources.getString(error.messageResId)
        } else {
            null
        }
    }

    private fun clearErrors() {
        with(binding) {
            pregnancies.error = ""
            glucose.error = ""
            insulin.error = ""
            bloodPressure.error = ""
            skinThickness.error = ""
            bmi.error = ""
        }
    }

    private fun setListeners() {
        with(viewModel) {
            isPregnanciesFieldVisible.observe(viewLifecycleOwner, { isVisible ->
                binding.pregnancies.visibility = if (isVisible) VISIBLE else GONE
            })

            formErrors.observe(viewLifecycleOwner, { errors ->
                clearErrors()
                for (error in errors) {
                    when (error.field) {
                        PREGNANCIES -> binding.pregnancies.error =
                            getErrorString(error.errorCode)
                        GLUCOSE_LEVELS -> binding.glucose.error =
                            getErrorString(error.errorCode)
                        INSULIN_LEVELS -> binding.insulin.error =
                            getErrorString(error.errorCode)
                        BLOOD_PRESSURE -> binding.bloodPressure.error =
                            getErrorString(error.errorCode)
                        SKIN_THICKNESS -> binding.skinThickness.error =
                            getErrorString(error.errorCode)
                        BMI -> binding.bmi.error =
                            getErrorString(error.errorCode)
                        ERROR_BOX -> binding.errorBox.text =
                            getErrorString(error.errorCode)
                    }
                }
            })
        }
        binding.buttonSave.setOnClickListener {
            val pregnancies = binding.pregnanciesText.text.toString().trim()
            val glucose = binding.glucoseText.text.toString().trim()
            val insulin = binding.insulinText.text.toString().trim()
            val bloodPressure = binding.bloodPressureText.text.toString().trim()
            val skinThickness = binding.skinThicknessText.text.toString().trim()
            val bmi = binding.bmiText.text.toString().trim()

            viewModel.saveForm(
                DiabetesFormDTO(
                    pregnancies.toIntOrNull(),
                    glucose.toIntOrNull(),
                    insulin.toIntOrNull(),
                    bloodPressure.toIntOrNull(),
                    skinThickness.toIntOrNull(),
                    bmi.toIntOrNull()
                )
            )
        }
//            pregnanciesError.observe(viewLifecycleOwner, { error ->
//                binding.pregnancies.error = getErrorString(error)
//            })
//
//            glucoseError.observe(viewLifecycleOwner, { error ->
//                binding.glucose.error = getErrorString(error)
//            })
//
//            insulinError.observe(viewLifecycleOwner, { error ->
//                binding.insulin.error = getErrorString(error)
//            })
//
//            bloodPressureError.observe(viewLifecycleOwner, { error ->
//                binding.bloodPressure.error = getErrorString(error)
//            })
//
//            skinThicknessError.observe(viewLifecycleOwner, { error ->
//                binding.skinThickness.error = getErrorString(error)
//            })
//
//            bmiError.observe(viewLifecycleOwner, { error ->
//                binding.bmi.error = getErrorString(error)
//            })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
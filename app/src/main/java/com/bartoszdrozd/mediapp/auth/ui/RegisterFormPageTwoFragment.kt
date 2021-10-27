package com.bartoszdrozd.mediapp.auth.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.navGraphViewModels
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.viewmodels.RegisterViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentRegisterFormPageTwoBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class RegisterFormPageTwoFragment : Fragment() {
    private val viewModel: RegisterViewModel by hiltNavGraphViewModels(R.id.nav_graph_register)
    private var _binding: FragmentRegisterFormPageTwoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterFormPageTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun clearErrors(errorList: List<AuthErrorCode>) {
        if (!errorList.contains(AuthErrorCode.FIRST_NAME_NOT_SET)) {
            binding.firstName.error = ""
        }
        if (!errorList.contains(AuthErrorCode.LAST_NAME_NOT_SET)) {
            binding.lastName.error = ""
        }
        if (!errorList.contains(AuthErrorCode.DOB_NOT_SET)) {
            binding.dateOfBirth.error = ""
        }
        if (!errorList.contains(AuthErrorCode.GENDER_NOT_SET)) {
            binding.gender.error = ""
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        val items = resources.getStringArray(R.array.genders)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.genderDropdown.setAdapter(adapter)

        // Make only dates from today and backwards selectable.
        val datesTodayAndBackwardsConstraint = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.before(MaterialDatePicker.todayInUtcMilliseconds()))
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date")
            .setCalendarConstraints(datesTodayAndBackwardsConstraint)
            .build()

        binding.dateOfBirthText.setOnTouchListener { _, motionEvent ->
            // Use ACTION_UP so it does not trigger when scrolling
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (!datePicker.isAdded) {
                    datePicker.show(parentFragmentManager, "dobPicker")
                }
            }
            true
        }

        binding.dateOfBirthText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (!datePicker.isAdded) {
                    datePicker.show(parentFragmentManager, "dobPicker")
                }
            }
        }

        datePicker.addOnDismissListener {
            datePicker.selection?.let { selection ->
                val date =
                    Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault())
                        .toLocalDate()
                val dateText = date.format(DateTimeFormatter.ISO_LOCAL_DATE)

                binding.dateOfBirthText.setText(dateText)
                viewModel.validateDoB(selection)
            }
        }

        viewModel.validationErrors.observe(viewLifecycleOwner, { errorList ->
            for (error in errorList) {
                when (error) {
                    AuthErrorCode.FIRST_NAME_NOT_SET -> binding.firstName.error =
                        resources.getString(R.string.required_field)
                    AuthErrorCode.LAST_NAME_NOT_SET -> binding.lastName.error =
                        resources.getString(R.string.required_field)
                    AuthErrorCode.DOB_NOT_SET -> binding.dateOfBirth.error =
                        resources.getString(R.string.required_field)
                    AuthErrorCode.GENDER_NOT_SET -> binding.gender.error =
                        resources.getString(R.string.required_field)
                    else -> {
                    }
                }
            }
            clearErrors(errorList)
        })

        with(binding) {
            firstNameText.doAfterTextChanged { firstName ->
                viewModel.validateFirstName(firstName.toString().trim())
            }

            lastNameText.doAfterTextChanged { lastName ->
                viewModel.validateLastName(lastName.toString().trim())
            }

            // Listen to gender dropdown changes
            var selectedGender = -1
            genderDropdown.setOnItemClickListener { _, _, index, _ ->
                selectedGender = index
                viewModel.validateGender(selectedGender)
            }

            buttonFinish.setOnClickListener {
                viewModel.validateDoB(datePicker.selection)
                viewModel.validateGender(selectedGender)
                triggerPageValidation()
                if (viewModel.validationErrors.value!!.isEmpty()) {
                    val firstName = firstNameText.text.toString().trim()
                    val lastName = lastNameText.text.toString().trim()
                    val dateOfBirth = datePicker.selection
                    val phoneNumber = phoneNumberText.text.toString().trim()

                    viewModel.storePersonalData(
                        firstName,
                        lastName,
                        selectedGender,
                        dateOfBirth,
                        phoneNumber
                    )

                    // Navigate and register
                    viewModel.registerUser()
                }
            }
        }
    }

    private fun triggerPageValidation() {
        with(binding) {
            firstNameText.text = firstNameText.text
            lastNameText.text = lastNameText.text
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
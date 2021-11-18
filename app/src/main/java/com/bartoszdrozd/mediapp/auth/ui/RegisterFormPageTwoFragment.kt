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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.auth.dtos.RegisterUserDTO
import com.bartoszdrozd.mediapp.auth.models.AuthErrorCode
import com.bartoszdrozd.mediapp.auth.viewmodels.RegisterViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentRegisterFormPageTwoBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class RegisterFormPageTwoFragment : Fragment() {
    private val viewModel: RegisterViewModel by hiltNavGraphViewModels(R.id.nav_graph_register)
    private lateinit var navController: NavController
    private var _binding: FragmentRegisterFormPageTwoBinding? = null
    private val binding get() = _binding!!
    private lateinit var datePicker: MaterialDatePicker<Long>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterFormPageTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        buildDatePicker()
        setListeners()
    }

    private fun getErrorString(error: AuthErrorCode?): String? {
        return if (error != null) {
            resources.getString(error.messageResId)
        } else {
            null
        }
    }

    private fun buildDatePicker() {
        // Make only dates from today and backwards selectable.
        val datesTodayAndBackwardsConstraint = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.before(MaterialDatePicker.todayInUtcMilliseconds()))
            .build()

        datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date")
            .setCalendarConstraints(datesTodayAndBackwardsConstraint)
            .build()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        val items = resources.getStringArray(R.array.sexes)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.sexDropdown.setAdapter(adapter)

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
            }
        }

        with(viewModel) {
            firstNameError.observe(viewLifecycleOwner, { error ->
                binding.firstName.error = getErrorString(error)
            })

            lastNameError.observe(viewLifecycleOwner, { error ->
                binding.lastName.error = getErrorString(error)
            })

            sexError.observe(viewLifecycleOwner, { error ->
                binding.sex.error = getErrorString(error)
            })

            dateOfBirthError.observe(viewLifecycleOwner, { error ->
                binding.dateOfBirth.error = getErrorString(error)
            })

            genericError.observe(viewLifecycleOwner, { error ->
                binding.errorBox.text = getErrorString(error)
            })
        }

        with(binding) {
            firstNameText.doAfterTextChanged { firstName ->
                viewModel.validateFirstName(firstName.toString().trim())
            }

            lastNameText.doAfterTextChanged { lastName ->
                viewModel.validateLastName(lastName.toString().trim())
            }

            // Listen to sex dropdown changes
            var selectedSex = -1
            sexDropdown.setOnItemClickListener { _, _, index, _ ->
                selectedSex = index
            }

            buttonFinish.setOnClickListener {
                val firstName = firstNameText.text.toString().trim()
                val lastName = lastNameText.text.toString().trim()
                val dateOfBirth = datePicker.selection
                val phoneNumber = phoneNumberText.text.toString().trim()

                viewModel.validateFirstName(firstName)
                viewModel.validateLastName(lastName)
                viewModel.validateSex(selectedSex)
                viewModel.validateDateOfBirth(dateOfBirth)

                if (viewModel.isPersonalDetailsPageValid) {
                    viewModel.savePersonalDetails(
                        RegisterUserDTO(
                            firstName = firstName,
                            lastName = lastName,
                            // Save the date as seconds (DatePicker returns milliseconds)
                            dateOfBirth = TimeUnit.MILLISECONDS.toSeconds(dateOfBirth!!),
                            sex = selectedSex,
                            phoneNumber = phoneNumber
                        )
                    )
                    viewModel.registerUser()
                }
            }

            viewModel.registerSuccessEvent.onEach {
                // Navigate if registering succeeded
                if (it == 1) {
                    navController.navigate(R.id.action_global_register_to_dashboard)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
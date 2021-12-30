package com.bartoszdrozd.mediapp.dashboard.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.dashboard.viewmodels.InsuranceCompanyCardViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentInsuranceCompanyCardBinding
import com.bartoszdrozd.mediapp.messaging.ui.MessageInsuranceCompanyDialog
import com.bartoszdrozd.mediapp.utils.doAfterConfirmation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class InsuranceCompanyCardFragment : Fragment() {
    private val viewModel: InsuranceCompanyCardViewModel by viewModels()
    private var _binding: FragmentInsuranceCompanyCardBinding? = null
    private val binding get() = _binding!!

    private var showContactButtons: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInsuranceCompanyCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        val argsArray =
            context.obtainStyledAttributes(attrs, R.styleable.InsuranceCompanyCard_MembersInjector)
        if (argsArray.hasValue(R.styleable.InsuranceCompanyCard_MembersInjector_showContactButtons)) {
            showContactButtons =
                argsArray.getBoolean(
                    R.styleable.InsuranceCompanyCard_MembersInjector_showContactButtons,
                    false
                )
        }
        argsArray.recycle()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            contactButtonsContainer.visibility = if (showContactButtons) VISIBLE else GONE

            viewModel.insuranceCompany.observe(viewLifecycleOwner, { company ->
                if (company != null) {
                    insuranceCompanyCardContent.visibility = VISIBLE
                    noInsuranceCompanyCard.visibility = GONE
                    companyName.text =
                        resources.getString(R.string.name_string, company.name, "")

                    if (!company.logo.isNullOrBlank()) {
                        val imageBytes = Base64.getDecoder().decode(company.logo)
                        val decodedImage =
                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        logoImage.setImageBitmap(decodedImage)
                    } else {
                        logoImage.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.default_gp_photo,
                                null
                            )
                        )
                    }
                } else {
                    insuranceCompanyCardContent.visibility = GONE
                    noInsuranceCompanyCard.visibility = VISIBLE
                }
            })

            chooseInsuranceCompanyButton.setOnClickListener {
                findNavController().navigate(R.id.action_dashboard_to_insurancePicker)
            }

            callInsuranceButton.setOnClickListener {
                doAfterConfirmation { dialInsuranceCompany() }
            }

            buyInsuranceButton.setOnClickListener {
                findNavController().navigate(R.id.action_dashboard_to_insurancePolicyList)
            }

            messageInsuranceButton.setOnClickListener {
                val fragManager = parentFragmentManager
                val newFragment = MessageInsuranceCompanyDialog()
                newFragment.show(fragManager, "insuranceDialog")
            }
        }
    }

    private fun dialInsuranceCompany() {
        val dialIntent =
            Intent(
                Intent.ACTION_DIAL,
                Uri.fromParts("tel", viewModel.insuranceCompany.value!!.phoneNumber, null)
            )
        dialIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(dialIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
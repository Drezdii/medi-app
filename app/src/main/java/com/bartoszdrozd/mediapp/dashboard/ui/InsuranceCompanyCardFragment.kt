package com.bartoszdrozd.mediapp.dashboard.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInsuranceCompanyCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.insuranceCompany.observe(viewLifecycleOwner, { company ->
            if (company != null) {
                binding.insuranceCompanyCardContent.visibility = View.VISIBLE
                binding.companyName.text =
                    resources.getString(R.string.name_string, company.name, "")

                if (!company.logo.isNullOrBlank()) {
                    val imageBytes = Base64.getDecoder().decode(company.logo)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    binding.logoImage.setImageBitmap(decodedImage)
                }
            }
        })

        binding.callInsuranceButton.setOnClickListener {
            doAfterConfirmation { dialInsuranceCompany() }
        }

        binding.messageInsuranceButton.setOnClickListener {
            val fragManager = parentFragmentManager
            val newFragment = MessageInsuranceCompanyDialog()
            newFragment.show(fragManager, "insuranceDialog")
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
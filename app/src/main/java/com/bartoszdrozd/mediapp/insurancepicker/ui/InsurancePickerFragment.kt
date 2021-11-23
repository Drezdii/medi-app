package com.bartoszdrozd.mediapp.insurancepicker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bartoszdrozd.mediapp.databinding.FragmentInsurancePickerBinding
import com.bartoszdrozd.mediapp.insurancepicker.adapters.InsuranceCompanyAdapter
import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany

class InsurancePickerFragment : Fragment() {
    private var _binding: FragmentInsurancePickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInsurancePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val test = listOf(
            InsuranceCompany("Company 1", "company1@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 2", "company2@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 3", "company3@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 4", "company4@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 3", "company3@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 3", "company3@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 3", "company3@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 3", "company3@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 3", "company3@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 3", "company3@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 3", "company3@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 3", "company3@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 3", "company3@test.comn", "013921932", "logo in base64"),
            InsuranceCompany("Company 3", "company3@test.comn", "013921932", "logo in base64"),
        )

        val adapter = InsuranceCompanyAdapter()

        adapter.submitList(test)

        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.bartoszdrozd.mediapp.insurancepicker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentInsurancePolicyListBinding
import com.bartoszdrozd.mediapp.insurancepicker.adapters.InsurancePolicyAdapter
import com.bartoszdrozd.mediapp.insurancepicker.models.InsurancePolicy
import com.bartoszdrozd.mediapp.insurancepicker.viewmodels.InsurancePolicyListViewModel
import com.bartoszdrozd.mediapp.utils.DialogConfig
import com.bartoszdrozd.mediapp.utils.doAfterConfirmation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class InsurancePolicyListFragment : Fragment() {
    private val viewModel: InsurancePolicyListViewModel by viewModels()
    private var _binding: FragmentInsurancePolicyListBinding? = null
    val binding get() = _binding!!
    private lateinit var dialog: androidx.appcompat.app.AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInsurancePolicyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = MaterialAlertDialogBuilder(requireContext())
            .setCancelable(false)
            .setView(R.layout.loading_dialog)
            .create()

        val adapter = InsurancePolicyAdapter()

        adapter.setOnItemClickListener(this::buyPolicy)

        binding.recyclerView.adapter = adapter

        viewModel.policies.observe(viewLifecycleOwner, { policies ->
            adapter.submitList(policies)
        })

        viewModel.hasFinishedProcessing.onEach {
            val toastText = when (it) {
                1 -> resources.getString(R.string.payment_success)
                2 -> resources.getString(R.string.balance_too_low)
                3 -> resources.getString(R.string.generic_error)
                4 -> resources.getString(R.string.no_wallet)
                else -> resources.getString(R.string.generic_error)
            }

            dialog.dismiss()
            Toast.makeText(requireContext(), toastText, Toast.LENGTH_LONG).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun buyPolicy(policy: InsurancePolicy) {
        val config = DialogConfig(
            resources.getString(R.string.cancel),
            resources.getString(R.string.buy),
            resources.getString(R.string.confirm_payment),
            resources.getString(R.string.confirm_payment_message)
        )

        doAfterConfirmation(config) {
            viewModel.buyPolicy(policy)
            dialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
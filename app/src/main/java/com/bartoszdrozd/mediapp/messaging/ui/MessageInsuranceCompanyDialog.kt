package com.bartoszdrozd.mediapp.messaging.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.DialogMessageInsuranceCompanyBinding
import com.bartoszdrozd.mediapp.messaging.viewmodels.MessagingViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MessageInsuranceCompanyDialog : DialogFragment() {
    private val viewModel: MessagingViewModel by viewModels()
    private var _binding: DialogMessageInsuranceCompanyBinding? = null
    val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogMessageInsuranceCompanyBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.message_your_insurance_company))
            .setView(binding.root)
            .setNegativeButton(resources.getString(android.R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.send_message), null)
            .create()

        return dialog
    }

    private fun sendMessage() {
        val message = binding.messageText.text.toString().trim()

        if (message.isBlank()) {
            binding.messageText.error = resources.getString(R.string.message_empty)
            return
        }

        viewModel.sendMessageToInsuranceCompany(message)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.setCanceledOnTouchOutside(false)
        binding.messageText.error = null

        (dialog as androidx.appcompat.app.AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener {
                sendMessage()
            }

        dialog?.window?.setDimAmount(0.90f)

        (dialog as androidx.appcompat.app.AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)

        viewModel.sendSuccessInsurance.onEach {
            // Dismiss if sending succeeded
            if (it == 1) {
                Toast.makeText(context, resources.getText(R.string.message_sent), Toast.LENGTH_LONG)
                    .show()
                dialog?.dismiss()
            } else {
                Toast.makeText(
                    context,
                    resources.getText(R.string.generic_error),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }.launchIn(this.lifecycleScope)

    }
}
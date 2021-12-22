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
        val message = binding.messageText.text.toString()

        if (message.isBlank()) {
            binding.message.error = resources.getString(R.string.message_empty)
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
        binding.message.error = ""

        (dialog as androidx.appcompat.app.AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener {
                sendMessage()
            }

        (dialog as androidx.appcompat.app.AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)

        viewModel.sendSuccessInsurance.onEach {
            // Navigate if sending succeeded
            if (it == 1) {
                dialog?.dismiss()
            } else {
                val text = resources.getText(R.string.generic_error)
                val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
                toast.show()
            }
        }.launchIn(this.lifecycleScope)

    }
}
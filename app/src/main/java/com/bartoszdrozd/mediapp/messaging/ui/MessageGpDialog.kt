package com.bartoszdrozd.mediapp.messaging.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.DialogMessageGpBinding
import com.bartoszdrozd.mediapp.messaging.viewmodels.MessagingViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MessageGpDialog : DialogFragment() {
    private val viewModel: MessagingViewModel by viewModels()
    private var _binding: DialogMessageGpBinding? = null
    val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogMessageGpBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.message_your_gp))
            .setView(binding.root)
            .setNegativeButton(resources.getString(android.R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.send_message), null)
//            .setPositiveButtonIcon(
//                ResourcesCompat.getDrawable(
//                    resources,
//                    R.drawable.ic_heart_icon,
//                    null
//                )
//            )
            .create()

        return dialog
    }

    private fun sendMessage() {
        val message = binding.messageText.text.toString().trim()

        if (message.isBlank()) {
            binding.messageText.error = resources.getString(R.string.message_empty)
            return
        }

        viewModel.sendMessageToGp(message)
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCanceledOnTouchOutside(false)

        dialog?.window?.setDimAmount(0.90f)

        (dialog as androidx.appcompat.app.AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener {
                sendMessage()
            }

        viewModel.sendSuccessGp.onEach {
            // Dismiss if sending succeeded
            if (it == 1) {
                val text = resources.getText(R.string.message_sent)
                val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
                toast.show()
                dialog?.dismiss()
            } else {
                val text = resources.getText(R.string.generic_error)
                val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
                toast.show()
            }
        }.launchIn(this.lifecycleScope)

    }
}
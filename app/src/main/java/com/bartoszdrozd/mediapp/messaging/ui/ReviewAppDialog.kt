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
import com.bartoszdrozd.mediapp.databinding.DialogReviewAppBinding
import com.bartoszdrozd.mediapp.messaging.viewmodels.MessagingViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ReviewAppDialog : DialogFragment() {
    private val viewModel: MessagingViewModel by viewModels()
    private var _binding: DialogReviewAppBinding? = null
    val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogReviewAppBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.feedback))
            .setView(binding.root)
            .setNegativeButton(resources.getString(android.R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.send), null)
            .create()

        return dialog
    }

    private fun sendMessage() {
        val message = binding.messageText.text.toString()
        val rating = when (binding.appRating.checkedButtonId) {
            R.id.rating_1 -> 1
            R.id.rating_2 -> 2
            R.id.rating_3 -> 3
            R.id.rating_4 -> 4
            R.id.rating_5 -> 5
            else -> 5
        }
        viewModel.sendFeedback(rating, message)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.setCanceledOnTouchOutside(false)

        dialog?.window?.setDimAmount(0.90f)

        (dialog as androidx.appcompat.app.AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener {
                sendMessage()
            }

        // Scroll to the bottom when the message box has focus
        binding.messageText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                with(binding.scrollView) {
                    postDelayed({
                        smoothScrollTo(0, Int.MAX_VALUE)
                    }, 0)
                }
            }
        }

        viewModel.sendSuccessFeedback.onEach {
            // Dismiss if sending succeeded
            if (it == 1) {
                val text = resources.getText(R.string.thanks_for_feedback)
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
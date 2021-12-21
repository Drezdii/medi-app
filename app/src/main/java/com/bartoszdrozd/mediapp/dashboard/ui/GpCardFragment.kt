package com.bartoszdrozd.mediapp.dashboard.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.dashboard.viewmodels.GpCardViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentGpCardBinding
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.messaging.ui.MessageGpDialog
import com.bartoszdrozd.mediapp.utils.doAfterConfirmation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GpCardFragment : Fragment() {
    private val viewModel: GpCardViewModel by viewModels()
    private var _binding: FragmentGpCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGpCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gp.observe(viewLifecycleOwner, { gp ->
            if (gp != null) {
                binding.gpCardContent.visibility = View.VISIBLE
                binding.name.text =
                    resources.getString(R.string.name_string, gp.firstName, gp.lastName)

                if (!gp.picture.isNullOrBlank()) {
                    val imageBytes = Base64.getDecoder().decode(gp.picture)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    binding.avatarImage.setImageBitmap(decodedImage)
                }
            } else {
                binding.gpCardContent.visibility = INVISIBLE
            }
        })

        binding.callGpButton.setOnClickListener {
            doAfterConfirmation { dialGPNumber() }
        }

        binding.messageGpButton.setOnClickListener {
            val fragManager = parentFragmentManager
            val newFragment = MessageGpDialog()
            newFragment.show(fragManager, "dialog")
        }
    }

    fun getGp(): GeneralPractitioner? {
        return viewModel.gp.value
    }

    private fun dialGPNumber() {
        val dialIntent =
            Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", viewModel.gp.value!!.phoneNumber, null))
        dialIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(dialIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
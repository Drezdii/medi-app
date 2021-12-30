package com.bartoszdrozd.mediapp.dashboard.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.dashboard.viewmodels.GpCardViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentGpCardBinding
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

    private var showContactButtons: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGpCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        val argsArray =
            context.obtainStyledAttributes(attrs, R.styleable.InsuranceCompanyCard_MembersInjector)
        if (argsArray.hasValue(R.styleable.InsuranceCompanyCard_MembersInjector_showContactButtons)) {
            showContactButtons =
                argsArray.getBoolean(
                    R.styleable.InsuranceCompanyCard_MembersInjector_showContactButtons, false
                )
        }
        argsArray.recycle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            contactButtonsContainer.visibility = if (showContactButtons) VISIBLE else GONE

            viewModel.gp.observe(viewLifecycleOwner, { gp ->
                if (gp != null) {
                    gpCardContent.visibility = VISIBLE
                    noGpCard.visibility = INVISIBLE
                    name.text =
                        resources.getString(R.string.name_string, gp.firstName, gp.lastName)

                    if (!gp.picture.isNullOrBlank()) {
                        val imageBytes = Base64.getDecoder().decode(gp.picture)
                        val decodedImage =
                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        avatarImage.setImageBitmap(decodedImage)
                    }
                } else {
                    gpCardContent.visibility = INVISIBLE
                    noGpCard.visibility = VISIBLE
                }
            })

            chooseGpButton.setOnClickListener {
                findNavController().navigate(R.id.action_dashboard_to_gpPicker)
            }

            callGpButton.setOnClickListener {
                doAfterConfirmation { dialGPNumber() }
            }

            messageGpButton.setOnClickListener {
                val fragManager = parentFragmentManager
                val newFragment = MessageGpDialog()
                newFragment.show(fragManager, "gpDialog")
            }
        }
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
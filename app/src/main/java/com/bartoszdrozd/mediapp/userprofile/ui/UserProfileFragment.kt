package com.bartoszdrozd.mediapp.userprofile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentUserProfileBinding
import com.bartoszdrozd.mediapp.messaging.ui.ReviewAppDialog
import com.bartoszdrozd.mediapp.userprofile.viewmodels.UserProfileViewModel
import com.bartoszdrozd.mediapp.utils.doAfterConfirmation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.web3j.utils.Convert
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        viewModel.overallHealthScore.observe(viewLifecycleOwner, { healthScore ->
            val textResource = if (healthScore.isNaN()) {
                R.string.overall_health_no_score
            } else if (healthScore < 0.25) {
                R.string.overall_health_very_good
            } else if (healthScore < 0.5) {
                R.string.overall_health_good
            } else if (healthScore >= 0.5 && healthScore < 0.75) {
                R.string.overall_health_bad
            } else {
                R.string.overall_health_very_bad
            }

            binding.arc.text = resources.getString(textResource)
            binding.arc.arcValue = (1 - healthScore).coerceAtLeast(0.05f)
        })

        viewModel.coinsBalance.observe(viewLifecycleOwner, { balance ->
            val balanceMDC = Convert.fromWei(balance.mediCoin.toString(), Convert.Unit.ETHER)
            binding.medicoinBalance.text = String.format(Locale.getDefault(), "%f", balanceMDC)

            val balanceEther = Convert.fromWei(balance.ether.toString(), Convert.Unit.ETHER)
            binding.etherBalance.text = String.format(Locale.getDefault(), "%f", balanceEther)

            binding.walletAddress.text = balance.wallet
        })

        viewModel.showSavedToast.onEach {
            if (it == 1) {
                val text = resources.getText(R.string.saved_success)
                val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
                toast.show()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.userDetails.observe(viewLifecycleOwner, { details ->
            with(binding) {
                name.text =
                    resources.getString(R.string.name_string, details.firstName, details.lastName)
//                dateOfBirth.text = Instant.ofEpochSecond(details.dateOfBirth).atZone(
//                    ZoneId.systemDefault()
//                ).toLocalDate().toString()

                unlinkGpButton.visibility = if (details.gpId != null) View.VISIBLE else View.GONE
                unlinkInsuranceCompanyButton.visibility =
                    if (details.insuranceCompanyId != null) View.VISIBLE else View.GONE
            }
        })

        binding.logoutButton.setOnClickListener {
            viewModel.signOut()
        }

        binding.changeGpButton.setOnClickListener {
            navController.navigate(R.id.action_userProfile_to_gpPicker)
        }

        binding.changeInsuranceCompanyButton.setOnClickListener {
            navController.navigate(R.id.action_userProfile_to_insurancePicker)
        }

        binding.unlinkGpButton.setOnClickListener {
            doAfterConfirmation { viewModel.unlinkGp() }
        }

        binding.changePasswordButton.setOnClickListener { }

        binding.unlinkInsuranceCompanyButton.setOnClickListener {
            doAfterConfirmation { viewModel.unlinkInsuranceCompany() }
        }

        binding.changePasswordButton.setOnClickListener {
            val details = viewModel.userDetails.value
            val usersName = "${details?.firstName} ${details?.lastName}"
            val action = UserProfileFragmentDirections.actionChangePassword(usersName)

            navController.navigate(action)
        }

        binding.feedbackButton.setOnClickListener {
            val fragManager = parentFragmentManager
            val newFragment = ReviewAppDialog()
            newFragment.show(fragManager, "reviewAppDialog")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
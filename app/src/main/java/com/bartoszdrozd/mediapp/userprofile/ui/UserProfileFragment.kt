package com.bartoszdrozd.mediapp.userprofile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentUserProfileBinding
import com.bartoszdrozd.mediapp.userprofile.viewmodels.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.Instant
import java.time.ZoneId

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

        binding.logoutButton.setOnClickListener {
            viewModel.signOut()
        }

        binding.changeGpButton.setOnClickListener {
            navController.navigate(R.id.action_userProfile_to_gpPicker)
        }

        viewModel.userDetails.observe(viewLifecycleOwner, { details ->
            with(binding) {
                name.text =
                    resources.getString(R.string.name_string, details.firstName, details.lastName)
                dateOfBirth.text = Instant.ofEpochSecond(details.dateOfBirth).atZone(
                    ZoneId.systemDefault()
                ).toLocalDate().toString()
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
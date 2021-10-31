package com.bartoszdrozd.mediapp.dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.dashboard.viewmodels.DashboardViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            heartButton.setOnClickListener {
                navController.navigate(R.id.action_dashboard_to_heartForm)
            }

            diabetesButton.setOnClickListener {
                navController.navigate(R.id.action_dashboard_to_diabetesForm)
            }

            alzheimersButton.setOnClickListener {
                navController.navigate(R.id.action_dashboard_to_alzheimersForm)
            }

            logoutButton.setOnClickListener {
                // Temporary
                FirebaseAuth.getInstance().signOut()
            }
        }

        viewModel.name.observe(viewLifecycleOwner, { name ->
            binding.pageTitle.text = resources.getString(R.string.welcome_message, name!!)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
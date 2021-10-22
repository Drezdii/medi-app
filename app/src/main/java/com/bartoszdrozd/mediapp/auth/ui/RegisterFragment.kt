package com.bartoszdrozd.mediapp.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.auth.viewmodels.RegisterViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by navGraphViewModels(R.id.nav_graph_register)
    private lateinit var navController: NavController
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setListeners()
    }

    private fun setListeners() {
        with(binding.multistepper) {
            pager.adapter = RegisterFormPageAdapter(this@RegisterFragment)
            maxStep = pager.adapter?.itemCount!!
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class RegisterFormPageAdapter(frag: Fragment) : FragmentStateAdapter(frag) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> RegisterFormPageOneFragment()
                1 -> Fragment(R.layout.fragment_register_form_page_two)
                else -> Fragment()
            }
        }
    }
}
package com.bartoszdrozd.mediapp.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bartoszdrozd.mediapp.databinding.FragmentRegisterFormPageTwoBinding

class RegisterFormPageTwoFragment : Fragment() {
    private var _binding: FragmentRegisterFormPageTwoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterFormPageTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
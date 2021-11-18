package com.bartoszdrozd.mediapp.gppicker.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bartoszdrozd.mediapp.databinding.FragmentGpPickerBinding
import com.bartoszdrozd.mediapp.gppicker.adapters.GpAdapter
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import com.bartoszdrozd.mediapp.gppicker.viewmodels.GpPickerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GpPickerFragment : Fragment() {
    private val viewModel: GpPickerViewModel by viewModels()
    private var _binding: FragmentGpPickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGpPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TEST
        viewModel.loadGPs()

        // TEST
        val gpAdapter = GpAdapter()
        gpAdapter.setOnItemClickListener {
            viewModel.selectGP(it)
        }

        viewModel.selectedGP.observe(viewLifecycleOwner, {
            gpAdapter.selectedGP = it
            gpAdapter.notifyDataSetChanged()
        })
        binding.recyclerView.adapter = gpAdapter

        viewModel.generalPractitioners.observe(viewLifecycleOwner, { gps ->
            gpAdapter.submitList(gps)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
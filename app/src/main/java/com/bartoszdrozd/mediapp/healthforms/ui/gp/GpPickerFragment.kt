package com.bartoszdrozd.mediapp.healthforms.ui.gp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bartoszdrozd.mediapp.databinding.FragmentGpPickerBinding
import com.bartoszdrozd.mediapp.healthforms.adapters.GpAdapter
import com.bartoszdrozd.mediapp.healthforms.models.gp.GeneralPractitioner

class GpPickerFragment : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gpAdapter = GpAdapter()

        val testList = mutableListOf(
            GeneralPractitioner("Test", "1", 123),
            GeneralPractitioner("Test", "2", 456),
            GeneralPractitioner("Test", "1", 789),
        )

        binding.recyclerView.adapter = gpAdapter
        gpAdapter.submitList(testList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.bartoszdrozd.mediapp.gppicker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bartoszdrozd.mediapp.databinding.FragmentGpPickerBinding
import com.bartoszdrozd.mediapp.gppicker.adapters.GpAdapter
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner

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
            GeneralPractitioner("Test0", "1", 123),
            GeneralPractitioner("Test1", "2", 456),
            GeneralPractitioner("Test2", "1", 789),
            GeneralPractitioner("Test3", "1", 789),
            GeneralPractitioner("Test4", "1", 789),
            GeneralPractitioner("Test5", "1", 789),
            GeneralPractitioner("Test6", "1", 789),
            GeneralPractitioner("Test7", "1", 789),
            GeneralPractitioner("Test8", "1", 789),
            GeneralPractitioner("Test9", "1", 789),
            GeneralPractitioner("Test10", "1", 789),
            GeneralPractitioner("Test11", "1", 789),
            GeneralPractitioner("Test12", "1", 789),
            GeneralPractitioner("Test13", "1", 789),
            GeneralPractitioner("Test14", "1", 789),
        )

        binding.recyclerView.adapter = gpAdapter
        gpAdapter.submitList(testList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
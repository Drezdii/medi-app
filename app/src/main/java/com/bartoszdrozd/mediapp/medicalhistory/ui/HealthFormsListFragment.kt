package com.bartoszdrozd.mediapp.medicalhistory.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bartoszdrozd.mediapp.databinding.FragmentHealthFormsListBinding
import com.bartoszdrozd.mediapp.medicalhistory.adapters.HealthFormsHistoryAdapter
import com.bartoszdrozd.mediapp.medicalhistory.viewmodels.HealthFormsHistoryViewModel
import com.bartoszdrozd.mediapp.utils.DiseaseType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HealthFormsListFragment : Fragment() {
    private val viewModel: HealthFormsHistoryViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private var _binding: FragmentHealthFormsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var diseaseType: DiseaseType
    
    companion object {
        fun newInstance(type: DiseaseType): HealthFormsListFragment {
            val args = Bundle().apply {
                putSerializable("type", type)
            }

            val fragment = HealthFormsListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        diseaseType = arguments?.getSerializable("type") as DiseaseType
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHealthFormsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HealthFormsHistoryAdapter()

        binding.recyclerView.adapter = adapter

        viewModel.forms.observe(viewLifecycleOwner, { formsList ->
            adapter.submitList(formsList.filter { it.diseaseType == diseaseType })
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
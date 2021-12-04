package com.bartoszdrozd.mediapp.medicalhistory.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bartoszdrozd.mediapp.databinding.FragmentPredictionListBinding
import com.bartoszdrozd.mediapp.medicalhistory.adapters.PredictionHistoryAdapter
import com.bartoszdrozd.mediapp.medicalhistory.viewmodels.PredictionsHistoryViewModel
import com.bartoszdrozd.mediapp.predictions.models.PredictionType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PredictionListFragment : Fragment() {
    @ExperimentalCoroutinesApi
    private val viewModel: PredictionsHistoryViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private var _binding: FragmentPredictionListBinding? = null
    private val binding get() = _binding!!
    private lateinit var predictionType: PredictionType
    
    companion object {
        fun newInstance(type: PredictionType): PredictionListFragment {
            val args = Bundle().apply {
                putSerializable("type", type)
            }

            val fragment = PredictionListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        predictionType = arguments?.getSerializable("type") as PredictionType
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPredictionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PredictionHistoryAdapter()

        binding.recyclerView.adapter = adapter

        viewModel.predictions.observe(viewLifecycleOwner, { predictionList ->
            adapter.submitList(predictionList.filter { it.predictionType == predictionType })
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
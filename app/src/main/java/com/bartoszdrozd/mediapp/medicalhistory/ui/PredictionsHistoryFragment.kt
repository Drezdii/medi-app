package com.bartoszdrozd.mediapp.medicalhistory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentPredictionsHistoryBinding
import com.bartoszdrozd.mediapp.predictions.models.PredictionType
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PredictionsHistoryFragment : Fragment() {
    private var _binding: FragmentPredictionsHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPredictionsHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.tabLayout
        val viewPager = binding.pager
        viewPager.adapter = PredictionAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val res = requireContext().resources
            tab.text = when (position) {
                0 -> res.getString(R.string.heart)
                1 -> res.getString(R.string.alzheimers)
                2 -> res.getString(R.string.diabetes)
                else -> res.getString(R.string.generic_error)
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@ExperimentalCoroutinesApi
class PredictionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val predictionType = when (position) {
            0 -> PredictionType.HEART
            1 -> PredictionType.ALZHEIMERS
            2 -> PredictionType.DIABETES
            else -> PredictionType.HEART
        }
        return PredictionListFragment.newInstance(predictionType)
    }
}
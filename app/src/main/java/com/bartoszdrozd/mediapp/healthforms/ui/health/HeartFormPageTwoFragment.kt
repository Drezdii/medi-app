package com.bartoszdrozd.mediapp.healthforms.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentHeartFormPageTwoBinding
import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.viewmodels.HeartFormViewModel
import com.bartoszdrozd.mediapp.utils.FormFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeartFormPageTwoFragment : FormFragment() {
    private val formViewModel: HeartFormViewModel by hiltNavGraphViewModels(R.id.nav_graph_heart_form)
    private var _binding: FragmentHeartFormPageTwoBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeartFormPageTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        setListeners()
    }

    private fun setListeners() {
        with(binding) {

            formViewModel.generalError.observe(viewLifecycleOwner, { error ->
                errorBox.text = getErrorString(error)
            })

            buttonSave.setOnClickListener {
                val chestPainType = when (chestPain.checkedButtonId) {
                    R.id.chest_pain_typical -> 0
                    R.id.chest_pain_atypical -> 1
                    R.id.chest_pain_asymptotic -> 2
                    R.id.chest_pain_nonanginal -> 3
                    else -> -1
                }

                val exerciseAngina = when (exerciseAngina.checkedButtonId) {
                    R.id.no_button -> 0
                    R.id.yes_button -> 1
                    else -> -1
                }

                val peakST = when (peakStSegment.checkedButtonId) {
                    R.id.upsloping_st_button -> 0
                    R.id.flat_st_button -> 1
                    R.id.downsloping_st_button -> 2
                    else -> -1
                }

                val majorVessels = when (majorVessels.checkedButtonId) {
                    R.id.major_vessels_zero_button -> 0
                    R.id.major_vessels_one_button -> 1
                    R.id.major_vessels_two_button -> 2
                    R.id.major_vessels_three_button -> 3
                    else -> -1
                }

                val thalassemia = when (thalassemia.checkedButtonId) {
                    R.id.thalassemia_normal_button -> 0
                    R.id.thalassemia_fixed_button -> 1
                    R.id.thalassemia_reversible_button -> 2
                    else -> -1
                }

                formViewModel.saveForm(
                    HeartFormDTO(
                        chestPainType = chestPainType,
                        exerciseInducedAngina = exerciseAngina,
                        peakSTSegment = peakST,
                        majorVessels = majorVessels,
                        thalassemia = thalassemia
                    )
                )
            }
        }
    }
}
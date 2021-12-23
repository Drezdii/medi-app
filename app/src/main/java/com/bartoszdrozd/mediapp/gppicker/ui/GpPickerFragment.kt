package com.bartoszdrozd.mediapp.gppicker.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.FragmentGpPickerBinding
import com.bartoszdrozd.mediapp.gppicker.adapters.GpAdapter
import com.bartoszdrozd.mediapp.gppicker.viewmodels.GpPickerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GpPickerFragment : Fragment() {
    private val viewModel: GpPickerViewModel by viewModels()
    private var _binding: FragmentGpPickerBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private var isDirty = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGpPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        val saveItem = menu.findItem(R.id.action_save)
        saveItem.isVisible = isDirty
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                viewModel.saveSelection()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        val gpAdapter = GpAdapter()
        gpAdapter.setOnItemClickListener {
            viewModel.selectGP(it)
        }

        viewModel.selectedGP.observe(viewLifecycleOwner, {
            gpAdapter.selectedGP = it
            gpAdapter.notifyDataSetChanged()
        })

        viewModel.isDirty.observe(viewLifecycleOwner, {
            isDirty = it
            requireActivity().invalidateOptionsMenu()
        })

        binding.recyclerView.adapter = gpAdapter

        viewModel.generalPractitioners.observe(viewLifecycleOwner, { gps ->
            gpAdapter.submitList(gps)
        })

        viewModel.savingCompletedEvent.onEach {
            // Navigate if saving succeeded
            if (it == 1) {
                val text = resources.getText(R.string.saved_success)
                val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
                toast.show()

                navController.popBackStack()
            } else {
                val text = resources.getText(R.string.generic_error)
                val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
                toast.show()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
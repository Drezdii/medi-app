package com.bartoszdrozd.mediapp.insurancepicker.ui

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
import com.bartoszdrozd.mediapp.databinding.FragmentInsurancePickerBinding
import com.bartoszdrozd.mediapp.insurancepicker.adapters.InsuranceCompanyAdapter
import com.bartoszdrozd.mediapp.insurancepicker.viewmodels.InsuranceCompanyPickerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class InsuranceCompanyPickerFragment : Fragment() {
    private val viewModel: InsuranceCompanyPickerViewModel by viewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentInsurancePickerBinding? = null
    private val binding get() = _binding!!
    private var isDirty = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInsurancePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_cancel_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                viewModel.saveSelection()
                true
            }
            R.id.action_cancel -> {
                viewModel.selectCompany(null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        val cancelItem = menu.findItem(R.id.action_cancel)
        val saveItem = menu.findItem(R.id.action_save)

        cancelItem.isVisible = isDirty
        saveItem.isVisible = isDirty
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()


        val adapter = InsuranceCompanyAdapter()

        adapter.setOnItemClickListener {
            viewModel.selectCompany(it)
        }

        binding.cancelButton.setOnClickListener {
            viewModel.selectCompany(null)
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveSelection()
        }

        viewModel.companies.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.selectedCompany.observe(viewLifecycleOwner, {
            adapter.selectedCompany = it
            adapter.notifyDataSetChanged()
        })

        viewModel.isDirty.observe(viewLifecycleOwner, { dirtyStatus ->
            isDirty = dirtyStatus
            // Refresh the menu
            requireActivity().invalidateOptionsMenu()
        })

        viewModel.savingCompletedEvent.onEach {
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

        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

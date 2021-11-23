package com.bartoszdrozd.mediapp.insurancepicker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.InsuranceItemBinding
import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany

class InsuranceCompanyAdapter :
    ListAdapter<InsuranceCompany, InsuranceCompanyAdapter.ViewHolder>(InsuranceDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = InsuranceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), null)
    }

    inner class ViewHolder(val binding: InsuranceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(company: InsuranceCompany, selectedCompany: InsuranceCompany?) {
            with(binding) {
                name.text = company.name
                logo.setImageDrawable(ResourcesCompat.getDrawable(root.resources, R.drawable.default_gp_photo, null))
            }
        }
    }
}

object InsuranceDiffCallback : DiffUtil.ItemCallback<InsuranceCompany>() {
    override fun areItemsTheSame(oldItem: InsuranceCompany, newItem: InsuranceCompany): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: InsuranceCompany, newItem: InsuranceCompany): Boolean =
        oldItem.uid == newItem.uid
}
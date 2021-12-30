package com.bartoszdrozd.mediapp.insurancepicker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.InsurancePolicyItemBinding
import com.bartoszdrozd.mediapp.insurancepicker.models.InsurancePolicy

class InsurancePolicyAdapter :
    ListAdapter<InsurancePolicy, InsurancePolicyAdapter.ViewHolder>(InsurancePolicyDiffCallback) {
    private var _itemListener: ((InsurancePolicy) -> Unit?)? = null

    fun setOnItemClickListener(listener: (company: InsurancePolicy) -> Unit) {
        _itemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            InsurancePolicyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: InsurancePolicyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(policy: InsurancePolicy) {
            with(binding) {
                name.text = policy.name
                description.text = policy.description
                length.text =
                    root.resources.getQuantityString(
                        R.plurals.months_plural,
                        policy.lengthMonths,
                        policy.lengthMonths
                    )
                buyButton.text = root.resources.getString(R.string.buy_with_price, policy.price)
                buyButton.setOnClickListener {
                    _itemListener?.invoke(policy)
                }
            }
        }
    }
}

object InsurancePolicyDiffCallback : DiffUtil.ItemCallback<InsurancePolicy>() {
    override fun areItemsTheSame(oldItem: InsurancePolicy, newItem: InsurancePolicy): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: InsurancePolicy, newItem: InsurancePolicy): Boolean =
        oldItem.uid == newItem.uid
}
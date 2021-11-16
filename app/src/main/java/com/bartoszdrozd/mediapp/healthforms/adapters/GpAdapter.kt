package com.bartoszdrozd.mediapp.healthforms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bartoszdrozd.mediapp.databinding.GpItemBinding
import com.bartoszdrozd.mediapp.healthforms.models.gp.GeneralPractitioner

class GpAdapter :
    ListAdapter<GeneralPractitioner, GpAdapter.GpViewHolder>(GpDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GpViewHolder {
        val binding = GpItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GpViewHolder, position: Int) {
        // TEST
        val gp = getItem(position)
        holder.binding.name.text = gp.firstName + gp.lastName
        holder.binding.councilNum.text = gp.medCouncilNum.toString()
    }

    inner class GpViewHolder(val binding: GpItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}

object GpDiffCallback : DiffUtil.ItemCallback<GeneralPractitioner>() {
    override fun areItemsTheSame(
        oldItem: GeneralPractitioner,
        newItem: GeneralPractitioner
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: GeneralPractitioner,
        newItem: GeneralPractitioner
    ): Boolean = oldItem.medCouncilNum == newItem.medCouncilNum
}
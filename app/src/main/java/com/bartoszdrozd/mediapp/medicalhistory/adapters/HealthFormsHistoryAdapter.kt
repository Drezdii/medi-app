package com.bartoszdrozd.mediapp.medicalhistory.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bartoszdrozd.mediapp.databinding.HealthFormItemBinding
import com.bartoszdrozd.mediapp.medicalhistory.dtos.HealthFormEntryDTO
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HealthFormsHistoryAdapter :
    ListAdapter<HealthFormEntryDTO, HealthFormsHistoryAdapter.ViewHolder>(HealthFormDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HealthFormItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: HealthFormItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(prediction: HealthFormEntryDTO) {
            binding.date.text =
                Instant
                    .ofEpochSecond(prediction.date)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(ZoneId.systemDefault()))
        }
    }
}

object HealthFormDiffCallback : DiffUtil.ItemCallback<HealthFormEntryDTO>() {
    override fun areItemsTheSame(oldItem: HealthFormEntryDTO, newItem: HealthFormEntryDTO): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: HealthFormEntryDTO, newItem: HealthFormEntryDTO): Boolean =
        oldItem.id == newItem.id
}
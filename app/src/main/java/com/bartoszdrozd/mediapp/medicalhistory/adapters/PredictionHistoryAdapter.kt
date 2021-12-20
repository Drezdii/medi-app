package com.bartoszdrozd.mediapp.medicalhistory.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.PredictionItemBinding
import com.bartoszdrozd.mediapp.predictions.dtos.PredictionDTO
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class PredictionHistoryAdapter :
    ListAdapter<PredictionDTO, PredictionHistoryAdapter.ViewHolder>(PredictionDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PredictionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: PredictionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(prediction: PredictionDTO) {
            binding.date.text =
                Instant
                    .ofEpochSecond(prediction.date)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(ZoneId.systemDefault()))

            val predictionText: Int
            val color: Int

            if (prediction.value < 0.5) {
                color = R.color.prediction_good
                predictionText = R.string.prediction_healthy
            } else if (prediction.value >= 0.5 && prediction.value < 0.75) {
                color = R.color.prediction_warning
                predictionText = R.string.prediction_warning
            } else {
                color = R.color.prediction_serious
                predictionText = R.string.prediction_serious
            }

            binding.predictionResultText.text = binding.root.resources.getString(predictionText)
            binding.predictionCard.strokeColor = ContextCompat.getColor(binding.root.context, color)
        }
    }
}

object PredictionDiffCallback : DiffUtil.ItemCallback<PredictionDTO>() {
    override fun areItemsTheSame(oldItem: PredictionDTO, newItem: PredictionDTO): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: PredictionDTO, newItem: PredictionDTO): Boolean =
        oldItem.id == newItem.id
}
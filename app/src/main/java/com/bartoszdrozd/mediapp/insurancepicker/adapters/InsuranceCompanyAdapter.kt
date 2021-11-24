package com.bartoszdrozd.mediapp.insurancepicker.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.InsuranceItemBinding
import com.bartoszdrozd.mediapp.insurancepicker.models.InsuranceCompany
import java.util.*

class InsuranceCompanyAdapter :
    ListAdapter<InsuranceCompany, InsuranceCompanyAdapter.ViewHolder>(InsuranceDiffCallback) {
    private var _itemListener: ((InsuranceCompany) -> Unit?)? = null
    var selectedCompany: InsuranceCompany? = null

    fun setOnItemClickListener(listener: (company: InsuranceCompany) -> Unit) {
        _itemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = InsuranceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), selectedCompany)
    }

    inner class ViewHolder(val binding: InsuranceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(company: InsuranceCompany, selectedCompany: InsuranceCompany?) {
            with(binding) {
                name.text = company.name
                insuranceCard.isChecked = selectedCompany?.uid == company.uid

                if (!company.logo.isNullOrBlank()) {
                    val imageBytes = Base64.getDecoder().decode(company.logo)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    logo.setImageBitmap(decodedImage)
                } else {
                    logo.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            root.resources,
                            R.drawable.default_gp_photo,
                            null
                        )
                    )
                }

                insuranceCard.setOnClickListener {
                    _itemListener?.invoke(company)
                }
            }
        }
    }
}

object InsuranceDiffCallback : DiffUtil.ItemCallback<InsuranceCompany>() {
    override fun areItemsTheSame(oldItem: InsuranceCompany, newItem: InsuranceCompany): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: InsuranceCompany, newItem: InsuranceCompany): Boolean =
        oldItem.uid == newItem.uid
}
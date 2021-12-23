package com.bartoszdrozd.mediapp.gppicker.adapters

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.databinding.GpItemBinding
import com.bartoszdrozd.mediapp.gppicker.models.GeneralPractitioner
import java.util.*

class GpAdapter :
    ListAdapter<GeneralPractitioner, RecyclerView.ViewHolder>(GpDiffCallback) {
    private var _itemListener: ((GeneralPractitioner?) -> Unit?)? = null
    var selectedGP: GeneralPractitioner? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = GpItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return if (viewType == 0) {
            EmptyGpHolder(binding)
        } else {
            GpViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            0
        } else {
            1
        }
    }

    fun setOnItemClickListener(listener: (gp: GeneralPractitioner?) -> Unit) {
        _itemListener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            (holder as EmptyGpHolder).bind(selectedGP)
        } else {
            (holder as GpViewHolder).bind(getItem(position), selectedGP)
        }
    }

    inner class EmptyGpHolder(val binding: GpItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(selectedGP: GeneralPractitioner?) {
            with(binding) {
                name.text = root.resources.getString(R.string.unlink_gp)
                avatarContainer.visibility = INVISIBLE

                Log.d("TEST", "BIND EMPTY")
                gpCard.isChecked = selectedGP == null

                gpCard.setOnClickListener {
                    _itemListener?.invoke(null)
                }
            }
        }
    }

    inner class GpViewHolder(val binding: GpItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gp: GeneralPractitioner, selectedGP: GeneralPractitioner?) {
            with(binding) {
                Log.d("TEST", "BIND FULL")
                name.text = root.resources.getString(
                    R.string.gp_name_string,
                    gp.firstName,
                    gp.lastName
                )
                councilNum.text = gp.mcn.toString()
                medicalCenter.text = gp.medicalCenter

                if (!gp.picture.isNullOrBlank()) {
                    val imageBytes = Base64.getDecoder().decode(gp.picture)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    avatarImage.setImageBitmap(decodedImage)
                } else {
                    avatarImage.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            root.resources,
                            R.drawable.default_gp_photo,
                            null
                        )
                    )
                }

                gpCard.isChecked = selectedGP?.mcn == gp.mcn

                gpCard.setOnClickListener {
                    _itemListener?.invoke(gp)
                }
            }
        }
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
    ): Boolean = oldItem.mcn == newItem.mcn
}
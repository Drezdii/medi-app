package com.bartoszdrozd.mediapp

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.RecyclerView
import com.bartoszdrozd.mediapp.databinding.MultiStepperBinding

@Suppress("SetterBackingFieldAssignment")
class MultiStepper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = MultiStepperBinding.inflate(LayoutInflater.from(context), this)

    val pager = binding.pager
    
    init {
        binding.pager.isUserInputEnabled = false
    }

    var currentStep: Int = 1
        set(value) {
            pager.currentItem = value
            // Current step starts at 0 so we must add 1 to properly mark the blobs
            markSteps(value + 1)
            invalidate()
            requestLayout()
        }

    var maxStep: Int = -1
        set(value) {
            if (field == -1) {
                createBlobs(value)
            }
            invalidate()
            requestLayout()
        }

    private fun markSteps(step: Int) {
        binding.blobContainer.forEachIndexed { index, blob ->
            if (index < step) {
                // Mark the step as finished
                (blob.background as GradientDrawable).setColor(Color.WHITE)
            } else {
                (blob.background as GradientDrawable).setColor(Color.TRANSPARENT)
            }
        }
    }

    private fun createBlobs(num: Int) {
        for (i in 1..num) {
            val blob = ImageView(context)
            blob.background = (ContextCompat.getDrawable(context, R.drawable.step_blob))

            val params =
                LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

            params.height = 64
            params.width = 64
            params.setMargins(16, 0, 0, 0)
            blob.layoutParams = params

            binding.blobContainer.addView(blob)
        }
    }

}
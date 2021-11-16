package com.bartoszdrozd.mediapp.utils

import androidx.fragment.app.Fragment
import com.bartoszdrozd.mediapp.healthforms.models.FormErrorCode

open class FormFragment : Fragment() {
    fun getErrorString(error: FormErrorCode?): String? {
        return if (error != null) {
            resources.getString(error.messageResId)
        } else {
            null
        }
    }
}
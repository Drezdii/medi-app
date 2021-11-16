package com.bartoszdrozd.mediapp.healthforms.models

import com.bartoszdrozd.mediapp.R

enum class FormErrorCode(val messageResId: Int) {
    REQUIRED_FIELD(R.string.required_field),
    GENERIC_ERROR(R.string.generic_error)
}
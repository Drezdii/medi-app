package com.bartoszdrozd.mediapp.messaging.usecases

import com.bartoszdrozd.mediapp.utils.Result

interface ISendFeedbackUseCase {
    suspend operator fun invoke(rating: Int, message: String): Result<Unit, Unit>
}
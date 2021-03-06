package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.healthforms.dtos.DiabetesFormDTO
import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.predictions.repositories.IPredictionModelsRepository
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.inject.Inject

class GetDiabetesPredictionUseCase @Inject constructor(
    private val modelRepo: IPredictionModelsRepository
) :
    IGetDiabetesPredictionUseCase {
    override suspend fun execute(form: DiabetesFormDTO): Result<Prediction, Unit> {
        val modelResult = modelRepo.getDiabetes()

        val modelFile = if (modelResult is Success) {
            modelResult.value?.file
        } else {
            // Error getting the model
            return Error(Unit)
        }

        return if (modelFile != null) {
            val interpreter = Interpreter(modelFile)
            // Run the prediction
            val predictedValue = predict(interpreter, form)

            val prediction = Prediction(predictedValue)
            Success(prediction)
        } else {
            // When there is no form for this user, but there were no errors
            Error(Unit)
        }
    }

    private fun predict(interpreter: Interpreter, data: DiabetesFormDTO): Float {
        val bufferSize = 1 * java.lang.Float.SIZE / java.lang.Byte.SIZE

        val input =
            ByteBuffer.allocateDirect(8 * java.lang.Float.SIZE / java.lang.Byte.SIZE)
                .order(ByteOrder.nativeOrder())

        input.putFloat(data.age.toFloat())
        input.putFloat(data.bloodPressureLevel!!)
        input.putFloat(data.bmi!!)
        input.putFloat(data.glucoseLevel!!)
        input.putFloat(data.pedigreeFunc!!)
        input.putFloat(data.insulinLevel!!)
        input.putFloat(data.pregnancies!!.toFloat())
        input.putFloat(data.skinThickness!!)

        val modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())

        interpreter.run(input, modelOutput)
        modelOutput.rewind()
        return modelOutput.float
    }
}
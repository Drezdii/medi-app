package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.healthforms.dtos.AlzheimersFormDTO
import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.predictions.repositories.IPredictionModelsRepository
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.inject.Inject

class GetAlzheimersPredictionUseCase @Inject constructor(
    private val modelRepo: IPredictionModelsRepository
) : IGetAlzheimersPredictionUseCase {
    override suspend fun execute(form: AlzheimersFormDTO): Result<Prediction, Unit> {
        val modelResult = modelRepo.getAlzheimers()

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

            val prediction = Prediction(value = predictedValue)
            Success(prediction)
        } else {
            // When there is no form for this user, but there were no errors
            Error(Unit)
        }
    }

    private fun predict(interpreter: Interpreter, data: AlzheimersFormDTO): Float {
        val bufferSize = 1 * java.lang.Float.SIZE / java.lang.Byte.SIZE

        val input =
            ByteBuffer.allocateDirect(8 * java.lang.Float.SIZE / java.lang.Byte.SIZE).order(ByteOrder.nativeOrder())

        input.putFloat(data.age.toFloat())
        input.putFloat(data.gender.toFloat())
        input.putFloat(data.educationLevel!!.toFloat())
        input.putFloat(data.socioEconomicStatus!!.toFloat())
        input.putFloat(data.miniMentalState!!.toFloat())
        input.putFloat(data.clinicalDementiaRating!!.toFloat())
        input.putFloat(data.estTotalIntracranial!!.toFloat())
        input.putFloat(data.normalizeWholeBrain!!.toFloat())

        val modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())

        interpreter.run(input, modelOutput)
        modelOutput.rewind()
        return modelOutput.float
    }
}
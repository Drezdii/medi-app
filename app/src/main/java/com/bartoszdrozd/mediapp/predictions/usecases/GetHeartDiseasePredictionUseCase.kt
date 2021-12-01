package com.bartoszdrozd.mediapp.predictions.usecases

import com.bartoszdrozd.mediapp.healthforms.dtos.HeartFormDTO
import com.bartoszdrozd.mediapp.healthforms.repositories.IHealthFormsRepository
import com.bartoszdrozd.mediapp.predictions.models.Prediction
import com.bartoszdrozd.mediapp.predictions.models.PredictionType
import com.bartoszdrozd.mediapp.predictions.repositories.IPredictionModelsRepository
import com.bartoszdrozd.mediapp.utils.Error
import com.bartoszdrozd.mediapp.utils.Result
import com.bartoszdrozd.mediapp.utils.Success
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.inject.Inject

class GetHeartDiseasePredictionUseCase @Inject constructor(
    private val modelRepo: IPredictionModelsRepository,
    private val healthFormRepo: IHealthFormsRepository
) :
    IGetHeartDiseasePredictionUseCase {
    override suspend fun execute(uuid: String): Result<Prediction, Unit> {
        val modelResult = modelRepo.getHeart()

        val modelFile = if (modelResult is Success) {
            modelResult.value?.file
        } else {
            // Error getting the model
            return Error(Unit)
        }

        // Get the latest heart disease form for this user
        val res = healthFormRepo.getLatestHeartForm(uuid)

        val lastForm = if (res is Success) {
            res.value
        } else {
            null
        }

        return if (lastForm != null && modelFile != null) {
            val interpreter = Interpreter(modelFile)
            // Run the prediction
            Success(predict(interpreter, lastForm))
        } else {
            // When there is no form for this user, but there were no errors
            Error(Unit)
        }
    }

    private fun predict(interpreter: Interpreter, data: HeartFormDTO): Prediction {
        val bufferSize = 1 * java.lang.Float.SIZE / java.lang.Byte.SIZE

        val input =
            ByteBuffer.allocateDirect(13 * java.lang.Float.SIZE / java.lang.Byte.SIZE).order(ByteOrder.nativeOrder())

        input.putFloat(data.age.toFloat())
        input.putFloat(data.gender.toFloat())
        input.putFloat(data.chestPainType.toFloat())
        input.putFloat(data.restingBloodPressure!!.toFloat())
        input.putFloat(data.serumCholesterol!!.toFloat())
        input.putFloat(data.fastingBloodSugar!!.toFloat())
        input.putFloat(data.restingECG.toFloat())
        input.putFloat(data.maxHR!!.toFloat())
        input.putFloat(data.exerciseInducedAngina.toFloat())
        input.putFloat(data.stDepression!!.toFloat())
        input.putFloat(data.peakSTSegment.toFloat())
        input.putFloat(data.majorVessels.toFloat())
        input.putFloat(data.thalassemia.toFloat())

        val modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())

        interpreter.run(input, modelOutput)
        modelOutput.rewind()
        return Prediction("", modelOutput.float, PredictionType.HEART, 123456789)
    }
}
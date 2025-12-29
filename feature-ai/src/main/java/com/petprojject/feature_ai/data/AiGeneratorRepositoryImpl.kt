package com.petprojject.feature_ai.data

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.petprojject.domain.car.model.CarHistoryItem
import com.petprojject.domain.car.repository.CarHistoryRepository
import com.petprojject.feature_ai.domain.AiGeneratorRepository

class AiGeneratorRepositoryImpl(
    private val carHistoryRepository: CarHistoryRepository
) : AiGeneratorRepository {
    val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")

    override suspend fun getResponseFromAi(request: String): String {
        return model.generateContent(request).text ?: ""
    }

    override suspend fun getConclusionAboutUser(): String {
        val history = carHistoryRepository.getAllCarsHistory()
        val historyInString = StringBuilder()
        history.forEach {
            historyInString.append(it.manufacturer + " " + it.model + " " + it.year)
        }
        val prompt =
            "I'm user.Generate conclusion about me based on my latest search in cars: $historyInString." +
                    " Answer must be ready to show on android device, that means " +
                    "that you should not use **,this answer will be used directly in UI."
        return if (history.size >= MINIMAL_AMOUNT_OF_HISTORY_ITEMS) {
            model.generateContent(prompt).text ?: ""
        } else {
            "You need at least $MINIMAL_AMOUNT_OF_HISTORY_ITEMS items in history"
        }
    }

    override suspend fun compareCars(
        firstCar: CarHistoryItem,
        secondCar: CarHistoryItem
    ): String {
        val prompt =
            "I'm user.Compare 2 cars ${firstCar.manufacturer + " " + firstCar.model + "(" + firstCar.year + ")"} " +
                    "and ${secondCar.manufacturer + " " + secondCar.model + "(" + secondCar.year + ")"}. " +
                    "Answer must be ready to show on android device, that means that you should not use **, this answer will be used directly in UI."
        val response = model.generateContent(prompt).text ?: ""
        return response
    }

    override suspend fun getCarAlternatives(car: CarHistoryItem): String {
        val prompt =
            "I'm user.Give me alternative for car ${car.manufacturer + " " + car.model + "(" + car.year + ")"} " +
                    "Answer must be ready to show on android device, " +
                    "that means that you should not use **, this answer will be used directly in UI."
        val response = model.generateContent(prompt).text ?: ""
        return response
    }

    companion object {
        const val MINIMAL_AMOUNT_OF_HISTORY_ITEMS = 5
    }
}
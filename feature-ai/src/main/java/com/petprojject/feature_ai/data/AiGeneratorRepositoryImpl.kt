package com.petprojject.feature_ai.data

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.petprojject.feature_ai.domain.AiGeneratorRepository

class AiGeneratorRepositoryImpl : AiGeneratorRepository {
    val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")

    override suspend fun getResponseFromAi(request: String): String {
        return model.generateContent(request).text ?: ""
    }
}
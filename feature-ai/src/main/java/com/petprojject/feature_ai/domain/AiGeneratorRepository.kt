package com.petprojject.feature_ai.domain

interface AiGeneratorRepository {
   suspend fun getResponseFromAi(request: String): String
    suspend fun getConclusionAboutUser(): String

}
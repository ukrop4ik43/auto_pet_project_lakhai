package com.test.feature_ai.domain

interface AiGeneratorRepository {
   suspend fun getResponseFromAi(request: String): String
}
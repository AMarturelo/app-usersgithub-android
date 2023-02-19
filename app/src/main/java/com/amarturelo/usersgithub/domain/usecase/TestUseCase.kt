package com.amarturelo.usersgithub.domain.usecase

import com.amarturelo.usersgithub.core.domain.usecase.UseCase

class TestUseCase : UseCase<String, String>() {
    override suspend fun run(params: String): String {
        TODO("Not yet implemented")
    }
}
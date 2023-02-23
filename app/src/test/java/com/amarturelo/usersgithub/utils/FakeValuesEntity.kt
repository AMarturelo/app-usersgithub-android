package com.amarturelo.usersgithub.utils

import com.amarturelo.usersgithub.domain.entity.UserEntity


object FakeValuesEntity {
    fun user(): UserEntity {
        return UserEntity(
            "Fake",
            -1,
            "Fake",
        )
    }

    fun users(): List<UserEntity> {
        return (0..10).map { user() }
    }
}
package com.amarturelo.usersgithub.utils

import com.amarturelo.usersgithub.domain.entity.UserEntity


object FakeValuesEntity {
    fun follower(): UserEntity {
        return UserEntity(
            "Fake",
            -1,
            "Fake",
        )
    }

    fun followers(): List<UserEntity> {
        return (0..10).map { follower() }
    }
}
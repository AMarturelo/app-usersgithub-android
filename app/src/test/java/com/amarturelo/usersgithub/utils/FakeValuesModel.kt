package com.amarturelo.usersgithub.utils

import com.amarturelo.usersgithub.data.model.UserModel


object FakeValuesModel {
    fun user(): UserModel {
        return UserModel(
            avatarUrl = "Fake",
            id = -1,
            login = "Fake",
        )
    }

    fun users(): List<UserModel> {
        return (0..10).map { user() }
    }
}
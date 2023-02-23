package com.amarturelo.usersgithub.utils

import com.amarturelo.usersgithub.presentation.users.vo.UserListItemVO

object FakeValuesVO {
    fun user(): UserListItemVO {
        return UserListItemVO(
            -1,
            "Fake",
            "Fake",
        )
    }

    fun users(): List<UserListItemVO> {
        return (0..10).map { user() }
    }
}
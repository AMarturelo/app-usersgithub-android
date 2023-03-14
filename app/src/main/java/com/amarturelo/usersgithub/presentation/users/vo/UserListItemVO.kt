package com.amarturelo.usersgithub.presentation.users.vo

import android.os.Parcelable
import com.amarturelo.usersgithub.domain.entity.UserEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserListItemVO(val id: Int, val login: String, val avatarUrl: String) : Parcelable

fun UserEntity.toVO(): UserListItemVO {
    return UserListItemVO(id, login, avatarUrl)
}

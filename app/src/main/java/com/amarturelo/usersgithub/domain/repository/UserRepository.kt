package com.amarturelo.usersgithub.domain.repository

import com.amarturelo.usersgithub.commons.utils.Either
import com.amarturelo.usersgithub.domain.entity.UserEntity
import com.amarturelo.usersgithub.exception.Failure

interface UserRepository {
    suspend fun users(): Either<Failure, List<UserEntity>>
}
package com.amarturelo.usersgithub.data.datasource

import com.amarturelo.usersgithub.commons.utils.Either
import com.amarturelo.usersgithub.data.model.UserModel
import com.amarturelo.usersgithub.exception.Failure

interface UserDataSource {
    suspend fun users(): Either<Failure, List<UserModel>>
}

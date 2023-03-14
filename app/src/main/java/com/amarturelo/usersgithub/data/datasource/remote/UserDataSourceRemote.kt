package com.amarturelo.usersgithub.data.datasource.remote

import com.amarturelo.usersgithub.commons.utils.Either
import com.amarturelo.usersgithub.data.api.UserAPI
import com.amarturelo.usersgithub.data.datasource.UserDataSource
import com.amarturelo.usersgithub.data.model.UserModel
import com.amarturelo.usersgithub.exception.Failure
import javax.inject.Inject

class UserDataSourceRemote @Inject constructor(private val api: UserAPI) : UserDataSource {
    override suspend fun users(): Either<Failure, List<UserModel>> {
        val response = api.users()
        return when (response.isSuccessful) {
            true -> Either.Right(response.body() ?: listOf())
            false -> Either.Left(Failure.UnknownError)
        }
    }
}

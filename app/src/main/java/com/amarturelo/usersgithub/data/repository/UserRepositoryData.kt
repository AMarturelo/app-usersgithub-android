package com.amarturelo.usersgithub.data.repository

import com.amarturelo.usersgithub.commons.utils.Either
import com.amarturelo.usersgithub.commons.utils.map
import com.amarturelo.usersgithub.data.datasource.UserDataSource
import com.amarturelo.usersgithub.data.model.toEntity
import com.amarturelo.usersgithub.domain.entity.UserEntity
import com.amarturelo.usersgithub.domain.repository.UserRepository
import com.amarturelo.usersgithub.exception.Failure
import javax.inject.Inject

class UserRepositoryData @Inject constructor(private val remoteDataSource: UserDataSource) :
    UserRepository {
    override suspend fun users(): Either<Failure, List<UserEntity>> {
        return remoteDataSource.users().map { result ->
            result.map { it.toEntity() }
        }
    }
}

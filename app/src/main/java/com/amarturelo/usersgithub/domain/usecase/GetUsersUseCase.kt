package com.amarturelo.usersgithub.domain.usecase

import com.amarturelo.usersgithub.commons.utils.Either
import com.amarturelo.usersgithub.core.domain.usecase.UseCase
import com.amarturelo.usersgithub.domain.entity.UserEntity
import com.amarturelo.usersgithub.domain.repository.UserRepository
import com.amarturelo.usersgithub.exception.Failure
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: UserRepository) :
    UseCase<Either<Failure, List<UserEntity>>, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, List<UserEntity>> {
        return repository.users()
    }
}

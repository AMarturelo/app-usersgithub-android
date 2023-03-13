package com.amarturelo.usersgithub.usecase

import com.amarturelo.usersgithub.commons.utils.Either
import com.amarturelo.usersgithub.commons.utils.getOrElse
import com.amarturelo.usersgithub.core.domain.usecase.UseCase
import com.amarturelo.usersgithub.domain.repository.UserRepository
import com.amarturelo.usersgithub.domain.usecase.GetUsersUseCase
import com.amarturelo.usersgithub.exception.Failure
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import com.amarturelo.usersgithub.utils.FakeValuesEntity

class GetUsersUseCaseTest {

    @MockK(relaxed = true)
    lateinit var userRepository: UserRepository

    @InjectMockKs
    lateinit var useCase: GetUsersUseCase

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given valid params when run then verify result`() = runTest {
        //given
        val fakeResult = FakeValuesEntity.followers()
        coEvery { userRepository.users() } returns Either.Right(fakeResult)

        //when
        val result = useCase.run(UseCase.None)

        //then
        Assert.assertEquals(fakeResult.size, result.getOrElse(listOf()).size)
    }

    @Test
    fun `given invalid params when run then verify result`() = runTest {
        //given
        coEvery { userRepository.users() } returns Either.Left(Failure.NetworkConnection)

        //when
        val result = useCase.run(UseCase.None)

        //then
        Assert.assertTrue(result.isLeft)
    }

}
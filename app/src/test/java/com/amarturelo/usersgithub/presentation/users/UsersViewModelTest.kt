package com.amarturelo.usersgithub.presentation.users

import com.amarturelo.usersgithub.commons.utils.Either
import com.amarturelo.usersgithub.core.BaseViewModelTest
import com.amarturelo.usersgithub.core.CaptureObserver
import com.amarturelo.usersgithub.domain.usecase.GetUsersUseCase
import com.amarturelo.usersgithub.exception.Failure
import com.amarturelo.usersgithub.presentation.users.UsersState.ERROR
import com.amarturelo.usersgithub.presentation.users.vo.UserListItemVO
import com.amarturelo.usersgithub.utils.FakeValuesEntity
import com.amarturelo.usersgithub.utils.FakeValuesVO
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UsersViewModelTest : BaseViewModelTest() {
    @MockK(relaxed = true)
    lateinit var getPeoplesUseCase: GetUsersUseCase

    private lateinit var viewModel: UsersViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(UsersViewModel(getPeoplesUseCase))
    }

    @Test
    fun `given users when populate then verify interactions`() {
        //given
        val contentStateCapture = CaptureObserver<String>()
        viewModel.contentState.observeForever(contentStateCapture)
        viewModel.setUsers(FakeValuesVO.users())

        //when
        viewModel.populate()

        //then
        verify { getPeoplesUseCase(any(), any(), any()) }
        Assert.assertEquals(0, contentStateCapture.capture.size)
    }

    @Test
    fun `given empty users when populate then verify interactions`() {
        //given
        val contentStateCapture = CaptureObserver<String>()
        viewModel.contentState.observeForever(contentStateCapture)

        //when
        viewModel.populate()

        //then
        verify { getPeoplesUseCase(any(), any(), any()) }
        Assert.assertEquals(1, contentStateCapture.capture.size)
        Assert.assertEquals(UsersState.LOADING, contentStateCapture.capture[0])
    }

    @Test
    fun `given viewModel when refresh when verify interactions`() {
        //given
        val contentStateCapture = CaptureObserver<String>()
        viewModel.contentState.observeForever(contentStateCapture)

        //when
        viewModel.refresh()

        //then
        verify { viewModel.populate() }
    }

    @Test
    fun `given get users success result when handleGetProductsUseCaseResult then verify interactions`() {
        //given
        val contentStateCapture = CaptureObserver<String>()
        viewModel.contentState.observeForever(contentStateCapture)
        val observerPayload = CaptureObserver<List<UserListItemVO>>()
        val observerIsLoading = CaptureObserver<Boolean>()
        viewModel.users.observeForever(observerPayload)
        viewModel.isLoading.observeForever(observerIsLoading)

        //when
        viewModel.handleGetUsersUseCaseResult(
            Either.Right(FakeValuesEntity.followers())
        )

        //then
        Assert.assertEquals(1, contentStateCapture.capture.size)
        Assert.assertEquals(1, observerPayload.capture.size)
        Assert.assertEquals(false, observerIsLoading.capture.first())
    }

    @Test
    fun `given get users failed result when handleGetProductsUseCaseResult then verify interactions`() {
        //given
        val contentStateCapture = CaptureObserver<String>()
        viewModel.contentState.observeForever(contentStateCapture)
        val observerPayload = CaptureObserver<List<UserListItemVO>>()
        val observerIsLoading = CaptureObserver<Boolean>()
        val observerFailure = CaptureObserver<Failure>()
        viewModel.users.observeForever(observerPayload)
        viewModel.isLoading.observeForever(observerIsLoading)
        viewModel.failure.observeForever(observerFailure)

        //when
        viewModel.handleGetUsersUseCaseResult(
            Either.Left(Failure.ServerError)
        )

        //then
        Assert.assertEquals(1, contentStateCapture.capture.size)
        Assert.assertEquals(1, observerFailure.capture.size)
        Assert.assertEquals(ERROR, contentStateCapture.capture.first())
        verify { viewModel.handleFailure(any()) }
        Assert.assertEquals(0, observerPayload.capture.size)
        Assert.assertEquals(false, observerIsLoading.capture.first())
    }

    @Test
    fun `given loaded users when handleItemPressed then verify interactions`() {
        //given
        val observerGoToDetails = CaptureObserver<UserListItemVO>()
        viewModel.goToDetails.observeForever(observerGoToDetails)
        val user = FakeValuesVO.user()

        //when
        viewModel.handleItemPressed(user)

        //then
        Assert.assertEquals(listOf(user), observerGoToDetails.capture)
    }

}
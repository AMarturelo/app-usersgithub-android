package com.amarturelo.usersgithub.presentation.users

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amarturelo.usersgithub.commons.utils.Either
import com.amarturelo.usersgithub.commons.utils.getOrElse
import com.amarturelo.usersgithub.commons.utils.onFailure
import com.amarturelo.usersgithub.core.domain.usecase.UseCase
import com.amarturelo.usersgithub.core.presentation.SingleLiveEvent
import com.amarturelo.usersgithub.domain.entity.UserEntity
import com.amarturelo.usersgithub.domain.usecase.GetUsersUseCase
import com.amarturelo.usersgithub.exception.Failure
import com.amarturelo.usersgithub.presentation.commons.StatefulLayout
import com.amarturelo.usersgithub.presentation.users.vo.UserListItemVO
import com.amarturelo.usersgithub.presentation.users.vo.toVO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) :
    ViewModel() {

    private val _goToDetails: SingleLiveEvent<UserListItemVO> = SingleLiveEvent()
    val goToDetails: LiveData<UserListItemVO> = _goToDetails

    private val _users: MutableLiveData<List<UserListItemVO>> = MutableLiveData()
    val users: LiveData<List<UserListItemVO>> = _users

    private val _failure: MutableLiveData<Failure> = SingleLiveEvent()
    val failure: LiveData<Failure> = _failure

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _contentState: MutableLiveData<String> = MutableLiveData()
    val contentState: LiveData<String> = _contentState

    fun populate() {
        if (users.value.isNullOrEmpty()) {
            _contentState.value = UsersState.LOADING
        }
        getUsersUseCase(UseCase.None, viewModelScope, ::handleGetUsersUseCaseResult)
    }

    fun refresh() {
        this.populate()
    }

    @VisibleForTesting
    fun handleGetUsersUseCaseResult(result: Either<Failure, List<UserEntity>>) {
        if (result.isLeft) {
            _contentState.value = UsersState.ERROR
            result.onFailure {
                handleFailure(it)
            }
        } else {
            _contentState.value = UsersState.CONTENT
            val results = result.getOrElse(listOf())
            _users.value = results.map { it.toVO() }
        }
        _isLoading.value = false
    }

    @VisibleForTesting
    fun handleFailure(failure: Failure) {
        _failure.value = failure
    }

    @VisibleForTesting
    fun setUsers(users: List<UserListItemVO>) {
        _users.value = users
    }

    fun handleItemPressed(item: UserListItemVO) {
        _goToDetails.value = item
    }
}

object UsersState {
    const val CONTENT = StatefulLayout.State.CONTENT
    const val ERROR = "STATE_ERROR"
    const val LOADING = "STATE_LOADING"
}

package com.amarturelo.usersgithub.presentation.users

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amarturelo.usersgithub.R
import com.amarturelo.usersgithub.databinding.FragmentUsersBinding
import com.amarturelo.usersgithub.exception.Failure
import com.amarturelo.usersgithub.exception.failure
import com.amarturelo.usersgithub.exception.observe
import com.amarturelo.usersgithub.presentation.users.UsersState.ERROR
import com.amarturelo.usersgithub.presentation.users.UsersState.LOADING
import com.amarturelo.usersgithub.presentation.users.adapter.UserController
import com.amarturelo.usersgithub.presentation.users.vo.UserListItemVO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private val viewModel: UsersViewModel by viewModels()

    private lateinit var controller: UserController

    private var _binding: FragmentUsersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupStatefulLayout()

        with(viewModel) {
            observe(users, ::handleUsers)
            observe(contentState, ::handleContentState)
            observe(goToDetails, ::navigateToDetails)
            observe(isLoading, ::handleIsLoading)
            failure(failure, ::handleFailure)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.populate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        controller = UserController(::handleItemPressed)
        binding.rvUsers.adapter = controller.adapter
        binding.srlUsers.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    @SuppressLint("InflateParams")
    private fun setupStatefulLayout() {
        binding.slUsers.setStateView(
            ERROR,
            LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_users_state_error,
                null,
            ),
        )

        binding.slUsers.setStateView(
            LOADING,
            LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_users_state_loading,
                null,
            ),
        )
    }

    private fun navigateToDetails(item: UserListItemVO?) {
        item ?: return

        findNavController().navigate(
            UsersFragmentDirections.actionUsersFragmentToFollowersFragment(item.login),
        )
    }

    private fun handleItemPressed(item: UserListItemVO) {
        viewModel.handleItemPressed(item)
    }

    private fun handleUsers(values: List<UserListItemVO>?) {
        values ?: return
        controller.setData(values)
    }

    private fun handleFailure(failure: Failure?) {
        failure ?: return
    }

    private fun handleIsLoading(isLoading: Boolean?) {
        binding.srlUsers.isRefreshing = isLoading ?: false
    }

    private fun handleContentState(contentState: String?) {
        contentState ?: return
        binding.slUsers.state = contentState
    }
}

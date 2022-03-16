package com.example.changebondtask.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.changebondtask.view.ui.activity.MainActivity
import com.example.changebondtask.R
import com.example.changebondtask.databinding.FragmentHomeBinding
import com.example.changebondtask.utilities.AuthState
import com.example.changebondtask.view.adapter.UserListAdapter
import com.example.changebondtask.view.model.Users
import com.example.changebondtask.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class HomeFragment : Fragment(){

    private lateinit var binding : FragmentHomeBinding

    val activityContext: MainActivity by lazy {
        val activity = requireNotNull(activity)
        (activity as MainActivity)
    }


    private val viewModel : MainActivityViewModel by lazy {
        activityContext.viewModel
    }

    private val adapter : UserListAdapter by lazy {
        UserListAdapter{
            model ->
            navigateToDetailsFragment(model)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        //binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUsersList.adapter = adapter


        observers()

    }

    private fun observers() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.userList.collect {
                when (it){
                    is AuthState.Loading ->{

                    }
                    is AuthState.Authenticated ->{
                        if(!it.data.isNullOrEmpty()){
                            withContext(Dispatchers.Main){
                                adapter.submitList(it.data)
                            }
                        }
                    }
                    is AuthState.AuthenticationFailed->{

                    }
                }

            }
        }
    }

    private fun navigateToDetailsFragment(model : Users){
        val detailNavigateAction = HomeFragmentDirections.actionNavHomeToDetailFragment(model)
        requireView().findNavController().navigate(detailNavigateAction, NavOptions.Builder().setLaunchSingleTop(true).build())
    }
}
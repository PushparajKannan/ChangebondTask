package com.example.changebondtask.viewmodels

import androidx.lifecycle.ViewModel
import com.example.changebondtask.repository.ApiDataRepository
import com.example.changebondtask.repository.LocalDataRepository
import com.example.changebondtask.utilities.AuthState
import com.example.changebondtask.view.model.Users
import com.example.changebondtask.view.model.toUserDb
import com.example.changebondtask.view.model.toUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val networkRepo : ApiDataRepository,
    private val localDbRepo: LocalDataRepository
) : ViewModel() {


    val userList = flow<AuthState<List<Users>>> {
        localDbRepo.getVehicleLiveDataFromDb().collect {
            if(it.isNullOrEmpty()){
                emit(AuthState.loading())
                withContext(Dispatchers.IO){
                    networkRepo.apiUserList().run {
                        this.collect { _it->
                            when(_it){
                                is AuthState.Authenticated ->{
                                    withContext(Dispatchers.IO){

                                        localDbRepo.insertUserList(_it.data.toUserDb())
                                    }
                                }

                                is AuthState.AuthenticationFailed ->{
                                    emit(AuthState.authenticationFailed("Empty Data"))
                                }
                            }
                        }
                    }
                }

            }else{
                emit(AuthState.authenticated(it.toUserModel()))
            }
        }
    }

}
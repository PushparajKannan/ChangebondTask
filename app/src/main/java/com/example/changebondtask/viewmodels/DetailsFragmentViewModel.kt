package com.example.changebondtask.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.changebondtask.repository.LocalDataRepository
import com.example.changebondtask.view.model.Users
import com.example.changebondtask.view.model.toUserDbModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsFragmentViewModel  @Inject constructor(
    private val localDbRepo: LocalDataRepository
)  : ViewModel() {

    var userModel = MutableLiveData<Users>()
    suspend fun updateComment(users: Users) = localDbRepo.updateUserList(users.toUserDbModel())

}
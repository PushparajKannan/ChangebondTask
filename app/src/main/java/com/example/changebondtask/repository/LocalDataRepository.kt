package com.example.changebondtask.repository

import com.example.changebondtask.database.AppDatabase
import com.example.changebondtask.database.model.UsersDbModel
import com.example.changebondtask.view.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataRepository @Inject constructor(val database: AppDatabase) {

    /**
     * USERS CURD OPERATION FROM LOCAL DATA BASE
     */

    fun getVehicleListFromDb(params: String) =
        database.usersDao().getUsersListUpdate().map {
          //  it!!.localDataBaseVehicleListToVehicleList()
        }.flowOn(Dispatchers.IO)

    fun getVehicleLiveDataFromDb() = database.usersDao().getUsersListUpdate()

    suspend fun insertUserList(usersList : List<UsersDbModel>) = withContext(Dispatchers.IO) {
        database.usersDao().insertUser(usersList)
    }

    suspend fun updateUserList(user: UsersDbModel) = withContext(Dispatchers.IO) {
        database.usersDao().updateUser(user)
    }


}
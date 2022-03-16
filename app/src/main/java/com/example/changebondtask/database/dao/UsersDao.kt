package com.example.changebondtask.database.dao

import androidx.room.*
import com.example.changebondtask.database.model.UsersDbModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(config: List<UsersDbModel>?)

    @Update
    suspend fun updateUser(vararg config: UsersDbModel?)

    @Delete
    suspend fun deleteUser(vararg config: UsersDbModel?)

    @Query("SELECT * FROM UsersDbModel  ORDER BY id DESC")
    abstract fun loadUsersList(): Flow<List<UsersDbModel>?>

    fun getUsersListUpdate() =
        loadUsersList().distinctUntilChanged()
}
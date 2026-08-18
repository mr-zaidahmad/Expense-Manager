package com.example.expensemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDatabaseDAO {
  @Insert
  suspend fun InsertuserData(user : RoomdatabaseUserdata)

  @Query("SELECT *FROM Account")
  fun getAllAccount(): LiveData<List<RoomdatabaseUserdata>>

  @Query("SELECT * FROM Account WHERE name = :name LIMIT 1")
  suspend fun getAccountByName(name: String): RoomdatabaseUserdata?


}
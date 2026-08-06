package com.example.expensemanager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RoomdatabaseUserdata::class], version = 1)
abstract class Roomdatabase_UserDatabase : RoomDatabase()  {


    abstract fun userDAO() : RoomDatabaseDAO

     companion object{

         private var instance: Roomdatabase_UserDatabase?=null

         fun getDatabase(context: Context) : Roomdatabase_UserDatabase{

         if (instance==null){
              instance= Room.databaseBuilder(
                  context,
                  Roomdatabase_UserDatabase::class.java,
                  "UserDB"
              ).build()
             }
             return instance!!
         }
     }
}
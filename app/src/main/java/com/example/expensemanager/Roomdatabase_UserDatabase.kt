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

@Database(entities = [
    RoomdatabaseUserdata::class,
    RoomdatabaseTransaction::class],
    version = 2)
abstract class Roomdatabase_UserDatabase : RoomDatabase()  {


    abstract fun userDAO() : RoomDatabaseDAO
    abstract fun transactionDAO(): TransactionDAO

     companion object{

         private var instance: Roomdatabase_UserDatabase?=null

         fun getDatabase(context: Context) : Roomdatabase_UserDatabase{

         if (instance==null){
              instance= Room.databaseBuilder(
                  context,
                  Roomdatabase_UserDatabase::class.java,
                  "UserDB"
              )
                  .fallbackToDestructiveMigration()
                  .build()
             }
             return instance!!
         }
     }
}
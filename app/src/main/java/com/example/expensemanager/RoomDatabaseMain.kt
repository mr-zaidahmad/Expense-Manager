package com.example.expensemanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import com.example.expensemanager.databinding.ActivityRoomDatabaseMainBinding


class RoomDatabaseMain : AppCompatActivity() {

       private lateinit var database : Roomdatabase_UserDatabase
         private lateinit var binding: ActivityRoomDatabaseMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
          binding= ActivityRoomDatabaseMainBinding.inflate(layoutInflater)
          setContentView(binding.root)

        database= Roomdatabase_UserDatabase.getDatabase(this)

       if (savedInstanceState==null){
           supportFragmentManager.beginTransaction()
               .replace(R.id.fragmentContainer, AddAccount())
               .commit()
       }
    }
}
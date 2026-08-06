package com.example.expensemanager

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.PropertyKey

@Entity("Account")
data class RoomdatabaseUserdata (
    @PrimaryKey(autoGenerate = true)
    var id : Int=0,
   var name: String,
   var Currency : String,
    var InitialAmount : Int
)




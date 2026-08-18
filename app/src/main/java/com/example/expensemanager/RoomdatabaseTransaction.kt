package com.example.expensemanager

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Transaction")
data class RoomdatabaseTransaction(
         @PrimaryKey(autoGenerate = true)
          var id : Int=0,
    var accountId : Int,
    var type : String,
    var amount : Double,
    var description: String,
    var wallet : String,
    var category : String,
    var date: String
)

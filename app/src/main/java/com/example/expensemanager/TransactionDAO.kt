package com.example.expensemanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDAO {

    @Insert
    suspend fun insertTransaction(transaction: RoomdatabaseTransaction)

    @Query("""
        SELECT * FROM `Transaction`
        WHERE accountId = :accountId
        ORDER BY id DESC
    """)
    fun getTransactionsForAccount(
        accountId: Int
    ): LiveData<List<RoomdatabaseTransaction>>

    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM `Transaction`
        WHERE accountId = :accountId
        AND type = 'INCOME'
    """)
    suspend fun getTotalIncome(accountId: Int): Double

    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM `Transaction`
        WHERE accountId = :accountId
        AND type = 'EXPENSE'
    """)
    suspend fun getTotalExpense(accountId: Int): Double

    @Query("""
    SELECT COALESCE(SUM(amount), 0)
    FROM `Transaction`
    WHERE accountId = :accountId
    AND type = 'INCOME'
    AND date LIKE :monthPattern
""")
    suspend fun getMonthlyIncome(
        accountId: Int,
        monthPattern: String
    ): Double


    @Query("""
    SELECT COALESCE(SUM(amount), 0)
    FROM `Transaction`
    WHERE accountId = :accountId
    AND type = 'EXPENSE'
    AND date LIKE :monthPattern
""")
    suspend fun getMonthlyExpense(
        accountId: Int,
        monthPattern: String
    ): Double
}
package com.example.expensemanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

data class CategoryTotal(
    val category: String,
    val total: Double
)

@Dao
interface TransactionDAO {

    @Insert
    suspend fun insertTransaction(
        transaction: RoomdatabaseTransaction
    )

    // =========================================
    // ALL TRANSACTIONS FOR SELECTED ACCOUNT
    // =========================================

    @Query("""
        SELECT * FROM `Transaction`
        WHERE accountId = :accountId
        ORDER BY id DESC
    """)
    fun getTransactionsForAccount(
        accountId: Int
    ): LiveData<List<RoomdatabaseTransaction>>


    // =========================================
    // TRANSACTIONS BY CATEGORY
    // =========================================

    @Query("""
        SELECT * FROM `Transaction`
        WHERE accountId = :accountId
        AND category = :category
        AND type = :type
        ORDER BY id DESC
    """)
    fun getTransactionsByCategory(
        accountId: Int,
        category: String,
        type: String
    ): LiveData<List<RoomdatabaseTransaction>>


    // =========================================
    // TOTAL INCOME
    // =========================================

    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM `Transaction`
        WHERE accountId = :accountId
        AND type = 'INCOME'
    """)
    suspend fun getTotalIncome(
        accountId: Int
    ): Double


    // =========================================
    // TOTAL EXPENSE
    // =========================================

    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM `Transaction`
        WHERE accountId = :accountId
        AND type = 'EXPENSE'
    """)
    suspend fun getTotalExpense(
        accountId: Int
    ): Double


    // =========================================
    // MONTHLY INCOME
    // =========================================

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


    // =========================================
    // MONTHLY EXPENSE
    // =========================================

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


    // =========================================
    // EXPENSE BY CATEGORY
    // =========================================

    @Query("""
        SELECT category, COALESCE(SUM(amount), 0) AS total
        FROM `Transaction`
        WHERE accountId = :accountId
        AND type = 'EXPENSE'
        GROUP BY category
        ORDER BY total DESC
    """)
    suspend fun getExpenseByCategory(
        accountId: Int
    ): List<CategoryTotal>


    // =========================================
    // DELETE ALL TRANSACTIONS FOR ACCOUNT
    // =========================================

    @Query("""
        DELETE FROM `Transaction`
        WHERE accountId = :accountId
    """)
    suspend fun deleteTransactionsForAccount(
        accountId: Int
    )


    // =========================================
    // DELETE ONE TRANSACTION
    // =========================================

    @Query("""
        DELETE FROM `Transaction`
        WHERE id = :transactionId
    """)
    suspend fun deleteTransaction(
        transactionId: Int
    )
}
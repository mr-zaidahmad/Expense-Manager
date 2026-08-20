package com.example.expensemanager

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensemanager.databinding.FragmentSettingIncomeBinding
import kotlinx.coroutines.launch

class SettingIncome : Fragment() {

    private var _binding: FragmentSettingIncomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: Roomdatabase_UserDatabase
    private lateinit var adapter: SettingTransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingIncomeBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        database =
            Roomdatabase_UserDatabase.getDatabase(
                requireContext()
            )

        setupRecyclerView()

        loadIncomeTransactions()
    }

    // =========================================
    // RECYCLER VIEW
    // =========================================

    private fun setupRecyclerView() {

        adapter = SettingTransactionAdapter(
            emptyList()
        ) { transaction ->

            showDeleteDialog(transaction)
        }

        binding.SettingIncomeRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.SettingIncomeRecyclerView.adapter =
            adapter
    }


    // =========================================
    // LOAD INCOME TRANSACTIONS
    // =========================================

    private fun loadIncomeTransactions() {

        val preferences =
            requireActivity().getSharedPreferences(
                "ExpenseManager",
                Context.MODE_PRIVATE
            )

        val selectedAccountName =
            preferences.getString(
                "SELECTED_ACCOUNT",
                null
            )

        if (selectedAccountName == null) {
            return
        }

        lifecycleScope.launch {

            val account =
                database.userDAO()
                    .getAccountByName(
                        selectedAccountName
                    )

            if (account == null) {
                return@launch
            }

            database.transactionDAO()
                .getTransactionsForAccount(
                    account.id
                )
                .observe(viewLifecycleOwner) { transactions ->

                    val incomes =
                        transactions.filter {
                            it.type == "INCOME"
                        }

                    adapter.updateTransactions(
                        incomes
                    )
                }
        }
    }


    // =========================================
    // DELETE DIALOG
    // =========================================

    private fun showDeleteDialog(
        transaction: RoomdatabaseTransaction
    ) {

        AlertDialog.Builder(requireContext())
            .setTitle("Delete Transaction")
            .setMessage(
                "Are you sure you want to delete this transaction?"
            )
            .setNegativeButton(
                "CANCEL",
                null
            )
            .setPositiveButton(
                "DELETE"
            ) { _, _ ->

                lifecycleScope.launch {

                    database.transactionDAO()
                        .deleteTransaction(
                            transaction.id
                        )
                }
            }
            .show()
    }


    // =========================================
    // CLEAN UP
    // =========================================

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null
    }
}
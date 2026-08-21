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
import com.example.expensemanager.databinding.FragmentSettingExpenseBinding
import kotlinx.coroutines.launch

class settingExpense : Fragment() {

    private var _binding: FragmentSettingExpenseBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: Roomdatabase_UserDatabase
    private lateinit var adapter: SettingTransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentSettingExpenseBinding.inflate(
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

        super.onViewCreated(
            view,
            savedInstanceState
        )

        database =
            Roomdatabase_UserDatabase.getDatabase(
                requireContext()
            )

        setupRecyclerView()

        loadExpenseTransactions()
    }

    // =========================================
    // RECYCLER VIEW
    // =========================================

    private fun setupRecyclerView() {

        adapter =
            SettingTransactionAdapter(
                emptyList()
            ) { transaction ->

                showDeleteDialog(
                    transaction
                )
            }

        binding.SettingExpenseRecyclerView.layoutManager =
            LinearLayoutManager(
                requireContext()
            )

        binding.SettingExpenseRecyclerView.adapter =
            adapter
    }

    // =========================================
    // LOAD EXPENSE TRANSACTIONS
    // =========================================

    private fun loadExpenseTransactions() {

        val preferences =
            requireActivity().getSharedPreferences(
                Constant.PREFERENCESNAME,
                Context.MODE_PRIVATE
            )

        val selectedAccountName =
            preferences.getString(
                Constant.SELECTEDACCOUNT,
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
                .observe(
                    viewLifecycleOwner
                ) { transactions ->

                    val expenses =
                        transactions.filter {

                            it.type ==
                                    getString(
                                        R.string.expense
                                    )
                        }

                    adapter.updateTransactions(
                        expenses
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

        AlertDialog.Builder(
            requireContext()
        )
            .setTitle(
                getString(
                    R.string.delete_transaction
                )
            )
            .setMessage(
                getString(
                    R.string.are_you_sure_you_want_to_delete_this_transaction
                )
            )
            .setNegativeButton(
                getString(R.string.cancel),
                null
            )
            .setPositiveButton(
                getString(R.string.delete)
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
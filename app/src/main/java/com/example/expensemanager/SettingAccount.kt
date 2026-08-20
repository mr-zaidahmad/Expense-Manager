package com.example.expensemanager

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensemanager.databinding.FragmentSettingAccountBinding
import kotlinx.coroutines.launch

class SettingAccount : Fragment() {

    private lateinit var binding: FragmentSettingAccountBinding
    private lateinit var database: Roomdatabase_UserDatabase
    private lateinit var adapter: AccountAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSettingAccountBinding.inflate(
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

        loadAccounts()

        binding.AddAccountIcon.setOnClickListener {

            findNavController().navigate(
                R.id.action_settingAccount_to_addAccount
            )
        }
    }

    private fun setupRecyclerView() {

        adapter = AccountAdapter(
            emptyList(),

            // Account clicked
            onClick = { account ->
                showEditAccountDialog(account)
            },

            // Delete clicked
            onDeleteClick = { account ->
                showDeleteDialog(account)
            }
        )

        binding.AccountRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.AccountRecyclerView.adapter =
            adapter
    }

    private fun loadAccounts() {

        database.userDAO()
            .getAllAccount()
            .observe(viewLifecycleOwner) { accounts ->

                adapter.updateList(accounts)
            }
    }

    private fun showEditAccountDialog(
        account: RoomdatabaseUserdata
    ) {

        val editText = EditText(requireContext())

        editText.setText(account.name)
        editText.setSelection(editText.text.length)

        val padding = (20 * resources.displayMetrics.density).toInt()

        val container = android.widget.FrameLayout(requireContext())

        container.setPadding(
            padding,
            0,
            padding,
            0
        )

        container.addView(editText)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Account")
            .setView(container)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save") { _, _ ->

                val newName =
                    editText.text.toString().trim()

                if (newName.isEmpty()) {

                    Toast.makeText(
                        requireContext(),
                        "Account name cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setPositiveButton
                }

                updateAccount(
                    account,
                    newName
                )
            }
            .show()
    }

    private fun updateAccount(
        account: RoomdatabaseUserdata,
        newName: String
    ) {

        viewLifecycleOwner.lifecycleScope.launch {

            database.userDAO()
                .updateAccountName(
                    account.id,
                    newName
                )

            val preferences =
                requireActivity()
                    .getSharedPreferences(
                        "ExpenseManager",
                        Context.MODE_PRIVATE
                    )

            val selectedAccount =
                preferences.getString(
                    "SELECTED_ACCOUNT",
                    null
                )

            if (selectedAccount == account.name) {

                preferences.edit()
                    .putString(
                        "SELECTED_ACCOUNT",
                        newName
                    )
                    .apply()
            }

            Toast.makeText(
                requireContext(),
                "Account updated",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showDeleteDialog(
        account: RoomdatabaseUserdata
    ) {

        AlertDialog.Builder(requireContext())
            .setTitle("Delete Account")
            .setMessage(
                "Are you sure you want to delete \"${account.name}\"?"
            )
            .setNegativeButton(
                "Cancel",
                null
            )
            .setPositiveButton(
                "Delete"
            ) { _, _ ->

                deleteAccount(account)
            }
            .show()
    }

    private fun deleteAccount(
        account: RoomdatabaseUserdata
    ) {

        viewLifecycleOwner.lifecycleScope.launch {

            // Delete all transactions belonging to this account
            database.transactionDAO()
                .deleteTransactionsForAccount(
                    account.id
                )

            // Delete the account
            database.userDAO()
                .deleteAccount(
                    account.id
                )

            val preferences =
                requireActivity()
                    .getSharedPreferences(
                        "ExpenseManager",
                        Context.MODE_PRIVATE
                    )

            val selectedAccount =
                preferences.getString(
                    "SELECTED_ACCOUNT",
                    null
                )

            if (selectedAccount == account.name) {

                preferences.edit()
                    .remove("SELECTED_ACCOUNT")
                    .apply()
            }

            Toast.makeText(
                requireContext(),
                "Account deleted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
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

        binding =
            FragmentSettingAccountBinding.inflate(
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

        loadAccounts()


        binding.AddAccountIcon.setOnClickListener {

            findNavController().navigate(
                R.id.action_settingAccount_to_addAccount
            )
        }
    }


    // =========================================
    // RECYCLER VIEW
    // =========================================

    private fun setupRecyclerView() {

        adapter =
            AccountAdapter(
                emptyList(),

                // Account clicked
                onClick = { account ->

                    showEditAccountDialog(
                        account
                    )
                },

                // Delete clicked
                onDeleteClick = { account ->

                    showDeleteDialog(
                        account
                    )
                },

                // KEEP DELETE BUTTON VISIBLE
                showDeleteButton = true
            )


        binding.AccountRecyclerView.layoutManager =
            LinearLayoutManager(
                requireContext()
            )

        binding.AccountRecyclerView.adapter =
            adapter
    }


    // =========================================
    // LOAD ACCOUNTS
    // =========================================

    private fun loadAccounts() {

        database.userDAO()
            .getAllAccount()
            .observe(viewLifecycleOwner) { accounts ->

                adapter.updateList(
                    accounts
                )
            }
    }


    // =========================================
    // EDIT ACCOUNT
    // =========================================

    private fun showEditAccountDialog(
        account: RoomdatabaseUserdata
    ) {

        val editText =
            EditText(
                requireContext()
            )

        editText.setText(
            account.name
        )

        editText.setSelection(
            editText.text.length
        )


        val padding =
            (
                    20 *
                            resources.displayMetrics.density
                    ).toInt()


        val container =
            android.widget.FrameLayout(
                requireContext()
            )

        container.setPadding(
            padding,
            0,
            padding,
            0
        )

        container.addView(
            editText
        )


        AlertDialog.Builder(
            requireContext()
        )
            .setTitle(
                getString(
                    R.string.edit_account
                )
            )
            .setView(
                container
            )
            .setNegativeButton(
                getString(
                    R.string.cancel
                ),
                null
            )
            .setPositiveButton(
                getString(
                    R.string.save
                )
            ) { _, _ ->

                val newName =
                    editText.text
                        .toString()
                        .trim()


                if (newName.isEmpty()) {

                    Toast.makeText(
                        requireContext(),
                        getString(
                            R.string.account_name_cannot_be_empty
                        ),
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


    // =========================================
    // UPDATE ACCOUNT
    // =========================================

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
                        getString(
                            R.string.expensemanager
                        ),
                        Context.MODE_PRIVATE
                    )


            val selectedAccount =
                preferences.getString(
                    Constant.SELECTEDACCOUNT,
                    null
                )


            if (selectedAccount == account.name) {

                preferences.edit()
                    .putString(
                        Constant.SELECTEDACCOUNT,
                        newName
                    )
                    .apply()
            }


            Toast.makeText(
                requireContext(),
                getString(
                    R.string.account_updated
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    // =========================================
    // DELETE DIALOG
    // =========================================

    private fun showDeleteDialog(
        account: RoomdatabaseUserdata
    ) {

        AlertDialog.Builder(
            requireContext()
        )
            .setTitle(
                getString(
                    R.string.delete_account
                )
            )
            .setMessage(
                getString(
                    R.string.are_you_sure_you_want_to_delete,
                    account.name
                )
            )
            .setNegativeButton(
                getString(
                    R.string.cancel
                ),
                null
            )
            .setPositiveButton(
                getString(
                    R.string.delete
                )
            ) { _, _ ->

                deleteAccount(
                    account
                )
            }
            .show()
    }


    // =========================================
    // DELETE ACCOUNT
    // =========================================

    private fun deleteAccount(
        account: RoomdatabaseUserdata
    ) {

        viewLifecycleOwner.lifecycleScope.launch {

            // Delete all transactions
            // belonging to this account
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
                        getString(
                            R.string.expensemanager
                        ),
                        Context.MODE_PRIVATE
                    )


            val selectedAccount =
                preferences.getString(
                    Constant.SELECTEDACCOUNT,
                    null
                )


            if (selectedAccount == account.name) {

                preferences.edit()
                    .remove(
                        Constant.SELECTEDACCOUNT
                    )
                    .apply()
            }


            Toast.makeText(
                requireContext(),
                getString(
                    R.string.account_deleted
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
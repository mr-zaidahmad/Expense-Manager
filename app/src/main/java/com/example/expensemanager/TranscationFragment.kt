package com.example.expensemanager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensemanager.databinding.FragmentTranscationBinding
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale


class TranscationFragment : Fragment() {

    private lateinit var binding: FragmentTranscationBinding
    private lateinit var database: Roomdatabase_UserDatabase
    private lateinit var adapter: TransactionAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTranscationBinding.inflate(
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


        // Database
        database =
            Roomdatabase_UserDatabase.getDatabase(
                requireContext()
            )


        // RecyclerView
        adapter =
            TransactionAdapter(emptyList())

        binding.TransactionRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.TransactionRecyclerView.adapter =
            adapter


        // Get selected account
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


        // No account selected
        if (selectedAccountName == null) {
            return
        }


        /*
         * Save the ViewLifecycleOwner before
         * starting the coroutine.
         */
        val lifecycleOwner =
            viewLifecycleOwner


        viewLifecycleOwner.lifecycleScope.launch {


            // Get account
            val account =
                database.userDAO()
                    .getAccountByName(
                        selectedAccountName
                    )




            // Account not found
            if (account == null) {
                return@launch
            }


            /*
             * Get current month and year
             */
            val calendar =
                Calendar.getInstance()


            val month =
                String.format(
                    Locale.getDefault(),
                    "%02d",
                    calendar.get(Calendar.MONTH) + 1
                )


            val year =
                calendar.get(Calendar.YEAR)


            /*
             * Example:
             *
             * %/08/2026%
             *
             * This gets transactions
             * from August 2026.
             */
            val monthPattern =
                "%/$month/$year%"


            // =====================================
            // GET MONTHLY INCOME
            // =====================================

            val income =
                database.transactionDAO()
                    .getMonthlyIncome(
                        account.id,
                        monthPattern
                    )


            // =====================================
            // GET MONTHLY EXPENSE
            // =====================================

            val expense =
                database.transactionDAO()
                    .getMonthlyExpense(
                        account.id,
                        monthPattern
                    )


            // =====================================
            // GET INITIAL ACCOUNT AMOUNT
            // =====================================

            val initialAmount =
                account.InitialAmount.toDouble()


            // =====================================
            // CALCULATE CURRENT BALANCE
            // =====================================

            val total =
                initialAmount + income - expense


            // =====================================
            // SHOW INCOME
            // =====================================

            binding.IncomeTotalText.text =
                formatAmount(
                    income,
                    account.Currency
                )


            // =====================================
            // SHOW EXPENSE
            // =====================================

            binding.ExpenseTotalText.text =
                "-${formatAmount(
                    expense,
                    account.Currency
                )}"


            // =====================================
            // SHOW CURRENT TOTAL
            // =====================================

            if (total < 0) {

                binding.NetTotalText.text =
                    "-${formatAmount(
                        kotlin.math.abs(total),
                        account.Currency
                    )}"

            } else {

                binding.NetTotalText.text =
                    formatAmount(
                        total,
                        account.Currency
                    )
            }


            // =====================================
            // SHOW TRANSACTIONS
            // =====================================

            database.transactionDAO()
                .getTransactionsForAccount(
                    account.id
                )
                .observe(lifecycleOwner) { transactions ->

                    adapter.updateTransactions(
                        transactions
                    )
                }
        }
    }





    // =========================================
    // FORMAT AMOUNT
    // =========================================

    private fun formatAmount(
        amount: Double,
        currency: String
    ): String {

        val formatter =
            NumberFormat.getNumberInstance(
                Locale.US
            )


        formatter.maximumFractionDigits = 2


        return "$currency ${
            formatter.format(amount)
        }"
    }
}
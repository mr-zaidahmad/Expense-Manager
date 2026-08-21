package com.example.expensemanager

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.expensemanager.databinding.FragmentStatisticsBinding
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: Roomdatabase_UserDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(
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

        database = Roomdatabase_UserDatabase.getDatabase(
            requireContext()
        )

        loadStatistics()

        // -----------------------------------------
        // OVERVIEW SHOW MORE
        // -----------------------------------------

        binding.OverviewShowMore.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.FragmentsframeLayout,
                    TranscationFragment()
                )
                .addToBackStack(getString(R.string.statistics_to_transactions))
                .commit()

            requireActivity()
                .findViewById<View>(R.id.BottomNav)
                .visibility = View.GONE
        }


        // -----------------------------------------
        // EXPENSE SHOW MORE
        // -----------------------------------------

        binding.ExpenseShowMore.setOnClickListener {

            // Detailed expense statistics can be added here later.

        }
    }


    private fun loadStatistics() {

        val preferences =
            requireActivity().getSharedPreferences(
                    "ExpenseManager",
                Context.MODE_PRIVATE
            )

        val selectedAccountName =
            preferences.getString(
       "SELCTED_ACCOUNT",
                null
            )

        if (selectedAccountName == null) {
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {

            val account =
                database.userDAO()
                    .getAccountByName(
                        selectedAccountName
                    )

            if (account == null) {
                return@launch
            }

            // -----------------------------------------
            // GET ALL INCOME
            // -----------------------------------------

            val income =
                database.transactionDAO()
                    .getTotalIncome(
                        account.id
                    )


            // -----------------------------------------
            // GET ALL EXPENSE
            // -----------------------------------------

            val expense =
                database.transactionDAO()
                    .getTotalExpense(
                        account.id
                    )


            // -----------------------------------------
            // INITIAL AMOUNT
            // -----------------------------------------

            val initialAmount =
                account.InitialAmount.toDouble()


            // -----------------------------------------
            // CURRENT BALANCE
            // -----------------------------------------

            val total =
                initialAmount +
                        income -
                        expense


            // -----------------------------------------
            // CHECK THAT VIEW STILL EXISTS
            // -----------------------------------------

            if (!isAdded || _binding == null) {
                return@launch
            }


            // -----------------------------------------
            // BALANCE UI
            // -----------------------------------------

            binding.OpeningBalanceText.text =
                formatAmount(
                    initialAmount,
                    account.Currency
                )

            binding.EndingBalanceText.text =
                formatAmount(
                    total,
                    account.Currency
                )


            // -----------------------------------------
            // OVERVIEW UI
            // -----------------------------------------

            binding.StatisticsIncomeText.text =
                formatAmount(
                    income,
                    account.Currency
                )

            binding.StatisticsExpenseText.text =
                "-${
                    formatAmount(
                        expense,
                        account.Currency
                    )
                }"

            binding.StatisticsTotalText.text =
                formatAmount(
                    total,
                    account.Currency
                )


            // -----------------------------------------
            // EXPENSE CATEGORIES
            // -----------------------------------------

            val categories =
                database.transactionDAO()
                    .getExpenseByCategory(
                        account.id
                    )


            // -----------------------------------------
            // CHECK AGAIN BEFORE UI UPDATE
            // -----------------------------------------

            if (!isAdded || _binding == null) {
                return@launch
            }

            showExpenseChart(categories)
        }
    }


    private fun showExpenseChart(
        categories: List<CategoryTotal>
    ) {

        // -----------------------------------------
        // CHECK FRAGMENT STATE
        // -----------------------------------------

        if (!isAdded || _binding == null) {
            return
        }

        val context = binding.root.context


        // -----------------------------------------
        // NO EXPENSES
        // -----------------------------------------

        if (categories.isEmpty()) {

            binding.ExpenseChart.setValues(
                emptyList()
            )

            binding.ExpenseLegend.removeAllViews()

            return
        }


        // -----------------------------------------
        // VALUES FOR DONUT
        // -----------------------------------------

        val values =
            categories.map {
                it.total
            }

        binding.ExpenseChart.setValues(
            values
        )


        // -----------------------------------------
        // REMOVE OLD LEGEND
        // -----------------------------------------

        binding.ExpenseLegend.removeAllViews()


        val total =
            values.sum()


        // -----------------------------------------
        // CREATE LEGEND
        // -----------------------------------------

        categories.forEachIndexed { index, category ->

            // Fragment might have been detached
            // while this loop was running.

            if (!isAdded || _binding == null) {
                return
            }


            val percentage =
                if (total > 0) {

                    category.total /
                            total *
                            100

                } else {
                    0.0
                }


            // -----------------------------------------
            // ROW
            // -----------------------------------------

            val row =
                LinearLayout(context)

            row.orientation =
                LinearLayout.HORIZONTAL

            row.gravity =
                Gravity.CENTER_VERTICAL


            val rowParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            rowParams.bottomMargin =
                8

            row.layoutParams =
                rowParams


            // -----------------------------------------
            // COLORED DOT
            // -----------------------------------------

            val dot =
                TextView(context)

            dot.text =
                "●"

            dot.textSize =
                17f

            dot.setTextColor(
                getChartColor(index)
            )


            // -----------------------------------------
            // CATEGORY NAME
            // -----------------------------------------

            val categoryName =
                TextView(context)

            categoryName.text =
                category.category

            categoryName.textSize =
                13f

            categoryName.setTextColor(
                Color.DKGRAY
            )


            val nameParams =
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )

            nameParams.marginStart =
                4


            // -----------------------------------------
            // PERCENTAGE
            // -----------------------------------------

            val percentageText =
                TextView(context)

            percentageText.text =
                String.format(
                    Locale.getDefault(),
                    "(%.1f%%)",
                    percentage
                )

            percentageText.textSize =
                12f

            percentageText.setTextColor(
                Color.DKGRAY
            )


            // -----------------------------------------
            // ADD DOT
            // -----------------------------------------

            row.addView(
                dot,
                LinearLayout.LayoutParams(
                    22,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )


            // -----------------------------------------
            // ADD CATEGORY
            // -----------------------------------------

            row.addView(
                categoryName,
                nameParams
            )


            // -----------------------------------------
            // ADD PERCENTAGE
            // -----------------------------------------

            row.addView(
                percentageText
            )


            // -----------------------------------------
            // ADD ROW TO LEGEND
            // -----------------------------------------

            binding.ExpenseLegend.addView(
                row
            )
        }
    }


    private fun getChartColor(
        index: Int
    ): Int {

        val colors =
            listOf(

                Color.rgb(
                    8,
                    166,
                    166
                ),

                Color.rgb(
                    145,
                    70,
                    15
                ),

                Color.rgb(
                    255,
                    176,
                    0
                ),

                Color.rgb(
                    142,
                    77,
                    232
                ),

                Color.rgb(
                    119,
                    136,
                    153
                ),

                Color.rgb(
                    76,
                    175,
                    80
                ),

                Color.rgb(
                    233,
                    30,
                    99
                ),

                Color.rgb(
                    96,
                    125,
                    139
                )
            )

        return colors[
            index % colors.size
        ]
    }


    private fun formatAmount(
        amount: Double,
        currency: String
    ): String {

        val formatter =
            NumberFormat.getNumberInstance(
                Locale.US
            )

        formatter.maximumFractionDigits =
            2

        return "$currency ${
            formatter.format(amount)
        }"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
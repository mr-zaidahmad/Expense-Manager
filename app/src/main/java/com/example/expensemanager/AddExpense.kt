package com.example.expensemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.expensemanager.databinding.FragmentAddExpenseBinding


class AddExpense : Fragment() {

    private lateinit var binding: FragmentAddExpenseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.AddexpenseBottomNav.itemIconTintList = null

        var incomeFragment =
            childFragmentManager.findFragmentByTag("INCOME") as? IncomeFragment

        var expenseFragment =
            childFragmentManager.findFragmentByTag("EXPENSE") as? ExpenseFragment


        if (incomeFragment == null) {
            incomeFragment = IncomeFragment()

            childFragmentManager.beginTransaction()
                .add(
                    R.id.AddexpenseFramelayout,
                    incomeFragment,
                    "INCOME"
                )
                .commit()
        }


        if (expenseFragment == null) {
            expenseFragment = ExpenseFragment()

            childFragmentManager.beginTransaction()
                .add(
                    R.id.AddexpenseFramelayout,
                    expenseFragment,
                    "EXPENSE"
                )
                .hide(expenseFragment)
                .commit()
        }


        binding.AddexpenseBottomNav.selectedItemId = R.id.IncomeMenu


        binding.AddexpenseBottomNav.setOnItemSelectedListener { item ->

            val currentIncomeFragment =
                childFragmentManager.findFragmentByTag("INCOME")

            val currentExpenseFragment =
                childFragmentManager.findFragmentByTag("EXPENSE")


            when (item.itemId) {

                R.id.IncomeMenu -> {

                    childFragmentManager.beginTransaction()
                        .apply {

                            currentIncomeFragment?.let {
                                show(it)
                            }

                            currentExpenseFragment?.let {
                                hide(it)
                            }

                        }
                        .commit()

                    true
                }


                R.id.ExpenseMenu -> {

                    childFragmentManager.beginTransaction()
                        .apply {

                            currentIncomeFragment?.let {
                                hide(it)
                            }

                            currentExpenseFragment?.let {
                                show(it)
                            }

                        }
                        .commit()

                    true
                }


                else -> false
            }
        }
    }
}
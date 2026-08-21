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
            childFragmentManager.findFragmentByTag(getString(R.string.income)) as? IncomeFragment

        var expenseFragment =
            childFragmentManager.findFragmentByTag(getString(R.string.expense)) as? ExpenseFragment


        if (incomeFragment == null) {
            incomeFragment = IncomeFragment()

            childFragmentManager.beginTransaction()
                .add(
                    R.id.AddexpenseFramelayout,
                    incomeFragment,
                    getString(R.string.income)
                )
                .commit()
        }


        if (expenseFragment == null) {
            expenseFragment = ExpenseFragment()

            childFragmentManager.beginTransaction()
                .add(
                    R.id.AddexpenseFramelayout,
                    expenseFragment,
                    getString(R.string.expense)
                )
                .hide(expenseFragment)
                .commit()
        }


        binding.AddexpenseBottomNav.selectedItemId = R.id.IncomeMenu


        binding.AddexpenseBottomNav.setOnItemSelectedListener { item ->

            val currentIncomeFragment =
                childFragmentManager.findFragmentByTag(getString(R.string.income))

            val currentExpenseFragment =
                childFragmentManager.findFragmentByTag(getString(R.string.expense))


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
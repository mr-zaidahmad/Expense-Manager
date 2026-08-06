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

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.AddexpenseFramelayout, IncomeFragment())
                .commit()
            binding.AddexpenseBottomNav.selectedItemId = R.id.IncomeMenu
        }

        binding.AddexpenseBottomNav.setOnItemSelectedListener { item ->

            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.IncomeMenu -> {
                    selectedFragment = IncomeFragment()
                }
                R.id.ExpenseMenu -> {
                    selectedFragment = ExpenseFragment()
                }
            }

            if (selectedFragment != null) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.AddexpenseFramelayout, selectedFragment)
                    .commit()
            }
            true
        }
    }
}
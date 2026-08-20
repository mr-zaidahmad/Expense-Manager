package com.example.expensemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.expensemanager.databinding.FragmentSettingCategoryBinding

class settingCategory : Fragment() {

    private lateinit var binding: FragmentSettingCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            FragmentSettingCategoryBinding.inflate(
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

        if (savedInstanceState == null) {

            binding.SettingBottomnavigationView.selectedItemId =
                R.id.SettingExpense

            showExpense()
        }


        binding.SettingBottomnavigationView
            .setOnItemSelectedListener { item ->

                when (item.itemId) {

                    R.id.SettingIncome -> {

                        showIncome()

                        true
                    }

                    R.id.SettingExpense -> {

                        showExpense()

                        true
                    }

                    else -> false
                }
            }
    }


    // =========================================
    // SHOW EXPENSE
    // =========================================

    private fun showExpense() {

        childFragmentManager.beginTransaction()
            .replace(
                R.id.SettingIncomeAndExpenseFramelayout,
                settingExpense()
            )
            .commit()
    }


    // =========================================
    // SHOW INCOME
    // =========================================

    private fun showIncome() {

        childFragmentManager.beginTransaction()
            .replace(
                R.id.SettingIncomeAndExpenseFramelayout,
                SettingIncome()
            )
            .commit()
    }
}
package com.example.expensemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.expensemanager.databinding.FragmentIncomeBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class IncomeFragment : Fragment() {

    private lateinit var binding: FragmentIncomeBinding
    private lateinit var database: Roomdatabase_UserDatabase

    private var selectedDateMillis: Long =
        System.currentTimeMillis()

    private var selectedHour: Int = 0
    private var selectedMinute: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            FragmentIncomeBinding.inflate(
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

        val navController =
            requireParentFragment()
                .requireParentFragment()
                .findNavController()

        val savedStateHandle =
            navController.currentBackStackEntry
                ?.savedStateHandle

        savedStateHandle
            ?.get<String>(
                getString(R.string.incomeamount)
            )
            ?.let {

                binding.AmountIncome.setText(it)
            }

        savedStateHandle
            ?.get<String>(
                getString(R.string.incomedescription)
            )
            ?.let {

                binding.DescriptionIncome.setText(it)
            }

        savedStateHandle
            ?.get<String>(
                getString(R.string.incomewallet)
            )
            ?.let {

                binding.WalletIncome.setText(it)
            }

        database =
            Roomdatabase_UserDatabase.getDatabase(
                requireContext()
            )

        // =========================================
        // RESTORE SELECTED CATEGORY
        // =========================================

        savedStateHandle
            ?.getLiveData<String>(
                getString(R.string.selectedcategory)
            )
            ?.observe(viewLifecycleOwner) { category ->

                binding.categoryText.text =
                    category
            }

        // =========================================
        // DATE / TIME
        // =========================================

        val now =
            Calendar.getInstance()

        selectedHour =
            now.get(Calendar.HOUR_OF_DAY)

        selectedMinute =
            now.get(Calendar.MINUTE)

        updateDateTimeText()

        binding.tvSelectedDate.setOnClickListener {
            showDatePicker()
        }

        // =========================================
        // CATEGORY
        // =========================================

        binding.categoryContainerIncome.setOnClickListener {

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.incomeamount),
                    binding.AmountIncome.text.toString()
                )

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.incomedescription),
                    binding.DescriptionIncome.text.toString()
                )

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.incomewallet),
                    binding.WalletIncome.text.toString()
                )

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.categorytype),
                    getString(R.string.income)
                )

            navController.navigate(
                R.id.action_addExpense_to_categories
            )
        }

        // =========================================
        // SAVE
        // =========================================

        binding.saveButton.setOnClickListener {

            saveIncome()
        }
    }

    private fun saveIncome() {

        val amountText =
            binding.AmountIncome
                .text
                .toString()
                .trim()

        if (amountText.isEmpty()) {

            binding.AmountIncome.error =
                getString(
                    R.string.enter_amount_to_continue
                )

            return
        }

        val amount =
            amountText.toDoubleOrNull()

        if (amount == null || amount <= 0) {

            binding.AmountIncome.error =
                getString(
                    R.string.please_enter_a_valid_amount
                )

            return
        }

        val description =
            binding.DescriptionIncome
                .text
                .toString()
                .trim()

        val wallet =
            binding.WalletIncome
                .text
                .toString()
                .trim()

        val category =
            binding.categoryText
                .text
                .toString()
                .trim()

        val date =
            binding.tvSelectedDate
                .text
                .toString()
                .trim()

        // =========================================
        // GET SELECTED ACCOUNT
        // =========================================

        val preferences =
            requireActivity().getSharedPreferences(
                Constant.PREFERENCESNAME,
                android.content.Context.MODE_PRIVATE
            )

        val selectedAccountName =
            preferences.getString(
                Constant.SELECTEDACCOUNT,
                null
            )

        if (selectedAccountName == null) {

            Toast.makeText(
                requireContext(),
                getString(
                    R.string.please_select_an_account_first
                ),
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        viewLifecycleOwner.lifecycleScope.launch {

            val account =
                database.userDAO()
                    .getAccountByName(
                        selectedAccountName
                    )

            if (account == null) {

                Toast.makeText(
                    requireContext(),
                    getString(
                        R.string.account_not_found
                    ),
                    Toast.LENGTH_SHORT
                ).show()

                return@launch
            }

            val transaction =
                RoomdatabaseTransaction(

                    accountId = account.id,

                    type = getString(
                        R.string.income
                    ),

                    amount = amount,

                    description = description,

                    wallet = wallet,

                    category = category,

                    date = date
                )

            database.transactionDAO()
                .insertTransaction(
                    transaction
                )

            Toast.makeText(
                requireContext(),
                getString(
                    R.string.income_saved_successfully
                ),
                Toast.LENGTH_SHORT
            ).show()

            findNavController()
                .popBackStack()
        }
    }

    // =========================================
    // DATE PICKER
    // =========================================

    private fun showDatePicker() {

        val datePicker =
            MaterialDatePicker.Builder
                .datePicker()
                .setSelection(
                    selectedDateMillis
                )
                .setTitleText(
                    getString(
                        R.string.select_date
                    )
                )
                .build()

        datePicker
            .addOnPositiveButtonClickListener { millis ->

                selectedDateMillis =
                    millis

                showTimePicker()
            }

        datePicker.show(
            childFragmentManager,
            getString(
                R.string.date_picker
            )
        )
    }

    // =========================================
    // TIME PICKER
    // =========================================

    private fun showTimePicker() {

        val timePicker =
            MaterialTimePicker.Builder()
                .setTimeFormat(
                    TimeFormat.CLOCK_12H
                )
                .setHour(
                    selectedHour
                )
                .setMinute(
                    selectedMinute
                )
                .setTitleText(
                    getString(
                        R.string.select_time
                    )
                )
                .build()

        timePicker
            .addOnPositiveButtonClickListener {

                selectedHour =
                    timePicker.hour

                selectedMinute =
                    timePicker.minute

                updateDateTimeText()
            }

        timePicker.show(
            childFragmentManager,
            getString(
                R.string.time_picker
            )
        )
    }

    // =========================================
    // DATE / TIME TEXT
    // =========================================

    private fun updateDateTimeText() {

        val calendar =
            Calendar.getInstance()

        calendar.timeInMillis =
            selectedDateMillis

        calendar.set(
            Calendar.HOUR_OF_DAY,
            selectedHour
        )

        calendar.set(
            Calendar.MINUTE,
            selectedMinute
        )

        val dateFormat =
            SimpleDateFormat(
                getString(
                    R.string.dd_mm_yyyy_hh_mm_a
                ),
                Locale.getDefault()
            )

        binding.tvSelectedDate.text =
            dateFormat.format(
                calendar.time
            )
    }
}
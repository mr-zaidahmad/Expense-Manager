package com.example.expensemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.expensemanager.databinding.FragmentExpenseBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ExpenseFragment : Fragment() {

    private lateinit var binding: FragmentExpenseBinding
    private lateinit var database: Roomdatabase_UserDatabase

    // Stores the date the user picked, in milliseconds (this is how Android represents dates internally)
    // Defaults to right now, in case the user never opens the picker
    private var selectedDateMillis: Long = System.currentTimeMillis()

    // Stores the hour/minute the user picked separately from the date,
    // because MaterialDatePicker only gives you a date, not a time
    private var selectedHour: Int = 0
    private var selectedMinute: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Roomdatabase_UserDatabase.getDatabase(requireContext())


        val navController = requireParentFragment()
            .requireParentFragment()
            .findNavController()

        val savedStateHandle =
            navController.currentBackStackEntry?.savedStateHandle


        // Restore amount
        savedStateHandle?.get<String>("expenseAmount")?.let {
            binding.AmountIncome.setText(it)
        }

        // Restore description
        savedStateHandle?.get<String>("expenseDescription")?.let {
            binding.DescriptionIncome.setText(it)
        }

        // Restore wallet
        savedStateHandle?.get<String>("expenseWallet")?.let {
            binding.WalletIncome.setText(it)
        }


        // Restore selected category
        savedStateHandle
            ?.getLiveData<String>("selectedCategory")
            ?.observe(viewLifecycleOwner) { category ->

                binding.categoryText.text = category
            }


        // Get the device's current date and time as soon as the screen opens
        val now = Calendar.getInstance()
        selectedHour = now.get(Calendar.HOUR_OF_DAY)
        selectedMinute = now.get(Calendar.MINUTE)

        // Show "today's date, current time" as the default text before user taps anything
        updateDateTimeText()

        // When user taps the gray date box, start the picker flow
        binding.tvSelectedDate.setOnClickListener {
            showDatePicker()
        }


        binding.categoryContainerExpense.setOnClickListener {

            // Save the current values before opening Category
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(
                    "expenseAmount",
                    binding.AmountIncome.text.toString()
                )

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(
                    "expenseDescription",
                    binding.DescriptionIncome.text.toString()
                )

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(
                    "expenseWallet",
                    binding.WalletIncome.text.toString()
                )

            // Tell Categories that we are coming from Expense
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(
                    "categoryType",
                    "EXPENSE"
                )

            navController.navigate(
                R.id.action_addExpense_to_categories
            )
        }


        binding.saveButton.setOnClickListener {
            saveExpense()
        }
    }


    private fun saveExpense() {

        val amountText = binding.AmountIncome.text.toString().trim()

        if (amountText.isEmpty()) {
            binding.AmountIncome.error = "Enter Amount to Continue"
            return
        }

        val amount = amountText.toDoubleOrNull()

        if (amount == null || amount <= 0) {
            binding.AmountIncome.error = "Please Enter a Valid amount"
            return
        }


        val description =
            binding.DescriptionIncome.text.toString().trim()

        val wallet =
            binding.WalletIncome.text.toString().trim()

        val category =
            binding.categoryText.text.toString().trim()

        val date =
            binding.tvSelectedDate.text.toString().trim()


        val preferences = requireActivity().getSharedPreferences(
            "ExpenseManager",
            android.content.Context.MODE_PRIVATE
        )

        val selectedAccountName =
            preferences.getString(
                "SELECTED_ACCOUNT",
                null
            )


        if (selectedAccountName == null) {

            Toast.makeText(
                requireContext(),
                "Please Select an account First",
                Toast.LENGTH_SHORT
            ).show()

            return
        }


        // Save everything in Room
        viewLifecycleOwner.lifecycleScope.launch {

            // Find selected account
            val account =
                database.userDAO()
                    .getAccountByName(selectedAccountName)


            if (account == null) {

                Toast.makeText(
                    requireContext(),
                    "Account not found",
                    Toast.LENGTH_SHORT
                ).show()

                return@launch
            }


            // Create transaction
            val transaction =
                RoomdatabaseTransaction(

                    accountId = account.id,

                    type = "EXPENSE",

                    amount = amount,

                    description = description,

                    wallet = wallet,

                    category = category,

                    date = date
                )


            // Insert transaction into database
            database.transactionDAO()
                .insertTransaction(transaction)


            Toast.makeText(
                requireContext(),
                "Expense saved successfully",
                Toast.LENGTH_SHORT
            ).show()


            // Go back after saving
            findNavController().popBackStack()
        }
    }


    private fun showDatePicker() {

        // Build a Material-style calendar date picker dialog
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(selectedDateMillis)   // pre-select whatever date was chosen before (or today, first time)
            .setTitleText("Select Date")
            .build()

        // This runs ONLY when user taps "OK" on the date picker (not if they cancel)
        datePicker.addOnPositiveButtonClickListener { millis ->
            selectedDateMillis = millis   // save the date they picked

            // Immediately open the time picker next, so user picks date then time in one flow
            showTimePicker()
        }
        // Actually display the date picker
        // "childFragmentManager" is used because this dialog lives inside a fragment, not an activity
        // "DATE_PICKER" is just a tag name Android uses internally to track the dialog
        datePicker.show(childFragmentManager, "DATE_PICKER")
    }


    private fun showTimePicker() {

        // Build a Material-style clock time picker dialog
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)   // 12-hour format with AM/PM (use CLOCK_24H if you want 24-hour instead)
            .setHour(selectedHour)      // pre-fill with previously selected hour (or current hour, first time)
            .setMinute(selectedMinute)  // pre-fill with previously selected minute
            .setTitleText("Select Time")
            .build()

        // This runs ONLY when user taps "OK" on the time picker
        timePicker.addOnPositiveButtonClickListener {
            selectedHour = timePicker.hour       // read back the hour user picked
            selectedMinute = timePicker.minute   // read back the minute user picked

            // Now that we have both date AND time, update the text box on screen
            updateDateTimeText()
        }

        timePicker.show(childFragmentManager, "TIME_PICKER")
    }


    private fun updateDateTimeText() {

        // Create a Calendar object to combine the separate date and time values into one
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDateMillis   // set the date part

        // Overwrite just the hour/minute part with what the time picker gave us
        calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
        calendar.set(Calendar.MINUTE, selectedMinute)

        // Define how the final text should look: e.g. "08/06/2026   03:58 PM"
        val dateFormat = SimpleDateFormat(
            "dd/MM/yyyy   hh:mm a",
            Locale.getDefault()
        )

        // Convert the Calendar into a formatted string and show it in the gray box
        binding.tvSelectedDate.text = dateFormat.format(calendar.time)
    }
}
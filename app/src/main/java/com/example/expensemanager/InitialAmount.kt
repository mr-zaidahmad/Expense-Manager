package com.example.expensemanager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.expensemanager.databinding.FragmentInitialAmountBinding
import kotlinx.coroutines.launch

class InitialAmount : Fragment() {

    private lateinit var binding: FragmentInitialAmountBinding
    private lateinit var database: Roomdatabase_UserDatabase

    private var accountName: String? = null
    private var currency: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInitialAmountBinding.inflate(
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

        accountName =
            arguments?.getString(getString(R.string.account_name))

        currency =
            arguments?.getString(getString(R.string.currency))


        // Push content up when keyboard opens
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->

            val imeHeight =
                insets.getInsets(
                    WindowInsetsCompat.Type.ime()
                ).bottom

            v.setPadding(
                20,
                20,
                20,
                imeHeight
            )

            insets
        }


        // DONE BUTTON
        binding.btnDone.setOnClickListener {

            val amountText =
                binding.EditTextAmount
                    .text
                    .toString()
                    .trim()

            val amount =
                if (amountText.isEmpty()) {
                    0
                } else {
                    amountText.toInt()
                }

            saveAccount(amount)
        }


        // SKIP BUTTON
        binding.tvSkip.setOnClickListener {

            saveAccount(0)
        }
    }


    private fun saveAccount(amount: Int) {

        val name =
            accountName ?: return

        val selectedCurrency =
            currency ?: return


        // Create a NEW account object
        val user =
            RoomdatabaseUserdata(
                name = name,
                Currency = selectedCurrency,
                InitialAmount = amount
            )


        lifecycleScope.launch {

            // Save this account to Room
            database.userDAO()
                .InsertuserData(user)


            // Make the newly created account
            // the currently selected account
            requireActivity()
                .getSharedPreferences(
                    getString(R.string.expensemanager),
                    Context.MODE_PRIVATE
                )
                .edit()
                .putString(
                    Constant.SELECTEDACCOUNT,
                    name
                )
                .apply()


            Toast.makeText(
                requireContext(),
                getString(R.string.saved_successfully),
                Toast.LENGTH_SHORT
            ).show()


            // Go back to Home
            findNavController()
                .popBackStack(
                    R.id.HomeContainerFragment,
                    false
                )
        }
    }
}
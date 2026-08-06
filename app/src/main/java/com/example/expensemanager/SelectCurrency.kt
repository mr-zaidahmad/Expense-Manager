package com.example.expensemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.expensemanager.databinding.FragmentSelectCurrencyBinding


class SelectCurrency : Fragment() {

    private lateinit var binding: FragmentSelectCurrencyBinding
    private var selectCurrency = ""
    private var accountName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSelectCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.isEnabled = false

        accountName = arguments?.getString("ACCOUNT_NAME")

        val currencyLIst = arrayOf(
            "Select Currency",
            "PKR",
            "USDT",
            "EURO"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            currencyLIst
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.SpinnerCurrency.adapter = adapter

        binding.SpinnerCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
                selectCurrency = currencyLIst[p2]
                binding.btnNext.isEnabled = p2 != 0
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.btnNext.setOnClickListener {

            findNavController().navigate(
                R.id.action_selectCurrency_to_initialAmount,
                bundleOf(
                    "ACCOUNT_NAME" to accountName,
                    "CURRENCY" to selectCurrency
                )
            )
        }
    }
}
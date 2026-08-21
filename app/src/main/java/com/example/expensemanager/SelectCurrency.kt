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

        accountName = arguments?.getString(getString(R.string.account_name))

        val currencyLIst = arrayOf(
            getString(R.string.select_currency),
            getString(R.string.pkr),
            getString(R.string.usdt),
            getString(R.string.euro)
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
                    getString(R.string.account_name) to accountName,
                    getString(R.string.currency) to selectCurrency
                )
            )
        }
    }
}
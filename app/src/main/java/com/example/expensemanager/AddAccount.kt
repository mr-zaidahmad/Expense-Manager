package com.example.expensemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.expensemanager.databinding.FragmentAddAccountBinding


class AddAccount : Fragment() {

    private lateinit var binding : FragmentAddAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.isEnabled = false

        binding.EditTextAddAcount.addTextChangedListener { editable ->
            val accountname = editable.toString().trim()

            binding.btnNext.isEnabled = accountname.isNotEmpty()

            binding.btnNext.alpha =
                if (accountname.isNotEmpty()) 1f else 0.5f
        }

        binding.btnNext.setOnClickListener {
            val accountName = binding.EditTextAddAcount.text.toString().trim()

            findNavController().navigate(
                R.id.action_addAccount_to_selectCurrency,
                bundleOf(getString(R.string.account_name) to accountName)
            )
        }
    }
}
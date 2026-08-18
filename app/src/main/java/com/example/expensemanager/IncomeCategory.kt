package com.example.expensemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.expensemanager.databinding.ActivityRoomDatabaseMainBinding
import com.example.expensemanager.databinding.FragmentIncomeCategoryBinding


class IncomeCategory : Fragment() {

 private lateinit var binding: FragmentIncomeCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= FragmentIncomeCategoryBinding.inflate(inflater, container, false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allowanceContainer.setOnClickListener {

            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Allowance")

            findNavController().popBackStack()
        }

        binding.AwardContainer.setOnClickListener {

            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Award")

            findNavController().popBackStack()
        }

        binding.BonusContainer.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Bonus")

            findNavController().popBackStack()
        }

        binding.DividendContainer.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Dividend")

            findNavController().popBackStack()
        }

        binding.InvestmentContainer.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Investment")

            findNavController().popBackStack()
        }

        binding.lotteryContainer.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Lottery")

            findNavController().popBackStack()
        }

        binding.salaryContainer.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Salary")

            findNavController().popBackStack()
        }

        binding.TipsContainer.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Tips")

            findNavController().popBackStack()
        }

        binding.BusinessContainer.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Business")

            findNavController().popBackStack()
        }

        binding.OthersContainer.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Others")

            findNavController().popBackStack()
        }

    }

}
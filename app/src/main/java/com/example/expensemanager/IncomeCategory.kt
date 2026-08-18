package com.example.expensemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.expensemanager.databinding.FragmentIncomeCategoryBinding


class IncomeCategory : Fragment() {

    private lateinit var binding: FragmentIncomeCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentIncomeCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allowanceContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Allowance")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.AwardContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Award")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.BonusContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Bonus")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.DividendContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Dividend")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.InvestmentContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Investment")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.lotteryContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Lottery")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.salaryContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Salary")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.TipsContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Tips")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.BusinessContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Business")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.OthersContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Others")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }
    }
}
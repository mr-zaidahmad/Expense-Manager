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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentIncomeCategoryBinding.inflate(
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

        binding.allowanceContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.selectedcategory),
                    getString(R.string.allowance)
                )

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }


        binding.AwardContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.selectedcategory),
                    getString(R.string.award)
                )

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }


        binding.BonusContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.selectedcategory),
                    getString(R.string.bonus)
                )

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }


        binding.DividendContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.selectedcategory),
                    getString(R.string.dividend)
                )

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }


        binding.InvestmentContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.selectedcategory),
                    getString(R.string.investment)
                )

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }


        binding.lotteryContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.selectedcategory),
                    getString(R.string.lottery)
                )

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }


        binding.salaryContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.selectedcategory),
                    getString(R.string.salary)
                )

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }


        binding.TipsContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.selectedcategory),
                    getString(R.string.tips)
                )

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }


        binding.BusinessContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.selectedcategory),
                    getString(R.string.business)
                )

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }


        binding.OthersContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    getString(R.string.selectedcategory),
                    getString(R.string.others)
                )

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }
    }
}
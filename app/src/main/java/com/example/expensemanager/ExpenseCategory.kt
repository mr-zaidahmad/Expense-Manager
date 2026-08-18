package com.example.expensemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.expensemanager.databinding.FragmentExpenseCategoryBinding


class ExpenseCategory : Fragment() {

    private lateinit var binding: FragmentExpenseCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExpenseCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.BillsContainer.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Bills")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.ClothCategory.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Cloth")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.EducationCategory.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Education")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.EntertainmentCategory.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Entertainment")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.FitnessCategory.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Fitness")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.FoodCategory.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Food")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.GiftsCategory.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Gifts")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.HealthCategory.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Health")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.FurnitureCategory.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Furniture")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }

        binding.PetCategory.setOnClickListener {

            requireParentFragment()
                .findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory", "Pet")

            requireParentFragment()
                .findNavController()
                .popBackStack()
        }
    }
}
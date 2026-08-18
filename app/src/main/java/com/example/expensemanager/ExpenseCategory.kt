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
        binding= FragmentExpenseCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.BillsContainer.setOnClickListener {
                  findNavController().previousBackStackEntry
                      ?.savedStateHandle
                      ?.set("selectedCategory","Bills")
                  findNavController().popBackStack()

        }
        binding.ClothCategory.setOnClickListener {
                findNavController().previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("selectedCategory","Cloth")
                findNavController().popBackStack()
        }

        binding.EducationCategory.setOnClickListener {
              findNavController().previousBackStackEntry
                  ?.savedStateHandle
                  ?.set("selectedCategory","Education")
            findNavController().popBackStack()
        }

        binding.EntertainmentCategory.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory","Entertainment")
            findNavController().popBackStack()
        }

        binding.FitnessCategory.setOnClickListener {
                   findNavController().previousBackStackEntry
                       ?.savedStateHandle
                       ?.set("selectedCategory","Fitness")
            findNavController().popBackStack()
        }

        binding.FoodCategory.setOnClickListener {
                   findNavController().previousBackStackEntry
                       ?.savedStateHandle
                       ?.set("selectedCategory","Food")
            findNavController().popBackStack()
        }

        binding.GiftsCategory.setOnClickListener {
              findNavController().previousBackStackEntry
                  ?.savedStateHandle
                  ?.set("selectedCategory","Gifts")
            findNavController().popBackStack()
        }

        binding.HealthCategory.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory","Health")
            findNavController().popBackStack()
        }

        binding.FurnitureCategory.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory","Health")
            findNavController().popBackStack()
        }

        binding.PetCategory.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedCategory","Health")
            findNavController().popBackStack()
        }




    }

}
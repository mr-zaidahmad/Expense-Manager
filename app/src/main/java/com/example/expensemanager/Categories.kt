package com.example.expensemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.expensemanager.databinding.FragmentCategoriesBinding


class Categories : Fragment() {


    private lateinit var binding : FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding=  FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState==null){
            childFragmentManager.beginTransaction()
                .replace(R.id.CategoryFrameLayout, IncomeCategory())
                .commit()
        }
        binding.CategoryDrawer.setOnItemSelectedListener{
            item ->
            var selectedFragment : Fragment? = null

            when(item.itemId){
                R.id.CategoryIncome ->{
                    selectedFragment= IncomeCategory()
                }
                R.id.CategoryExpense ->{
                    selectedFragment = ExpenseCategory()
                }

            }
            if (selectedFragment!=null){
                childFragmentManager.beginTransaction()
                    .replace(R.id.CategoryFrameLayout,selectedFragment)
                    .commit()
            }
            true
        }
    }

}
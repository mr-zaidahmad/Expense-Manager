package com.example.expensemanager

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanager.databinding.FragmentHomeContainerBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeContainerFragment : Fragment() {

    private var _binding: FragmentHomeContainerBinding? = null
    private val binding get() = _binding!!

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    private lateinit var database: Roomdatabase_UserDatabase

    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.BottomNav.itemIconTintList = null

        database = Roomdatabase_UserDatabase.getDatabase(requireContext())

        preferences = requireActivity().getSharedPreferences("ExpenseManager", AppCompatActivity.MODE_PRIVATE)

        binding.ToolbarAccount.text =
            preferences.getString("SELECTED_ACCOUNT", "Account")

        setupToolbar()

        if (savedInstanceState == null) {
            loadFragment(TranscationFragment())
            binding.transactionToolbar.visibility = View.VISIBLE
        }

        binding.BottomNav.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_transcation -> {
                    loadFragment(TranscationFragment())
                    binding.transactionToolbar.visibility = View.VISIBLE
                }

                R.id.nav_addExpense -> {
                    findNavController().navigate(R.id.action_HomeContainerFragment_to_addExpense)
                    return@setOnItemSelectedListener false
                }

                R.id.nav_statictics -> {
                    loadFragment(StatisticsFragment())
                    binding.transactionToolbar.visibility = View.GONE
                }
            }

            true
        }

        binding.NavigationMenu.setNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.AccountDrawer ->
                    Toast.makeText(requireContext(), "Account", Toast.LENGTH_SHORT).show()

                R.id.CurrencyDrawer ->
                    Toast.makeText(requireContext(), "Currency", Toast.LENGTH_SHORT).show()

                R.id.CategoryDrawer ->
                    Toast.makeText(requireContext(), "Category", Toast.LENGTH_SHORT).show()

                R.id.BudgetDrawer ->
                    Toast.makeText(requireContext(), "Budget", Toast.LENGTH_SHORT).show()

                R.id.GoalDrawer ->
                    Toast.makeText(requireContext(), "Goal", Toast.LENGTH_SHORT).show()

                R.id.ThemeMode ->
                    Toast.makeText(requireContext(), "Theme", Toast.LENGTH_SHORT).show()

                R.id.language ->
                    Toast.makeText(requireContext(), "Language", Toast.LENGTH_SHORT).show()
            }

            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }
    }

    private fun setupToolbar() {

        drawerLayout = binding.Drawerlayout
        toolbar = binding.transactionToolbar

        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        toolbar.setNavigationIcon(R.drawable.setting)

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.ToolbarAccount.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun loadFragment(fragment: Fragment) {

        childFragmentManager.beginTransaction()
            .replace(R.id.FragmentsframeLayout, fragment)
            .commit()
    }

    private fun showBottomSheet() {

        val dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvAccounts)

        val adapter = AccountAdapter(emptyList()) { account ->

            // Save selected account
            preferences.edit()
                .putString("SELECTED_ACCOUNT", account.name)
                .apply()

            // Change account name in toolbar
            binding.ToolbarAccount.text = account.name

            // Reload transaction screen so it loads
            // the selected account's data
            loadFragment(TranscationFragment())

            dialog.dismiss()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        database.userDAO().getAllAccount().observe(viewLifecycleOwner) { accounts ->
            adapter.updateList(accounts)
        }

        dialog.setContentView(view)

        view.findViewById<LinearLayout>(R.id.AddaccountLinear).setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.action_HomeContainerFragment_to_addAccount)
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.expensemanager

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeContainerBinding.inflate(
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

        binding.BottomNav.itemIconTintList = null

        database =
            Roomdatabase_UserDatabase.getDatabase(
                requireContext()
            )

        preferences =
            requireActivity().getSharedPreferences(
                "ExpenseManager",
                AppCompatActivity.MODE_PRIVATE
            )


        childFragmentManager.addOnBackStackChangedListener {

            if (childFragmentManager.backStackEntryCount == 0) {

                binding.BottomNav.visibility =
                    View.VISIBLE
            }
        }


        binding.ToolbarAccount.text =
            preferences.getString(
                "SELECTED_ACCOUNT",
                "Account"
            )


        setupToolbar()

        updateThemeDrawerText()


        if (savedInstanceState == null) {

            loadFragment(
                TranscationFragment()
            )

            binding.transactionToolbar.visibility =
                View.VISIBLE
        }


        // =========================================
        // BOTTOM NAVIGATION
        // =========================================

        binding.BottomNav.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_transcation -> {

                    loadFragment(
                        TranscationFragment()
                    )

                    binding.transactionToolbar.visibility =
                        View.VISIBLE
                }


                R.id.nav_addExpense -> {

                    findNavController().navigate(
                        R.id.action_HomeContainerFragment_to_addExpense
                    )

                    return@setOnItemSelectedListener false
                }


                R.id.nav_statictics -> {

                    loadFragment(
                        StatisticsFragment()
                    )

                    binding.transactionToolbar.visibility =
                        View.GONE
                }
            }

            true
        }


        // =========================================
        // DRAWER MENU
        // =========================================

        binding.NavigationMenu.setNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.AccountDrawer -> {

                    findNavController().navigate(
                        R.id.action_HomeContainerFragment_to_settingAccount
                    )
                }


                R.id.CategoryDrawer -> {

                    findNavController().navigate(
                        R.id.action_HomeContainerFragment_to_settingCategory
                    )
                }


                R.id.ThemeMode -> {

                    showThemeDialog()
                }


                R.id.language -> {

                    findNavController().navigate(
                        R.id.action_HomeContainerFragment_to_settingLanguage
                    )
                }
            }


            drawerLayout.closeDrawer(
                GravityCompat.START
            )

            true
        }
    }


    // =========================================
    // TOOLBAR
    // =========================================

    private fun setupToolbar() {

        drawerLayout =
            binding.Drawerlayout

        toolbar =
            binding.transactionToolbar


        (requireActivity() as AppCompatActivity)
            .setSupportActionBar(toolbar)


        (requireActivity() as AppCompatActivity)
            .supportActionBar
            ?.setDisplayShowTitleEnabled(false)


        toolbar.setNavigationIcon(
            R.drawable.setting
        )


        toolbar.setNavigationOnClickListener {

            drawerLayout.openDrawer(
                GravityCompat.START
            )
        }


        binding.ToolbarAccount.setOnClickListener {

            showBottomSheet()
        }
    }


    // =========================================
    // LOAD FRAGMENT
    // =========================================

    private fun loadFragment(
        fragment: Fragment
    ) {

        childFragmentManager.beginTransaction()
            .replace(
                R.id.FragmentsframeLayout,
                fragment
            )
            .commit()
    }


    // =========================================
    // ACCOUNT BOTTOM SHEET
    // =========================================

    private fun showBottomSheet() {

        val dialog =
            BottomSheetDialog(
                requireContext()
            )


        val view =
            layoutInflater.inflate(
                R.layout.bottom_sheet_layout,
                null
            )


        val recyclerView =
            view.findViewById<RecyclerView>(
                R.id.rvAccounts
            )


        val adapter =
            AccountAdapter(
                emptyList(),

                onClick = { account ->

                    preferences.edit()
                        .putString(
                            "SELECTED_ACCOUNT",
                            account.name
                        )
                        .apply()


                    binding.ToolbarAccount.text =
                        account.name


                    loadFragment(
                        TranscationFragment()
                    )


                    dialog.dismiss()
                }
            )


        recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext()
            )


        recyclerView.adapter =
            adapter


        database.userDAO()
            .getAllAccount()
            .observe(viewLifecycleOwner) { accounts ->

                adapter.updateList(
                    accounts
                )
            }


        dialog.setContentView(view)


        view.findViewById<LinearLayout>(
            R.id.AddaccountLinear
        ).setOnClickListener {

            dialog.dismiss()

            findNavController().navigate(
                R.id.action_HomeContainerFragment_to_addAccount
            )
        }


        dialog.show()
    }


    // =========================================
    // UPDATE THEME TEXT IN DRAWER
    // =========================================

    private fun updateThemeDrawerText() {

        val themeItem =
            binding.NavigationMenu.menu.findItem(
                R.id.ThemeMode
            )

        val actionView =
            themeItem.actionView

        val themeValue =
            actionView?.findViewById<TextView>(
                R.id.DrawerThemeValue
            )

        themeValue?.text =
            ThemeManager.getThemeDisplayName(
                requireContext()
            )
    }


    // =========================================
    // THEME DIALOG
    // =========================================

    private fun showThemeDialog() {

        val currentTheme =
            ThemeManager.getTheme(
                requireContext()
            )


        var selectedTheme =
            currentTheme


        val themeOptions =
            arrayOf(
                "System default",
                "Light",
                "Dark"
            )


        val checkedItem =
            when (currentTheme) {

                ThemeManager.LIGHT -> 1

                ThemeManager.DARK -> 2

                else -> 0
            }


        val dialog =
            AlertDialog.Builder(
                requireContext()
            )
                .setTitle("Theme")
                .setSingleChoiceItems(
                    themeOptions,
                    checkedItem
                ) { _, which ->

                    selectedTheme =
                        when (which) {

                            1 -> ThemeManager.LIGHT

                            2 -> ThemeManager.DARK

                            else -> ThemeManager.SYSTEM
                        }
                }
                .setNegativeButton(
                    "CANCEL",
                    null
                )
                .setPositiveButton(
                    "DONE",
                    null
                )
                .create()


        dialog.setOnShowListener {

            dialog.getButton(
                AlertDialog.BUTTON_POSITIVE
            ).setOnClickListener {

                ThemeManager.saveTheme(
                    requireContext(),
                    selectedTheme
                )


                updateThemeDrawerText()


                dialog.dismiss()
            }
        }


        dialog.show()
    }


    // =========================================
    // CLEAN UP
    // =========================================

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null
    }
}
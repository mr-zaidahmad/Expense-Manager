package com.example.expensemanager

import android.app.Application

class ExpenseManagerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ThemeManager.applySavedTheme(this)
    }
}
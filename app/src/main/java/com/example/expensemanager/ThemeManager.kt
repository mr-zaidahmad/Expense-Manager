package com.example.expensemanager

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    private const val PREF_NAME = "ExpenseManager"
    private const val THEME_KEY = "APP_THEME"

    const val SYSTEM = "SYSTEM"
    const val LIGHT = "LIGHT"
    const val DARK = "DARK"


    fun getTheme(context: Context): String {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        return preferences.getString(
            THEME_KEY,
            SYSTEM
        ) ?: SYSTEM
    }


    fun getThemeDisplayName(context: Context): String {

        return when (getTheme(context)) {

            LIGHT -> "Light"

            DARK -> "Dark"

            else -> "System default"
        }
    }


    fun saveTheme(
        context: Context,
        theme: String
    ) {

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(
                THEME_KEY,
                theme
            )
            .apply()

        applyTheme(theme)
    }


    fun applyTheme(theme: String) {

        when (theme) {

            LIGHT -> {

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }

            DARK -> {

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            }

            else -> {

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                )
            }
        }
    }


    fun applySavedTheme(context: Context) {

        val savedTheme = getTheme(context)

        applyTheme(savedTheme)
    }
}
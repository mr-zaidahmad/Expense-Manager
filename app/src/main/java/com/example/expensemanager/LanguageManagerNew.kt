package com.example.expensemanager

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class LanguageManagerNew {

    companion object {
        fun setLocale(context: Context) {

            val preferences =
                context.getSharedPreferences(
                    Constant.PREFERENCESNAME,
                    AppCompatActivity.MODE_PRIVATE
                )

            val languageSaved = preferences.getString(
                Constant.APPLANGUAGUES,
                "en"
            )

            Log.e("LanguageManagerNew", "setLocale: 17 $languageSaved")

            val locale = Locale(languageSaved)
            Locale.setDefault(locale)

            val resources = context.resources
            val config = Configuration(resources.configuration)

            config.setLocale(locale)

            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }
}

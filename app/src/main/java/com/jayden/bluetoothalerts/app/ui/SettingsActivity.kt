package com.jayden.bluetoothalerts.app.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.action == Intent.ACTION_APPLICATION_PREFERENCES) {
            val mainActivityIntent = Intent(applicationContext, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra(EXTRA_INIT, Screen.SETTINGS)
            }
            Log.i(TAG, "Starting MainActivity")
            startActivity(mainActivityIntent)
            finish()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

    companion object {
        private const val TAG = "SettingsActivity"
        const val EXTRA_INIT = "init"
    }
}
package com.example.myapplication

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

class SimChangeReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == "android.intent.action.SIM_STATE_CHANGED") {
            // You can also use specific states like SIM_STATE_ABSENT or SIM_STATE_READY
            when (val simState = intent.getStringExtra("ss")) {
                TelephonyManager.SIM_STATE_ABSENT.toString() -> {
                    Log.i("SimChangeReceiver", "SIM absent")
                    // Handle SIM absent case if needed
                }
                "READY" -> {
                    Log.i("SimChangeReceiver", "SIM ready")
                    checkSimFingerprint(context)

                }
                else -> {
                    Log.i("SimChangeReceiver", "SIM state changed: $simState")

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkSimFingerprint(context: Context) {
        try {
            // Check for the necessary permissions
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(context, "Permissions required for SIM detection.", Toast.LENGTH_LONG).show()
                return
            }

            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val subscriptionManager = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

            // Get the active subscription information
            val activeSubscriptionInfoList = subscriptionManager.activeSubscriptionInfoList

            val sharedPreferences = context.getSharedPreferences("simData", Context.MODE_PRIVATE)

            activeSubscriptionInfoList?.forEach { subscriptionInfo ->
                val simSlotIndex = subscriptionInfo.simSlotIndex

                val simFingerprint = subscriptionManager.getPhoneNumber(SubscriptionManager.DEFAULT_SUBSCRIPTION_ID)
                val savedSimFingerprint = sharedPreferences.getString("simFingerprint_$simSlotIndex", null)
                if (savedSimFingerprint.isNullOrEmpty()) {
                    // First time storing the SIM fingerprint for this slot
                    sharedPreferences.edit().putString("simFingerprint_$simSlotIndex", simFingerprint).apply()
                    Log.i("checkSimFingerprint", "SIM $simFingerprint data stored.")
                    Toast.makeText(context, "SIM $simFingerprint data stored.", Toast.LENGTH_LONG).show()
                } else if (savedSimFingerprint != simFingerprint) {
                    // SIM fingerprint has changed, indicating a possible SIM swap
                    Log.i("checkSimFingerprint", "SIM swap detected on SIM $simFingerprint!")
                    Toast.makeText(context, "SIM swap detected on SIM $simFingerprint!", Toast.LENGTH_LONG).show()
                    sharedPreferences.edit().putString("simFingerprint_$simSlotIndex", simFingerprint).apply()
                } else {
                    // SIM not swapped, same fingerprint
                    Log.i("checkSimFingerprint", "SIM $simFingerprint not swapped.")
                    Toast.makeText(context, "SIM $simFingerprint not swapped.", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: SecurityException) {
            Log.i("checkSimFingerprint", "checkSimFingerprint: ${e.message}")
            Toast.makeText(context, "SecurityException: Permission denied. ${e.message}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Exception: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}

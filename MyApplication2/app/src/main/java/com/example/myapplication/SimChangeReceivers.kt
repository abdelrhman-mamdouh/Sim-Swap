package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
//@RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
//class SimChangeReceiver : BroadcastReceiver() {
//
//    private val TAG = "SimChangeReceiver"
//
//    override fun onReceive(context: Context?, intent: Intent?) {
//        if (intent?.action == "android.intent.action.SIM_STATE_CHANGED") {
//            Log.i(TAG, "SIM State Change Detected")
//
//            context?.let {
//                saveSimDataInSharedPreferences(it)
//                simDataObserver(it)
//            }
//        }
//    }
//
//    private fun saveSimDataInSharedPreferences(context: Context) {
//        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        val subscriptionManager = context.getSystemService(SubscriptionManager::class.java)
//
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.READ_PHONE_STATE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Request permissions if not granted
//            return
//        }
//
//        val imsiList = mutableSetOf<String>()
//
//        subscriptionManager?.activeSubscriptionInfoList?.forEach { subscriptionInfo ->
//            val imsi = telephonyManager.getSubscriberId(subscriptionInfo.subscriptionId)
//            imsiList.add(imsi ?: "")
//        }
//
//        val sharedPreferences = context.getSharedPreferences("simData", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//
//        if (imsiList.isNotEmpty()) {
//            editor.putStringSet("simData", imsiList)
//            Toast.makeText(context, "${imsiList.size} SIM(s) Detected", Toast.LENGTH_SHORT).show()
//        } else {
//            editor.putStringSet("simData", setOf())
//            Toast.makeText(context, "No SIM Card Detected/No Permission", Toast.LENGTH_SHORT).show()
//        }
//
//        editor.apply()
//    }
//
//    private fun simDataObserver(context: Context) {
//        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        val subscriptionManager = context.getSystemService(SubscriptionManager::class.java)
//
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.READ_PHONE_STATE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Request permissions if not granted
//            return
//        }
//
//        val imsiList = mutableSetOf<String>()
//
//        subscriptionManager?.activeSubscriptionInfoList?.forEach { subscriptionInfo ->
//            val imsi = telephonyManager.getSubscriberId(subscriptionInfo.subscriptionId)
//            imsiList.add(imsi ?: "")
//        }
//
//        val sharedPreferences = context.getSharedPreferences("simData", Context.MODE_PRIVATE)
//        val prevImsiList = sharedPreferences.getStringSet("simData", setOf())
//
//        if (prevImsiList == null || prevImsiList.isEmpty() || imsiList.isEmpty()) {
//            Toast.makeText(context, "LOGOUT USER, NO SIM DETECTED", Toast.LENGTH_SHORT).show()
//        } else {
//            for (prevImsi in prevImsiList) {
//                if (prevImsi !in imsiList) {
//                    Toast.makeText(context, "LOGOUT USER, SIM SWAP DETECTED", Toast.LENGTH_SHORT).show()
//                    break
//                }
//            }
//        }
//    }
//}

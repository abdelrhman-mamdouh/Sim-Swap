package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions[Manifest.permission.READ_PHONE_STATE] == true &&
                        permissions[Manifest.permission.READ_SMS] == true &&
                        permissions[Manifest.permission.READ_PHONE_NUMBERS] == true -> {
                    // All permissions granted
                    Toast.makeText(this, "All permissions granted.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // At least one permission denied
                    Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if all the required permissions are already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED) {
            // All permissions already granted
        } else {
            // Request the necessary permissions
            requestPermissionsLauncher.launch(arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS
            ))
        }
    }
}

package com.zvonimirplivelic.parkingzg.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.core.app.ActivityCompat


import android.content.pm.PackageManager
import android.widget.Toast


private const val PERMISSION_REQUEST_SEND_SMS = 1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.zvonimirplivelic.parkingzg.R.layout.activity_main)
        setupActionBarWithNavController(findNavController(com.zvonimirplivelic.parkingzg.R.id.nav_container_view))

        checkForSmsPermission()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(com.zvonimirplivelic.parkingzg.R.id.nav_container_view)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.SEND_SMS),
                PERMISSION_REQUEST_SEND_SMS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_SEND_SMS -> {
                if (permissions[0] != Manifest.permission.SEND_SMS
                    && grantResults[0] !=
                    PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        this,
                        "Please enable SMS permissions to use app",
                        Toast.LENGTH_LONG
                    ).show()
                    checkForSmsPermission()
                }
            }
        }
    }
}
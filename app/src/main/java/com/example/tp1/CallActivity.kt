package com.example.tp1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class CallActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.callview)

        val button = findViewById<ImageButton>(R.id.buttonCall)
        button.setOnClickListener { (intent.extras?.getString("phone")
            ?.let { it1 -> makePhoneCall(it1) }) }
    }

    private fun makePhoneCall(phoneNumber:String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent)
        } else {
            // Demande la permission à l'utilisateur
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
        }
    }
}
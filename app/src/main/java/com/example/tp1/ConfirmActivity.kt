package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmation)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val button = findViewById<Button>(R.id.buttonConfirmSummary)
        button.setOnClickListener(buttonListener)

        val extras = intent.extras;
        findViewById<TextView>(R.id.nom_result).text = extras?.getString("nom") ?: "Valeur non reçu"
        findViewById<TextView>(R.id.prenom_result).text = extras?.getString("prenom") ?: "Valeur non reçu"
        findViewById<TextView>(R.id.age_result).text = extras?.getString("age") ?: "Valeur non reçu"
        findViewById<TextView>(R.id.phone_result).text = extras?.getString("num") ?: "Valeur non reçu"
        findViewById<TextView>(R.id.domaine_result).text = extras?.getString("comp") ?: "Valeur non reçu"
    }

    private val buttonListener= View.OnClickListener { view ->
        when(view.id){
            R.id.buttonConfirmSummary->switchActivity()
        }
    }

    private fun switchActivity(){
        val intent = Intent(this, CallActivity::class.java)
        intent.putExtra("phone",findViewById<TextView>(R.id.phone_result).text.toString())
        startActivity(intent)
    }
}
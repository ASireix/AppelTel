package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.formulaire)

        val button = findViewById<Button>(R.id.buttonValidate)
        button.setOnClickListener(buttonListener)

        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    val buttonListener=View.OnClickListener { view ->
        when(view.id){
            R.id.buttonValidate->testToast()
        }
    }

    private fun testToast(){
        val dialog = AlertDialog.Builder(this)

        dialog.setTitle(R.string.alertName)
            .setMessage(R.string.alertDesc)
            .setPositiveButton("YES") { dialog, whichButton ->

                var fields = emptyArray<EditText>()

                // Récupération automatique des EditText à partir du layout
                val rootView = findViewById<ViewGroup>(R.id.formulaire_layout) // ID du parent contenant les champs
                fields += getAllEditTexts(rootView)

                val intent = Intent(this, ConfirmActivity::class.java)
                var check = true

                for (t in fields) {
                    if (t.text.isNotEmpty()) {
                        val key = resources.getResourceEntryName(t.id) // Convertir R.id.nom -> "nom"
                        intent.putExtra(key, t.text.toString())
                    } else {
                        check = false
                        t.error = t.hint // Affiche l'erreur avec le hint du champ
                    }
                }

                if (check) {
                    val bundle = Bundle()
                    onSaveInstanceState(bundle)
                    startActivity(intent)
                }

            }
            .setNegativeButton("NO") { dialog, whichButton ->

            }

        dialog.show()
    }

    private fun getAllEditTexts(viewGroup: ViewGroup): Array<EditText> {
        val fields = mutableListOf<EditText>()

        for (i in 0 until viewGroup.childCount) {
            val view = viewGroup.getChildAt(i)
            if (view is EditText) {
                fields.add(view)
            } else if (view is ViewGroup) {
                fields.addAll(getAllEditTexts(view)) // Recherche récursive
            }
        }
        return fields.toTypedArray()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val rootView = findViewById<ViewGroup>(R.id.formulaire_layout)
        var fields = emptyArray<EditText>()
        fields += getAllEditTexts(rootView)

        for (t in fields) {
            val key = resources.getResourceEntryName(t.id) // Convertir R.id.nom -> "nom"
            outState.putString(key, t.text.toString()) // Sauvegarde chaque champ
        }
        super.onSaveInstanceState(outState)
    }

    //Pourquoi ça marche pas ?
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        restoreFormData(savedInstanceState)
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun restoreFormData(savedInstanceState: Bundle) {
        val rootView = findViewById<ViewGroup>(R.id.formulaire_layout)
        var fields = emptyArray<EditText>()
        fields += getAllEditTexts(rootView)

        for (t in fields) {
            val key = resources.getResourceEntryName(t.id)
            t.setText(savedInstanceState.getString(key, "Test value")) // Restaurer les valeurs
        }
    }

}
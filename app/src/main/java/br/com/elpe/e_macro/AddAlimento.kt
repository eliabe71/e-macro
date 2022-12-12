package br.com.elpe.e_macro

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddAlimento : AppCompatActivity() {

    var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alimento)
        val button: Button = findViewById(R.id.button)
        val gord = findViewById<EditText>(R.id.gordTota)
        val nameA = findViewById<EditText>(R.id.nameA)
        val prot = findViewById<EditText>(R.id.proteinasAdd)
        val car = findViewById<EditText>(R.id.carboAdd)
        //val refNames = hashMapOf()
        var ff = intent.extras?.get("key").toString()

        button.setOnClickListener {
            val alimentos = hashMapOf(
                "key" to ff,
                "nomeAlimento" to nameA.text?.toString(),
                "gorduras" to (gord.text?.toString()?.toDouble() ?: 0.0),
                "proteinas" to (prot.text?.toString()?.toDouble() ?: 0.0),
                "carboidratos" to (car.text?.toString()?.toDouble() ?: 0.0)
            )

            db.collection("alimentos")
                .add(alimentos)
                .addOnSuccessListener { documentReference ->

                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    onBackPressed()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "ERRRUUU "+e.toString(), Toast.LENGTH_LONG).show()
                    Log.w(ContentValues.TAG, "Error adding document", e)
                    //onBackPressed()
                }
        }
    }
}
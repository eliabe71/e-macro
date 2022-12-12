package br.com.elpe.e_macro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CustomRefeicao: AppCompatActivity() {
    lateinit var button: Button
    lateinit var buttonFinish: Button
    lateinit var nameRef: TextView
    lateinit var nameEdit: EditText
    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_refeicao)
        nameRef = findViewById(R.id.refName)
        nameEdit = findViewById(R.id.editNameRef)
        button = findViewById(R.id.buttonAddAliment)

        val name: String =   if(intent.extras?.get("nomeWindow").toString().isEmpty())
                                intent.extras?.get("key").toString()
                             else intent.extras?.get("nomeWindow").toString()

        nameRef.text = name
        button.setOnClickListener {
            val intent = Intent(this, AddAlimento::class.java)
//            intent.putExtra("pos", intent.extras?.get("key").toString().toInt())
            intent.putExtra("key", intent.extras?.get("key").toString())
            startActivity(intent)

        }

        buttonFinish = findViewById(R.id.buttonFinishOperation)
        buttonFinish.setOnClickListener {

            val data = hashMapOf(
                "nomeWindow" to nameEdit.text.toString(),
                "nome" to intent.extras?.get("key").toString()
            )
            db.collection("refeicao").document(intent.extras?.get("id").toString()).set(data)
            this.onBackPressed()
        }

    }

}
package br.com.elpe.e_macro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CustomRefeicao: AppCompatActivity() {
    lateinit var button: Button
    lateinit var buttonFinish: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_refeicao)
        button = findViewById(R.id.buttonAddAliment)
        button.setOnClickListener {
            val intent = Intent(this, AddAlimento::class.java)
            startActivity(intent)
        }

        buttonFinish = findViewById(R.id.buttonFinishOperation)
        buttonFinish.setOnClickListener {
            this.onBackPressed()
        }

    }

}
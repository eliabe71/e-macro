package br.com.elpe.e_macro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.elpe.e_macro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rollButton.setOnClickListener{ rollDice()};
    //setContentView(R.layout.activity_main)
        //val rollButton: Button = findViewById(R.id.roll_button)
        //rollButton.setOnClickListener { rollDice() }
    }
    private fun rollDice() {
        Toast.makeText(this, "button clicked",
            Toast.LENGTH_SHORT).show()
    }
}
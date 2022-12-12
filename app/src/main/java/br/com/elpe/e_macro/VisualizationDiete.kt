package br.com.elpe.e_macro

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp

class VisualizationDiete : AppCompatActivity() {
    private val array = ArrayList<String>()
    private val array2 = ArrayList<String>()
    private var arrayKey = ArrayList<String>()
    private val db = Firebase.firestore
    init {
        array.add("Eliabe")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualization_diete)

        db.collection("refeicao")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(intent.extras?.get("name").toString()
                        == document.data["nomeDieta"].toString()){
                        array.add(document.data["nome"].toString())
                        arrayKey.add(document.id)
                        array2.add(document.data["nomeWindow"].toString())
                    }
                }
                val myAdapter = RefeicoesAdapter(array,array2,true, AppCompatActivity(), arrayKey)
                val recycler = findViewById<RecyclerView>(R.id.recyclerVisualization)
                recycler.layoutManager = LinearLayoutManager(this)
                recycler.adapter = myAdapter
                recycler.adapter?.notifyDataSetChanged()

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }
}
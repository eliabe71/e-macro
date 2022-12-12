package br.com.elpe.e_macro

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SavedDiets : AppCompatActivity() {
    val array:ArrayList<String> = ArrayList()
    lateinit var button: Button
    private var db = Firebase.firestore
    init{

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_diets)
        val recyclerView:RecyclerView = findViewById(R.id.recyclerDiets)
        val adapter = DietsSavedsAdapter(array)
        val managerLayout = LinearLayoutManager(this)
        recyclerView.layoutManager = managerLayout
        recyclerView.adapter = adapter

        db.collection("refeicao").get().addOnSuccessListener {
                result ->
            for (document in result) {
                array.add(document.data["nomeDieta"].toString())
            }
            val recycler = findViewById<RecyclerView>(R.id.recyclerDiets)
            recycler.adapter?.notifyDataSetChanged()


        }.addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting documents.", exception)
        }
    }
}
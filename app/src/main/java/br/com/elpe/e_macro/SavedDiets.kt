package br.com.elpe.e_macro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

class SavedDiets : AppCompatActivity() {
    val array:ArrayList<String> = ArrayList()

    init{
    array.add("Eliabe")
    array.add("Cristiano")
    array.add("Basil")
    array.add("babel")
    array.add("Baleia")
    //array2.add("Eliabe")
    //array2.add("Cristiano")
    //array2.add("Basil")
    //array2.add("babel")
    //array2.add("Baleia")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_diets)

        val recyclerView:RecyclerView = findViewById(R.id.recyclerDiets)
        val adapter = DietsSavedsAdapter(array)
        val managerLayout = LinearLayoutManager(this)
        recyclerView.layoutManager = managerLayout
        recyclerView.adapter = adapter
    }
}
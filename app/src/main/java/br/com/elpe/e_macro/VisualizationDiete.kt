package br.com.elpe.e_macro

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class VisualizationDiete : AppCompatActivity() {
    private val array = ArrayList<String>()
    private val array2 = ArrayList<String>()
    init {
        array.add("Eliabe")
        array.add("Cristiano")
        array.add("Basil")
        array.add("babel")
        array.add("Baleia")
        array2.add("Eliabe")
        array2.add("Cristiano")
        array2.add("Basil")
        array2.add("babel")
        array2.add("Baleia")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualization_diete)
        val myAdapter = RefeicoesAdapter(array,array2,true)

        val recycler = findViewById<RecyclerView>(R.id.recyclerVisualization)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = myAdapter
    }
}
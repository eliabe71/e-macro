package br.com.elpe.e_macro

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AlimentoAdapter (private val dataSet: ArrayList<String>, private val entrada: ArrayList<Map<String, String>>) : RecyclerView.Adapter<AlimentoAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val textView2: TextView
        val db = Firebase.firestore
        val list = ArrayList<String>()

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.nomeAlimento)
            textView2 = view.findViewById(R.id.caloriaAlimento)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.aliment_line, viewGroup, false)
        return ViewHolder(view)

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = entrada[position]["nome"].toString()
        viewHolder.textView2.text = entrada[position]["kcal"].toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = entrada.size

}
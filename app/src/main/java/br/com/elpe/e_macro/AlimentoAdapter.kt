package br.com.elpe.e_macro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlimentoAdapter (private val dataSet: ArrayList<String>) : RecyclerView.Adapter<AlimentoAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val textView2: TextView

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
        viewHolder.textView.text = dataSet[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
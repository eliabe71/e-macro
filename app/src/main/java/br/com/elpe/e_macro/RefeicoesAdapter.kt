package br.com.elpe.e_macro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RefeicoesAdapter (private val dataSet: ArrayList<String>, private val dataSet2: ArrayList<String> ) : RecyclerView.Adapter<RefeicoesAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recycler: RecyclerView
        val text : TextView
        val button: Button
        val buttonEdit: Button
        //lateinit var intent: Intent
        init {
            button = view.findViewById(R.id.addAlimento)
            buttonEdit = view.findViewById(R.id.edit)
            text = view.findViewById(R.id.refeicao)
            // Define click listener for the ViewHolder's View.
            recycler = view.findViewById(R.id.recyclerDiet)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.diet_line, viewGroup, false)
        return RefeicoesAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = dataSet[position]


        //Custom Refeição
        holder.buttonEdit.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(holder.itemView.context, CustomRefeicao::class.java)
            bundle.putInt("pos", position)
            startActivity(holder.itemView.context,intent, bundle)
        }

        //// Add ALimento
        holder.button.setOnClickListener {
            val intent = Intent(holder.itemView.context, AddAlimento::class.java)
            val bundle = Bundle()
            bundle.putInt("pos", position)
            startActivity(holder.itemView.context,intent, bundle)
        }
        val myAdapter = AlimentoAdapter(dataSet2)
        holder.recycler.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.recycler.adapter = myAdapter
    }



    override fun getItemCount() = dataSet.size

}
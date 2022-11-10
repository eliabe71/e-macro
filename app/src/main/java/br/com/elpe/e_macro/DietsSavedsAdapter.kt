package br.com.elpe.e_macro

import android.app.ActionBar.LayoutParams
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DietsSavedsAdapter(private val dataName: ArrayList<String>) : RecyclerView.Adapter<DietsSavedsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewDate: TextView
        val textViewName : TextView
        init {
            textViewDate = view.findViewById(R.id.dayDiet)
            textViewName = view.findViewById(R.id.nameDiet)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //INflate
        val view = LayoutInflater.from(parent.context).inflate(R.layout.line_dietes,parent,false)
        //val params = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        //view.layoutParams = params
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewName.text = dataName[position]
    }

    override fun getItemCount() = dataName.size
}
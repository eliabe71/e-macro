package br.com.elpe.e_macro

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class DietsSavedsAdapter(private val dataName: ArrayList<String>) : RecyclerView.Adapter<DietsSavedsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewDate: TextView
        val textViewName : TextView
        val buttonEye : ImageButton
        init {
            textViewDate = view.findViewById(R.id.dayDiet)
            textViewName = view.findViewById(R.id.nameDiet)
            buttonEye = view.findViewById(R.id.viewRef)
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
        holder.buttonEye.setOnClickListener {
            holder.itemView.context
            startViewDetailsDiet(holder.itemView.context,position)
        }
    }

    private fun startViewDetailsDiet(context: Context, position:Int) {
        val intent = Intent(context,VisualizationDiete::class.java)
        intent.putExtra("name" , dataName[position])
        startActivity(context,intent, null)
    }

    override fun getItemCount() = dataName.size
}
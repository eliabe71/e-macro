package br.com.elpe.e_macro

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


//@SuppressLint("NotifyDataSetChanged")
class RefeicoesAdapter(private val dataSet: ArrayList<String>,
                       private val dataSet2: ArrayList<String>,
                       val r:Boolean = true, activity: AppCompatActivity,
                       private val custom: ArrayList<String> ) : RecyclerView.Adapter<RefeicoesAdapter.ViewHolder>(){

    private lateinit var activity2: AppCompatActivity
    lateinit var startForResult: ActivityResultLauncher<Intent>

    init {

    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recycler: RecyclerView
        val text : TextView
        val button: Button
        val buttonEdit: Button
        var db = Firebase.firestore
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
        if(r){
            holder.buttonEdit.visibility = View.GONE
            holder.button.visibility = View.GONE
            var mapArray: ArrayList<Map<String, String>> = ArrayList()
            holder.db.collection("alimentos").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        var calGord = 0.0
                        var calProt = 0.0
                        var calCarb = 0.0
                        if(dataSet[position] == document.data["key"]){
                            //array.add(document.data["nomeAlimento"].toString())
                            calCarb += (document.data["carboidratos"]?.toString()?.toDouble() ?: 0.0)*4
                            calProt += (document.data["proteinas"]?.toString()?.toDouble() ?: 0.0)*4
                            calGord += (document.data["gorduras"]?.toString()?.toDouble() ?: 0.0)*9
                            val mapElement = mapOf(
                                "nome" to document.data["nomeAlimento"]?.toString(),
                                "kcal" to (calGord+calCarb+calProt).toString()
                            )
                            mapArray.add(mapElement as Map<String, String>)
                        }

                    }
                    val myAdapter = AlimentoAdapter(dataSet2, mapArray)
                    holder.recycler.layoutManager = LinearLayoutManager(holder.itemView.context)
                    holder.recycler.adapter = myAdapter
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
            return
        }
        holder.text.text = dataSet2[position].ifEmpty {dataSet[position]}
        //Custom Refeição
        holder.buttonEdit.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(holder.itemView.context, CustomRefeicao::class.java)
            intent.putExtra("key", dataSet[position].toString())
            intent.putExtra("id", custom[position].toString())
            intent.putExtra("nomeWindow", dataSet2[position].toString())
            intent.putExtra("pos", position)
            startActivity(holder.itemView.context,intent, bundle)
        }

        //// Add ALimento
        holder.button.setOnClickListener {
            val intent = Intent(holder.itemView.context, AddAlimento::class.java)
            val bundle = Bundle()
            bundle.putInt("pos", position)
            bundle.putString("key", dataSet[position])
            intent.putExtra("key", dataSet[position])
            startActivity(holder.itemView.context,intent, bundle)
        }
        val array: ArrayList<String> = ArrayList()
        val mapArray: ArrayList<Map<String, String>> = ArrayList()

        holder.db.collection("alimentos").get()
            .addOnSuccessListener { result ->


                for (document in result) {
                    var calGord = 0.0
                    var calProt = 0.0
                    var calCarb = 0.0
                    if(dataSet[position] == document.data["key"]){
                        //array.add(document.data["nomeAlimento"].toString())
                        calCarb += (document.data["carboidratos"]?.toString()?.toDouble() ?: 0.0)*4
                        calProt += (document.data["proteinas"]?.toString()?.toDouble() ?: 0.0)*4
                        calGord += (document.data["gorduras"]?.toString()?.toDouble() ?: 0.0)*9
                        val mapElement = mapOf(
                            "nome" to document.data["nomeAlimento"]?.toString(),
                            "kcal" to (calGord+calCarb+calProt).toString()
                        )
                        mapArray.add(mapElement as Map<String, String>)
                    }
                }
                val myAdapter = AlimentoAdapter(array, mapArray)
                holder.recycler.layoutManager = LinearLayoutManager(holder.itemView.context)
                holder.recycler.adapter = myAdapter
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
                val myAdapter = AlimentoAdapter(array,ArrayList<Map<String, String>>())
                holder.recycler.layoutManager = LinearLayoutManager(holder.itemView.context)
                holder.recycler.adapter = myAdapter
            }
    }

    override fun getItemCount() = dataSet.size

}




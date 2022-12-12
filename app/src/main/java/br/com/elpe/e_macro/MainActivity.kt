package br.com.elpe.e_macro

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.DateTime
import java.sql.Timestamp

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*
import kotlin.math.roundToInt


@SuppressLint("NotifyDataSetChanged")
class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding
    private var timer: Timer = Timer()
    private val TIME_INTERVAL: Int = 2000
    private var mBackPressed: Long = 0
    var count =0;
    //val recycler = R.id.recycler]
    val db = Firebase.firestore

    private val array = ArrayList<String>()
    private val array2 = ArrayList<String>()
    private val arrayKey = ArrayList<String>()
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    private val arrayNameWin  = ArrayList<String>()

    private var last = String()
    private var idLast = String()
    private val currentDate = System.currentTimeMillis()

    val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy")
    var f: DateTimeFormatter = DateTimeFormatterBuilder().parseCaseInsensitive()
        .append(DateTimeFormatter.ofPattern("yyyy-MMM-dd")).toFormatter()
    private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)

    init {

        db.collection("last").get().addOnSuccessListener {
            result ->
            for (document in result) {
                last = document["lastGetIn"].toString()
                val pass  =
                    Timestamp(last.toLong()).day ==
                            Timestamp(currentDate).day
                            &&
                            Timestamp(last.toLong()).month ==
                            Timestamp(currentDate).month
                            &&
                            Timestamp(last.toLong()).year ==
                            Timestamp(currentDate).year
                if(!pass) {last = System.currentTimeMillis().toString()

                }

            }
            updateFireStore()
        }.addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }

    private fun updateFireStore(){
        db.collection("refeicao")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val pass  =
                        Timestamp(last.toLong()).day ==
                                Timestamp(document.data["data"].toString().toLong()).day
                                &&
                                Timestamp(last.toLong()).month ==
                                Timestamp(document.data["data"].toString().toLong()).month
                                &&
                                Timestamp(last.toLong()).year ==
                                Timestamp(document.data["data"].toString().toLong()).year

                    if(pass){
                        array.add(document.data["nome"].toString())
                        arrayKey.add(document.id)
                        array2.add(document.data["nomeWindow"].toString())
                    }
                }
                val recycler = findViewById<RecyclerView>(R.id.recycler)
                recycler.adapter?.notifyDataSetChanged()
                updateProgressBar()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun savDiet(name:String){
        db.collection("refeicao")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val dataSave = hashMapOf(
                        "data" to System.currentTimeMillis().toString(),
                        "nome" to document.data["nome"].toString(),
                        "nomeWindow" to document.data["nomeWindow"].toString(),
                        "salva" to true,
                        "nomeDieta" to name
                    )
                    db.collection("refeicao").document(document.id).set(dataSave)
                }
                Toast.makeText(this, "Dieta de Hoje Salva", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
            return
        }

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Clique Duas Vezes Para Sair", Toast.LENGTH_SHORT).show()
        }
        mBackPressed = System.currentTimeMillis()
    }


    private fun updateProgressBar(){
        var progressCarb = findViewById<LinearProgressIndicator>(R.id.progressCarb)
        var progressProt = findViewById<LinearProgressIndicator>(R.id.progressProt)
        var progressGord = findViewById<LinearProgressIndicator>(R.id.progressGord)
        val totalCarb  = 156
        val totalProt = 344
        val gordura = 56
        var totalCarbAux = 0.0
        var totalProtAux = 0.0
        var gorduraAux = 0.0

        db.collection("alimentos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    document.id
                    totalCarbAux += document.data["carboidratos"]?.toString()?.toDouble() ?: 0.0
                    totalProtAux += document.data["proteinas"]?.toString()?.toDouble() ?: 0.0
                   gorduraAux += document.data["gorduras"]?.toString()?.toDouble() ?: 0.0
                }

                timer.schedule(object:TimerTask(){
                    override fun run() {

                        progressCarb.setProgress((totalCarbAux*100/totalCarb).roundToInt(), true)
                        progressProt.setProgress((totalProtAux*100/totalProt).roundToInt(), true)
                        progressGord.setProgress((gorduraAux*100/gordura).roundToInt(), true)

                    }

                }, 0, 1)

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }
    override fun onStart() {
        super.onStart()
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter?.notifyDataSetChanged()
        //updateProgressBar()
    }
    override fun onResume() {
        updateProgressBar()
        super.onResume()


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        drawerLayout = findViewById<DrawerLayout>(R.id.my_drawer_layout);
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView = findViewById(R.id.navi)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.saveDiet -> {
                    startSaveDiet()
                }
                R.id.nextsGyms -> {
                    startMaps()
                }
            }
            true
        }

        val myAdapter = RefeicoesAdapter(array, array2, false, this,arrayKey)
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = myAdapter

        val button = findViewById<Button>(R.id.addRef)

        button.setOnClickListener {
            openBottomSheetAddRef()
            //rollDice()
        }

        val imageButton = findViewById<ImageButton>(R.id.buttonQrCode)
        imageButton.setOnClickListener {
            openBottomShetQRCode()
        }

        val imageButtonCamera = findViewById<ImageButton>(R.id.imageButtonCamera)

        imageButtonCamera.setOnClickListener {
            startCamera()
        }

        val shareButton = findViewById<ImageButton>(R.id.imageButtonShare)

        shareButton.setOnClickListener { shareSocialMedia() }

        val saveDietBtton = findViewById<ImageButton>(R.id.imageButtonExport)

        saveDietBtton.setOnClickListener {
            openBottomSheetNameRef()

        }

        updateProgressBar()

    }



    private fun startMaps() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent, null)
    }

    //    override fun
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun openBottomSheetAddRef() {

        val bottomSheet = BottomSheetDialog(this)

        bottomSheet.setContentView(R.layout.bottom_sheet_add_ref)
        bottomSheet.findViewById<Button>(R.id.salvar)?.setOnClickListener {


            val strName = bottomSheet.findViewById<EditText>(R.id.refNameAdd)?.text
            val refNames = hashMapOf(
                "nome" to strName.toString(),
                "data" to System.currentTimeMillis().toString(),
                "nomeWindow" to strName.toString(),
                "salva" to false,
                "nomeDieta" to ""
            )
            db.collection("refeicao")
                .add(refNames)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    array.clear()
                    array2.clear()
                    arrayKey.clear()
                    updateFireStore()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "ERRRUUU "+e.toString(), Toast.LENGTH_LONG).show()
                    Log.w(TAG, "Error adding document", e)

                }
            bottomSheet.dismiss()
            val recycler = findViewById<RecyclerView>(R.id.recycler)
            recycler.adapter?.notifyDataSetChanged()
        }
        bottomSheet.show()
    }
    private fun openBottomSheetNameRef() {

        val bottomSheet = BottomSheetDialog(this)

        bottomSheet.setContentView(R.layout.bottom_shet_name_diet)
        bottomSheet.findViewById<Button>(R.id.salvar)?.setOnClickListener {
            savDiet(bottomSheet.findViewById<EditText>(R.id.refNameAdd)!!.text.toString())
            bottomSheet.dismiss()
        }
        bottomSheet.show()
    }

    private fun openBottomShetQRCode() {
        val bottomSheet = BottomSheetDialog(this)

        bottomSheet.setContentView(R.layout.bottom_sheet_qrcode)

        val qrCode = QrCode()
        bottomSheet.findViewById<ImageView>(R.id.imageQR)
            ?.setImageBitmap(qrCode.encode("", "Eliabe"))
        bottomSheet.show()
    }

    private fun startSaveDiet() {
        val intent = Intent(this, SavedDiets::class.java)

        startActivity(intent, null)

    }

    private fun startCamera() {
        val intent = Intent(this, Scanner::class.java)
        startActivity(intent)

    }

    private fun shareSocialMedia() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND;
        intent.type = "image/*";
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("mipmap-hdpi/ic_camera.png"));
        intent.putExtra(Intent.EXTRA_TEXT, "Aqui Está Minha Dieta! ")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        startActivity(Intent.createChooser(intent, "share"));
    }

}
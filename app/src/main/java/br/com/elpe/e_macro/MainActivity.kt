package br.com.elpe.e_macro

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar.DisplayOptions
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.elpe.e_macro.R.menu.main_menu
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding

    //val recycler = R.id.recycler

    private val array = ArrayList<String>()
    private val array2 = ArrayList<String>()
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit  var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
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
        setContentView(R.layout.activity_main)


        drawerLayout = findViewById<DrawerLayout>(R.id.my_drawer_layout);
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView = findViewById(R.id.navi)

        navigationView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.saveDiet -> {
                    startSaveDiet()
                }
            }
            true
        }

        val myAdapter = RefeicoesAdapter(array,array2)
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = myAdapter

        val button = findViewById<Button>(R.id.addRef)

        button.setOnClickListener {
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
    }

//    override fun
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }


    private fun openBottomShetQRCode() {
        val bottomSheet = BottomSheetDialog(this)

        bottomSheet.setContentView(R.layout.bottom_sheet_qrcode)

        val qrCode = QrCode()
        bottomSheet.findViewById<ImageView>(R.id.imageQR)
            ?.setImageBitmap(qrCode.encode("", "Eliabe"))
        bottomSheet.show()
    }

    private fun startSaveDiet(){
        val intent = Intent(this,SavedDiets::class.java)
        startActivity(intent, null)

    }
    private fun startCamera() {
        val intent = Intent(this, SavedDiets::class.java)
        startActivity(intent)

    }
    private fun shareSocialMedia(){
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
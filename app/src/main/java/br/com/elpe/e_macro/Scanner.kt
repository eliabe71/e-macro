package br.com.elpe.e_macro

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_DENIED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import java.util.jar.Manifest

class Scanner : AppCompatActivity() {

    lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA ) == PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf( android.Manifest.permission.CAMERA), 123)
        }
        else{
            startCamera()
        }
    }

    private fun startCamera() {
        val camera: CodeScannerView = findViewById(R.id.scanner_view)
        codeScanner = CodeScanner(this, camera)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false
        codeScanner.decodeCallback = DecodeCallback{

        }
        camera.setOnClickListener {
            codeScanner.startPreview()
        }
        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread{
                Toast.makeText(this, "Error Initiliazer Camera",
                Toast.LENGTH_SHORT).show()
            }
        }


    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Camera Denied",
                Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Camera Granted",
                Toast.LENGTH_SHORT).show()
            startCamera()
        }

    }

    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized){
            codeScanner?.startPreview()
        }
    }
    override fun onPause() {
        super.onPause()
        if(::codeScanner.isInitialized){
            codeScanner?.releaseResources()
        }
    }
}
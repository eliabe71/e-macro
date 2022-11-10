package br.com.elpe.e_macro

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlin.properties.Delegates

class UserLocation(context: Context): LocationListener {
    lateinit var context: Context
    private var longitude: Double = 0.0
    private var latitude:Double = 0.0

    //var location = LocationRequest()

    init {
        this.context = context
        //location.crea
    }
    override fun onLocationChanged(p0: Location) {
        longitude = p0.longitude
        latitude = p0.latitude
        Toast.makeText(context, longitude.toString()+" "+latitude.toString(), Toast.LENGTH_LONG)
    }
    fun getLatitude(): Double {
        return latitude
    }
    fun getLongitude(): Double {
        return longitude
    }
}
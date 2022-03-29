package com.example.devandroidtp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.devandroidtp.databinding.MapEditActivityBinding
import com.example.devandroidtp.services.ApiClient
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class MapEditActivity :
    AppCompatActivity(),
    MapboxMap.OnMapClickListener,
    MapboxMap.OnMapLongClickListener,
    MapboxMap.OnMarkerClickListener {

    private lateinit var mapview : MapView
    private lateinit var binding: MapEditActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(applicationContext, getString(R.string.access_token))
        setContentView(R.layout.map_edit_activity)

        binding = MapEditActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapview = binding.map
        mapview.onCreate(savedInstanceState)

        val mapName = intent.getStringExtra("name").toString()
        binding.mapName.text = mapName
        Toast.makeText(this, "page2 " + intent.getStringExtra("name"), Toast.LENGTH_SHORT).show()
        binding.btnBack.setOnClickListener{finish()}

        GlobalScope.launch(Dispatchers.Main) {
            try{
                val response = ApiClient.apiService.getOneMap(mapName)
                println(response)
                if(response.isSuccessful) {

                    // Obtention de la carte de manière asynchrone
                    mapview.getMapAsync { map ->
                        // On choisi un style (et on ne fait rien avec)
                        map.setStyle(Style.OUTDOORS) { style -> }

                        response.body()?.map { point ->
                            map.addMarker(MarkerOptions()
                                .title(point.name?.toString())
                                .snippet(point.type?.toString())
                                .position(LatLng(point.latitude, point.longitude)))
                        }
                        map.addOnMapClickListener(this@MapEditActivity)
                        map.addOnMapLongClickListener(this@MapEditActivity)
                    }
                }
                else{
                    Toast.makeText(
                        this@MapEditActivity,
                        "Error Occured: ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            //Map Vide ?
            catch (e: Exception){
                Toast.makeText(
                    this@MapEditActivity,
                    "Error Occured: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                mapview.getMapAsync { map ->
                    // On choisi un style (et on ne fait rien avec)
                    map.setStyle(Style.OUTDOORS) { style ->
                    }
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        mapview.onStart()
    }
    public override fun onResume() {
        super.onResume()
        mapview.onResume()
    }
    public override fun onPause() {
        super.onPause()
        mapview.onPause()
    }
    public override fun onStop() {
        super.onStop()
        mapview.onStop()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapview.onLowMemory()
    }
    override fun onDestroy() {
        super.onDestroy()
        mapview.onDestroy()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapview.onSaveInstanceState(outState)
    }

    override fun onMapClick(point: LatLng): Boolean {
        binding.latitude.setText(point.latitude.toString())
        binding.longitude.setText(point.longitude.toString())
        return true
    }

    override fun onMapLongClick(point: LatLng): Boolean {
        binding.pointName.setText("")
        binding.pointType.setText("")
        binding.latitude.setText("")
        binding.longitude.setText("")
        return true
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        binding.pointName.setText(marker.title?.toString())
        binding.pointType.setText(marker.snippet?.toString())
        binding.latitude.setText(marker.position.latitude.toString())
        binding.longitude.setText(marker.position.longitude.toString())
        return true
    }
}

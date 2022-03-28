package com.example.devandroidtp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.devandroidtp.databinding.MapEditActivityBinding
import com.example.devandroidtp.services.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MapEditActivity : AppCompatActivity() {

    private lateinit var binding: MapEditActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_edit_activity)

        binding = MapEditActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapName = intent.getStringExtra("name").toString()
        binding.mapName.text = mapName
        Toast.makeText(this, "page2 " + intent.getStringExtra("name"), Toast.LENGTH_SHORT).show()
        binding.btnBack.setOnClickListener{finish()}

        GlobalScope.launch(Dispatchers.Main) {
            try{
                val response = ApiClient.apiService.getOneMap(mapName)

                if(response.isSuccessful) {

                    Toast.makeText(
                        this@MapEditActivity,
                        "Map request OK",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else{
                    Toast.makeText(
                        this@MapEditActivity,
                        "Error Occured: ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            catch (e: Exception){
                Toast.makeText(
                    this@MapEditActivity,
                    "Error Occured: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }
}
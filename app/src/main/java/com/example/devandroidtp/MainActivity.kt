package com.example.devandroidtp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.devandroidtp.databinding.ActivityMainBinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.devandroidtp.services.ApiClient
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch(Dispatchers.Main){
            try {
                val response = ApiClient.apiService.getAllMaps()
                if(response.isSuccessful) {
                    val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, response.body()!!)
                    binding.listMaps.adapter = adapter
                    Toast.makeText(
                        this@MainActivity,
                        "Liste ok",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else{
                    Toast.makeText(
                        this@MainActivity,
                        "Error Occured: ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            catch (e: Exception){
                Toast.makeText(
                    this@MainActivity,
                    "Error Occured: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

            binding.listMaps.setOnItemClickListener{ parent, view, position, id ->
                Toast.makeText(
                    this@MainActivity,
                    binding.listMaps.getItemAtPosition(position).toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
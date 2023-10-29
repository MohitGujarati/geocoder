package com.example.geocoder

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        var listView=findViewById<ListView>(R.id.locationlistview)

        val EdSearch = findViewById<EditText>(R.id.btnsearch)
        EdSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                if (charSequence.isNullOrEmpty()) {
                    // Clear the ListView if the input is empty
                    findViewById<ListView>(R.id.locationlistview).adapter = null
                } else {
                    // Call the geocoder and get the list of locations related to the entered text
                    val locations = getLocationInfo(charSequence.toString())

                    // Create a list of strings to display in the ListView
                    val locationStrings = locations!!.map { address ->
                        // Get the latitude and longitude of the location
                        val latitude = address.latitude
                        val longitude = address.longitude

                        // Log the latitude and longitude
                        Log.d("LocationInfo", "Latitude: $latitude, Longitude: $longitude")

                        // Return a formatted string containing the address and locality
                        "${address.getAddressLine(0)}, ${address.locality}"
                    }

                    // Create an ArrayAdapter to display the list of location strings in the ListView
                    val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, locationStrings)
                    findViewById<ListView>(R.id.locationlistview).adapter = adapter
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })


        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedLocation = listView.getItemAtPosition(position) as String
            Toast.makeText(this, selectedLocation, Toast.LENGTH_SHORT).show()
        }



    }

    private fun getLocationInfo(locationName: String): MutableList<Address>? {
        val geocoder = Geocoder(this)
        return geocoder.getFromLocationName(locationName, 10,)
    }


}


//show data in log
/*



        btnsearch.setOnClickListener {
            val locationName = "ahmedabad" // replace with your location name

            val locations = getLocationInfo(locationName)

            if (locations != null) {
                for (address in locations) {
                    Log.d("LocationInfo", "Address: ${address.getAddressLine(0)}")
                    Log.d("LocationInfo", "Locality: ${address.locality}")
                    Log.d("LocationInfo", "Postal Code: ${address.postalCode}")
                    Log.d("LocationInfo", "Country: ${address.countryName}")
                    Log.d("LocationInfo", "Latitude: ${address.latitude}")
                    Log.d("LocationInfo", "Longitude: ${address.longitude}")
                    Log.d("LocationInfo", "----------------------")
                }
            }else{
                Log.d("LocationInfo", "Error")

            }
        }
    private fun getLocationInfo(locationName: String): MutableList<Address>? {
        val geocoder = Geocoder(this)
        return geocoder.getFromLocationName(locationName, 10)
    }

 */

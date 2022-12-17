package com.saifer.storyapp.ui.map

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.saifer.storyapp.R
import com.saifer.storyapp.databinding.ActivityMapsBinding
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.ui.detail.DetailStoryActivity
import com.saifer.storyapp.ui.post.NewStoryActivity

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat: String? = null
    private var lon: String? = null

    private val viewModel: MapViewModel by viewModels {
        MapViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(this)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel.getStoryForMaps(sessionManager.getToken()!!)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.fbNewStory.setOnClickListener {
            val i = Intent(this@MapsActivity, NewStoryActivity::class.java)
            i.putExtra("lat", lat)
            i.putExtra("lon", lon)
            startActivity(i)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        viewModel.getStoryForMaps(sessionManager.getToken()!!).observe(this) { story ->
            for (i in story!!.indices){
                mMap.addMarker(MarkerOptions().position(LatLng(story[i].lat!!, story[i].lon!!)).title(story[i].name).snippet(story[i].description))?.tag = story[i].id
                mMap.setOnInfoWindowClickListener { marker ->
                    val intent = Intent(this, DetailStoryActivity::class.java)
                    intent.putExtra(DetailStoryActivity.ID, marker.tag.toString())
                    startActivity(intent)
                }
            }
        }
    }
}
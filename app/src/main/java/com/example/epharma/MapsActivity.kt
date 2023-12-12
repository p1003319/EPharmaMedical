package com.example.epharma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.epharma.databinding.ActivityMapsBinding
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val medicalStoreNames = listOf(
            "Healthy Living Pharmacy",
            "Wellness Corner Drugstore",
            "Global Health Solutions",
            "Nature's Remedy Pharmacy",
            "MediCare Express",
            "Pristine Wellness Pharmacy",
            "Healing Haven Apothecary",
            "EcoMed Pharmacy",
            "Vitality Drugs",
            "Holistic Health Mart",
            "Sunrise Health Services",
            "BlueSky Pharmaceuticals",
            "Evergreen Medical Supplies",
            "PeakWell Pharmacy",
            "Pioneer Health Solutions",
            "PrimeLife Drugs",
            "Harmony Medical Center",
            "Sunflower Wellness",
            "Serenity Health Hub",
            "GreenLeaf Apothecary",
            "OptiCare Pharmacy",
            "Miracle Health Corner",
            "Unity Wellness Pharmacy",
            "Tranquil Remedies",
            "Nature's Oasis Pharmacy",
            "CityCare Medical Supplies",
            "Emerald Health Solutions",
            "WellNest Pharmacy",
            "BrightHealth Apothecary",
            "PureLife Drugs",
            "Lifeline Wellness Center",
            "Metro Medico",
            "GoldenAge Pharmacy",
            "Zenith Medical Supplies",
            "CrystalCare Pharmacy",
            "HeartyHealth Corner",
            "GreenEarth Pharmaceuticals",
            "SunnySide Wellness Pharmacy",
            "WellBeing Apothecary",
            "Sunset Medical Supplies",
            "WellWish Drugs",
            "VivaCare Pharmacy",
            "BlueRibbon Health Solutions",
            "Soothing Hands Medical Center",
            "Eternal Wellness Pharmacy",
            "Lifespring Drugs",
            "SafeHarbor Apothecary",
            "WellCraft Medical Supplies",
            "ZenStar Pharmacy",
            "Aurora Health Solutions",
            "Dynamic Wellness Corner",
            "CrystalClear Pharmaceuticals",
            "RainbowLife Pharmacy",
            "PeacefulHealth Apothecary",
            "Everest Medical Supplies",
            "RadiantWell Pharmacy",
            "VibrantHealth Drugs",
            "GreenValley Wellness Center",
            "MiracleMinds Apothecary",
            "SpringBreeze Pharmacy",
            "BlueLagoon Medical Supplies",
            "WellRoots Drugs",
            "OceanView Wellness Pharmacy",
            "PurityHealth Corner",
            "Sunburst Pharmaceuticals",
            "MediMagic Pharmacy",
            "Aegis Health Solutions",
            "HealingTouch Apothecary",
            "SereneMedical Supplies",
            "GreenMeadow Pharmacy",
            "BlossomWell Drugs",
            "SolarFlare Wellness Center",
            "WellSculpt Apothecary",
            "HarborLight Pharmacy",
            "Celestial Health Solutions",
            "Revitalize Medical Supplies",
            "PeakPerformance Pharmacy",
            "VividWell Drugs",
            "DreamCatcher Wellness Corner",
            "PristinePeak Pharmaceuticals",
            "Flourish Pharmacy",
            "LivelyHealth Apothecary",
            "MajesticMedical Supplies",
            "AquaBloom Pharmacy",
            "CloudNine Drugs",
            "NatureNook Wellness Center",
            "EpicHealth Solutions",
            "EternalGlow Apothecary",
            "RadiantHarbor Pharmaceuticals",
            "MountainMist Pharmacy",
            "SkylineWell Drugs",
            "BountifulWellness Corner",
            "SoothingBreeze Apothecary"
            // Add more names as needed
        )
        val random = Random()
        for (storeName in medicalStoreNames) {
            var latitude = 0.0
            var longitude = 0.0
            latitude = -90.0 + random.nextDouble() * 180.0  // Range from -90 to 90 (latitude)
            longitude = -180.0 + random.nextDouble() * 360.0  // Range from -180 to 180 (longitude)

            val position = LatLng(latitude, longitude)
            mMap.addMarker(MarkerOptions().position(position).title(storeName))
        }
        // Add a marker in Sydney and move the camer
    }


}
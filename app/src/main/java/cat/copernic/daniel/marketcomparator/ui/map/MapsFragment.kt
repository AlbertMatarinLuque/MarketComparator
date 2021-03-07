package cat.copernic.daniel.marketcomparator.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentMapsBinding
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    lateinit var currentLocation:Location
    lateinit var fusedLocationProviderClient:FusedLocationProviderClient
    private val REQUEST_CODE = 101


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentMapsBinding>(
            inflater,
            R.layout.fragment_maps, container, false
        )
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fetchLocation()

        return binding.root
    }
    private fun fetchLocation() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )

        }

        val task = fusedLocationProviderClient.lastLocation

        task.addOnSuccessListener { location ->

            if (location != null) {
                Log.e("sdsdsd","sdsdsd")
                currentLocation = location
                Toast.makeText(
                    requireContext(),
                    currentLocation.latitude.toString() + "" + currentLocation.longitude,
                    Toast.LENGTH_SHORT
                ).show()
                val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

                mapFragment.getMapAsync(this)

            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("La teva posició")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(markerOptions)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }

}
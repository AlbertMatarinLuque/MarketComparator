package cat.copernic.daniel.marketcomparator.ui.configuration.configuration

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentAboutBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class aboutFragment : Fragment(), OnMapReadyCallback, OnMarkerClickListener {
    private lateinit var binding:FragmentAboutBinding
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var mMap: GoogleMap
    private lateinit var myMarker: Marker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentAboutBinding>(
            inflater,
            R.layout.fragment_about, container, false
        )
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

        mapFragment.getMapAsync(this)


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.search).setVisible(false)
        menu.findItem(R.id.trolley).setVisible(false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val Institut = LatLng(41.56981636285994, 1.9966395571577435)
       /* mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Institut, 18F))
        myMarker = mMap.addMarker(
            MarkerOptions().position(Institut).title("Institut Nicolau Copèrnic")
        )*/
        mMap.setOnMarkerClickListener(this)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Institut, 18F))
        myMarker = mMap.addMarker(
            MarkerOptions().position(Institut).title("Institut Nicolau Copèrnic"))



        enableMyLocation()

    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            mMap.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker!!.equals(myMarker))
        {
            Toast.makeText(requireContext(),"Prueba",Toast.LENGTH_SHORT).show()
            return true
        }
        return true
    }


}
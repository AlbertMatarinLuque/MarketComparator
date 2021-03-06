package cat.copernic.daniel.marketcomparator.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentCategoryBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import cat.copernic.daniel.marketcomparator.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL


class MapFragment : Fragment() {

    lateinit var spType : Spinner
    lateinit var btFind: Button
    lateinit var supportMapFragment: SupportMapFragment
    lateinit var map: GoogleMap
    lateinit var binding: FragmentMapBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var currentLat: Double = 0.0
    var currentLong: Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentMapBinding>(
            inflater,
            R.layout.fragment_map,
            container,
            false
        )

        spType = binding.spType
        btFind = binding.btFind
        supportMapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

        var placeTypeList: MutableList<String> = mutableListOf("market","supermarket")

        var placeNameList: MutableList<String> = mutableListOf("Mercadona","Carrefour")

        spType.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            placeNameList
        )

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if(ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), 44)
        }

        btFind.setOnClickListener{
            var i: Int = spType.selectedItemPosition
            var url: String =  "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$currentLat,$currentLong&radius=5000&types=" +
             placeTypeList[i] + "&sensor=true&key=" + resources.getString(R.string.google_maps_key)

            PlaceTask()

        }
        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(){
        var task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if( location != null){
                currentLat = location.latitude
                currentLong = location.longitude
                supportMapFragment.getMapAsync(OnMapReadyCallback { googleMap ->
                    map = googleMap
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        LatLng(currentLat,currentLong), 10F
                    ))
                })
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 44){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation()
            }
        }
    }
    inner private class PlaceTask: AsyncTask<String, Integer, String>() {
        override fun doInBackground(vararg params: String?): String {
            var data: String? = null
            try {
                data = dowloadUrl(params[0]!!)
            } catch (e: IOException) {
                e.stackTrace
            }

            return data!!
        }

        override fun onPostExecute(result: String?) {
            ParserTask().execute(result)

        }

        @Throws(IOException::class)
        private fun dowloadUrl(string: String): String {
            var url: URL = URL(string)
            var connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connect()
            var stream: InputStream = connection.inputStream
            var reader: BufferedReader = BufferedReader(InputStreamReader(stream))
            var builder: StringBuilder = StringBuilder()
            var line: String = ""
            line = reader.readLine()
            while (line != null) {
                builder.append(line)
            }

            var data: String = builder.toString()
            reader.close()
            return data
        }
    }

    inner class ParserTask : AsyncTask<String, Integer, List<HashMap<String, String>>>() {
        override fun doInBackground(vararg params: String?): List<HashMap<String, String>> {
            var jsonParser: JsonParser = JsonParser()
            var mapList: List<HashMap<String, String>> = emptyList()
            var obj: JSONObject
            try {
                obj = JSONObject(params[0])
                mapList = jsonParser.parseResult(obj)
            } catch (e: JSONException) {
                e.stackTrace
            }
            return mapList
        }

        override fun onPostExecute(result: List<HashMap<String, String>>?) {
            map.clear()

            for (i in result.toString()){
                var i: Int = 0
                var hashMapList: HashMap<String,String>  = result!!.get(i)
                // puede fallar
                var lat: Double = java.lang.Double.parseDouble(hashMapList.get("lat"))
                var lng: Double = java.lang.Double.parseDouble(hashMapList.get("lng"))
                var name: String = hashMapList.get("name")!!
                var latLng: LatLng = LatLng(lat,lng)
                var options: MarkerOptions = MarkerOptions()
                options.position(latLng)
                options.title(name)
                map.addMarker(options)
            }
        }

    }
}






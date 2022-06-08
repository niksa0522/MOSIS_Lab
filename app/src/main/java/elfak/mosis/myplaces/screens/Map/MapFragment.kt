package elfak.mosis.myplaces.screens.Map

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplaces.R
import elfak.mosis.myplaces.databinding.FragmentMapBinding
import elfak.mosis.myplaces.model.LocationViewModel
import elfak.mosis.myplaces.model.MyPlacesViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapFragment : Fragment() {

    private lateinit var map:MapView
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val mapViewModel: LocationViewModel by activityViewModels()
    private val placesViewModel: MyPlacesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater,container,false)
        return binding!!.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //????? sto ne moze samo context
        var ctx: Context? = activity?.applicationContext
        var context = context
        Configuration.getInstance().load(context,PreferenceManager.getDefaultSharedPreferences(context))
        map = binding.map
        map.setMultiTouchControls(true)
        if(ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissionLauncher.launch(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        }else{
            setupMap()
        }
    }

    private fun setupMap(){
        var startPoint = GeoPoint(43.3209,21.8958)
        map.controller.setZoom(15.0)
        if(mapViewModel.setLocation){
            setOnMapClickOverlay()
        }else{
            if(placesViewModel.selected!=null){
                //kada ga iskoristi stavi da je null da ne bude posle problema
                startPoint = GeoPoint(placesViewModel.selected!!.latitude.toDouble(),placesViewModel.selected!!.longitude.toDouble())
                val marker = Marker(map)
                marker.position=startPoint
                map.overlays.add(marker)
            }else{
                setMyLocationOverlay()
            }
        }
        map.controller.animateTo(startPoint)
    }

    private fun setMyLocationOverlay(){
        var myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(activity),map)
        myLocationOverlay.enableMyLocation()
        map.overlays.add(myLocationOverlay)
    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
        isGranted: Boolean->
        if(isGranted){
            setupMap()
        }
    }

    private fun setOnMapClickOverlay(){
        var receive = object:MapEventsReceiver{
            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                var lon = p.longitude.toString()
                var lat = p.latitude.toString()
                mapViewModel.setLocation(lon,lat)
                findNavController().popBackStack()
                return true
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                return false
            }

        }
        var overlayEvents=MapEventsOverlay(receive)
        map.overlays.add(overlayEvents)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_new_place->{
                findNavController().navigate(R.id.action_mapFragment_to_editFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        var item = menu.findItem(R.id.action_my_places_list)
        item.isVisible=false
        item = menu.findItem(R.id.action_show_map)
        item.isVisible=false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //drugo resenje stavi ovde da selected bude null
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}
package elfak.mosis.myplaces

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import elfak.mosis.myplaces.databinding.ActivityMainBinding
import elfak.mosis.myplaces.model.MyPlacesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel:MyPlacesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        navController.addOnDestinationChangedListener{controller,destination,arguments->
            if(destination.id == R.id.editFragment || destination.id == R.id.viewFragment){
                binding.fab.hide()
            }
            else{
                binding.fab.show()
            }
        }
        binding.fab.setOnClickListener {
                //OVO SAM NASAO I POPRAVICU GA
                view ->
            if(navController.currentDestination?.id==R.id.HomeFragment){
                //sta ako sam na mapu izaberem, on misli da radim sa postojecim
                viewModel.selected=null
                navController.navigate(R.id.action_HomeFragment_to_editFragment)
            }else if(navController.currentDestination?.id==R.id.ListFragment){
                //sta ako sam na mapu izaberem jedan, vratim se na listu i kliknem add, on misli da radim sa postojecim
                viewModel.selected=null
                navController.navigate(R.id.action_ListFragment_to_editFragment)
            }else if(navController.currentDestination?.id==R.id.mapFragment){
                //sta ako sam na mapi i kliknem add, on misli da radim sa postojecim
                viewModel.selected=null
                navController.navigate(R.id.action_mapFragment_to_editFragment)
            }
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when(item.itemId){
            R.id.action_show_map -> {
                if(navController.currentDestination?.id==R.id.HomeFragment){
                    navController.navigate(R.id.action_HomeFragment_to_mapFragment)
                }else if(navController.currentDestination?.id==R.id.ListFragment){
                    navController.navigate(R.id.action_ListFragment_to_mapFragment)
                }
            }
            R.id.action_about -> {
                val i: Intent = Intent(this,About::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
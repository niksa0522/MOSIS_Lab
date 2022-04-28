package elfak.mosis.myplaces

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import elfak.mosis.myplaces.databinding.ActivityMyPlacesListBinding

class MyPlacesList : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMyPlacesListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPlacesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var places: MutableList<String> = mutableListOf()
        places.add("Tvrdjava")
        places.add("Cair")
        places.add("Park Svetog Save")
        places.add("Trg Kralja Milana")

        recyclerView = findViewById(R.id.my_places_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter= Adapter(places)
        recyclerView.adapter=adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )


        binding.fab.setOnClickListener { view ->
            places.add("Novi place")
            adapter.notifyItemInserted(places.size-1)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_my_places_list,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_show_map -> Toast.makeText(this,"Show Map!", Toast.LENGTH_SHORT).show()
            R.id.action_new_place -> Toast.makeText(this,"New Place!", Toast.LENGTH_SHORT).show()
            R.id.action_about -> {
                val i: Intent = Intent(this,About::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
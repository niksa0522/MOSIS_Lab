package elfak.mosis.myplaces

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
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

    private val NEW_PLACE:Int = 1

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
        supportActionBar?.setDisplayShowHomeEnabled(true)

        var places: MutableList<String> = mutableListOf()
        places.add("Tvrdjava")
        places.add("Cair")
        places.add("Park Svetog Save")
        places.add("Trg Kralja Milana")

        recyclerView = findViewById(R.id.my_places_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        registerForContextMenu(recyclerView)


        binding.fab.setOnClickListener { view ->
            val i:Intent = Intent(this,EditMyPlaceActivity::class.java)
            startActivityForResult(i,NEW_PLACE)
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
            R.id.action_new_place -> {
                val i: Intent = Intent(this,EditMyPlaceActivity::class.java)
                startActivityForResult(i, NEW_PLACE)
            }
            R.id.action_about -> {
                val i: Intent = Intent(this,About::class.java)
                startActivity(i)
            }
            android.R.id.home-> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            recyclerView.adapter?.notifyDataSetChanged()
            Toast.makeText(this,"New Place Added",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var position:Int = -1
        try{
            position=adapter.getPosition()
        }catch (e:Exception){
            return super.onContextItemSelected(item)
        }
        val bundle:Bundle = Bundle()
        bundle.putInt("position",position)
        var i:Intent
        if(item.itemId==1){
            i=Intent(this,ViewMyPlaceActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }
        else if(item.itemId==2){
            i=Intent(this,EditMyPlaceActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }
        return super.onContextItemSelected(item)
    }

    override fun onStart() {
        adapter=Adapter()
        recyclerView.adapter=adapter
        super.onStart()
    }
}
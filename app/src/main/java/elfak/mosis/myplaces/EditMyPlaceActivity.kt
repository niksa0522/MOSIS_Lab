package elfak.mosis.myplaces

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import elfak.mosis.myplaces.databinding.ActivityEditMyPlaceBinding
import elfak.mosis.myplaces.databinding.ActivityMyPlacesListBinding

class EditMyPlaceActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityEditMyPlaceBinding
    private var editMode:Boolean = true
    private var position:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditMyPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i: Intent = this.intent
        val bundle:Bundle? = i.extras
        position = bundle?.getInt("position") ?: -1
        if(position == -1){
            editMode=false
        }

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val finishedButton: Button = findViewById(R.id.editmyplace_finished_button)
        finishedButton.setOnClickListener(this)
        //finishedButton.isEnabled=false
        val cancelButton:Button = findViewById(R.id.editmyplace_cancel_button)
        cancelButton.setOnClickListener(this)
        val nameEditText:EditText = findViewById(R.id.editmyplace_name_edit)

        if(!editMode){
            finishedButton.isEnabled=false
            finishedButton.setText("Add")
        }
        else{
            finishedButton.setText("Save")
            val place:MyPlace = MyPlacesData.getPlace(position)
            nameEditText.setText(place.name)
            val descEditText:EditText = findViewById(R.id.editmyplace_desc_edit)
            descEditText.setText(place.description)

        }


        nameEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                finishedButton.isEnabled=(p0?.length!! >0)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.editmyplace_finished_button -> {
                val etName: EditText = findViewById(R.id.editmyplace_name_edit)
                val name = etName.text.toString()
                val etDesc: EditText = findViewById(R.id.editmyplace_desc_edit)
                val desc = etDesc.text.toString()
                if(!editMode){
                    val place: MyPlace = MyPlace(name,desc)
                    MyPlacesData.addNewPlace(place)
                }
                else{
                    val place:MyPlace = MyPlacesData.getPlace(position)
                    place.name = name
                    place.description=desc
                }
                setResult(Activity.RESULT_OK)
                finish()
            }
            R.id.editmyplace_cancel_button->{
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_edit_my_place, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when(item.itemId){
            R.id.action_show_map -> Toast.makeText(this,"Show Map!", Toast.LENGTH_SHORT).show()
            R.id.action_my_places_list -> {
                val i: Intent = Intent(this,MyPlacesList::class.java)
                startActivity(i)
            }
            R.id.action_about -> {
                val i: Intent = Intent(this,About::class.java)
                startActivity(i)
            }
            android.R.id.home-> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
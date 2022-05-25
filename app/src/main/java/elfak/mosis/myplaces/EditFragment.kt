package elfak.mosis.myplaces

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import elfak.mosis.myplaces.data.MyPlace
import elfak.mosis.myplaces.databinding.FragmentEditBinding
import elfak.mosis.myplaces.databinding.FragmentListBinding
import elfak.mosis.myplaces.model.MyPlacesViewModel


class EditFragment : Fragment() {


    private var _binding: FragmentEditBinding? = null
    private val viewModel: MyPlacesViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main,menu)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(viewModel.selected!=null){
            binding.editmyplaceNameEdit.setText(viewModel.selected!!.name)
            binding.editmyplaceDescEdit.setText(viewModel.selected!!.description)

        }
        else{
            (activity as AppCompatActivity).supportActionBar?.title = "Add Place"
        }


        val addButton: Button = binding.editmyplaceFinishedButton
        addButton.isEnabled=false

        if(viewModel.selected!=null) {
            addButton.setText(R.string.editmyplace_save_label)
        }



        binding.editmyplaceNameEdit.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                addButton.isEnabled=(p0!!.isNotEmpty())
            }

        })
        addButton.setOnClickListener {
            val editName: EditText = binding.editmyplaceNameEdit
            val name = editName.text.toString()
            val editDesc = binding.editmyplaceDescEdit
            val desc = editDesc.text.toString()
            if(viewModel.selected!=null){
                viewModel.selected!!.name=name
                viewModel.selected!!.description=desc
            }
            else{
                viewModel.addPlace(MyPlace(name,desc))
            }

            findNavController().navigate(R.id.action_editFragment_to_ListFragment)
        }
        val cancelButton:Button = binding.editmyplaceCancelButton
        cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_editFragment_to_ListFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.selected=null
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_my_places_list ->{
                findNavController().navigate(R.id.action_editFragment_to_ListFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_new_place)
        item.isVisible=false
    }
}




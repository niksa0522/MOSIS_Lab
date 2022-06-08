package elfak.mosis.myplaces.screens.Edit

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
import elfak.mosis.myplaces.R
import elfak.mosis.myplaces.data.MyPlace
import elfak.mosis.myplaces.databinding.FragmentEditBinding
import elfak.mosis.myplaces.model.LocationViewModel
import elfak.mosis.myplaces.model.MyPlacesViewModel


class EditFragment : Fragment() {


    private var _binding: FragmentEditBinding? = null
    private val viewModel: MyPlacesViewModel by activityViewModels()
    private val mapViewModel: LocationViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
            binding.editmyplaceLatitudeEdit.setText(viewModel.selected!!.latitude)
            binding.editmyplaceLongitudeEdit.setText(viewModel.selected!!.longitude)
        }
        else{
            (activity as AppCompatActivity).supportActionBar?.title = "Add Place"
        }

        mapViewModel.longitude.observe(viewLifecycleOwner){
            binding.editmyplaceLongitudeEdit.setText(it)
        }
        mapViewModel.latitude.observe(viewLifecycleOwner){
            binding.editmyplaceLatitudeEdit.setText(it)
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
        binding.editmyplaceLocationButton.setOnClickListener {
            mapViewModel.setLocation=true
            findNavController().navigate(R.id.action_editFragment_to_mapFragment)
        }
        addButton.setOnClickListener {
            val editName: EditText = binding.editmyplaceNameEdit
            val name = editName.text.toString()
            val editDesc = binding.editmyplaceDescEdit
            val desc = editDesc.text.toString()
            val longitude= binding.editmyplaceLongitudeEdit.text.toString()
            val latitude = binding.editmyplaceLatitudeEdit.text.toString()
            if(viewModel.selected!=null){
                viewModel.selected!!.name=name
                viewModel.selected!!.description=desc
                viewModel.selected!!.latitude=latitude
                viewModel.selected!!.longitude=longitude
            }
            else{
                viewModel.addPlace(MyPlace(name,desc,longitude,latitude))
            }
            viewModel.selected=null
            mapViewModel.setLocation("","")
            findNavController().popBackStack()
        }
        val cancelButton:Button = binding.editmyplaceCancelButton
        cancelButton.setOnClickListener {
            viewModel.selected=null
            mapViewModel.setLocation("","")
            findNavController().popBackStack()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}




package elfak.mosis.myplaces

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import elfak.mosis.myplaces.data.MyPlace
import elfak.mosis.myplaces.databinding.FragmentListBinding
import elfak.mosis.myplaces.model.MyPlacesViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ListFragment : Fragment(),Adapter.OnClickNavigate {

    private var _binding: FragmentListBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:Adapter
    private val viewModel:MyPlacesViewModel by activityViewModels()

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

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onClickNavigate(place: MyPlace) {
        viewModel.selected=place
        findNavController().navigate(R.id.action_ListFragment_to_viewFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = binding.myPlacesList!!
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter= Adapter(viewModel.myPlacesList,this)
        recyclerView.adapter=adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )


    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position:Int = -1
        try{
            position=adapter.getPosition()
        }catch (e:Exception){
            return super.onContextItemSelected(item)
        }
        if(item.itemId==1){
            viewModel.selected = viewModel.myPlacesList[position]
            findNavController().navigate(R.id.action_ListFragment_to_viewFragment)

        }
        else if(item.itemId==2){
            viewModel.selected = viewModel.myPlacesList[position]
            findNavController().navigate(R.id.action_ListFragment_to_editFragment)
        }
        else if(item.itemId==3){
            Toast.makeText(context,"Delete item",Toast.LENGTH_SHORT).show()
        }
        return super.onContextItemSelected(item)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_new_place ->{
                findNavController().navigate(R.id.action_ListFragment_to_editFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_my_places_list)
        item.isVisible=false
    }
}
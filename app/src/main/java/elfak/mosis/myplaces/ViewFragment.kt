package elfak.mosis.myplaces

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplaces.databinding.FragmentEditBinding
import elfak.mosis.myplaces.databinding.FragmentListBinding
import elfak.mosis.myplaces.databinding.FragmentViewBinding
import elfak.mosis.myplaces.model.MyPlacesViewModel

class ViewFragment : Fragment() {

    private var _binding: FragmentViewBinding? = null
    private val viewModel: MyPlacesViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmyplaceNameText.text=viewModel.selected?.name
        binding.viewmyplaceDescText.text=viewModel.selected?.description


        binding.viewmyplaceFinishedButton.setOnClickListener {
            viewModel.selected = null
            findNavController().navigate(R.id.action_viewFragment_to_ListFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.selected = null
        _binding = null
    }

}
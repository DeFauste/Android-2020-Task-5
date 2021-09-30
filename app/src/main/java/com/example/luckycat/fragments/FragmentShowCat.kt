package com.example.luckycat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.navArgument
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luckycat.R
import com.example.luckycat.adapter.RecyclerViewAdapter
import com.example.luckycat.databinding.FragmentShowCatBinding
import com.example.luckycat.ui.CatsListViewModel
import com.example.luckycat.ui.ItemClickListener
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentShowCat.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentShowCat : Fragment() {

    private var _binding: FragmentShowCatBinding? = null
    private val binding get() = _binding!!
    private lateinit var navigationFragment: NavController
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private val viewModel: CatsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowCatBinding.inflate(inflater, container, false)
        val view = binding.root
        initRecyclerView()
        initViewModel()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationFragment = Navigation.findNavController(view)
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = binding.recycler
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = RecyclerViewAdapter(object : ItemClickListener {
                override fun onItemClick(url: String?) {
                    val bundle = bundleOf(
                        "URL_IMAGE" to url
                    )
                    findNavController().navigate(
                        R.id.action_fragmentShowCat_to_showSingleImage,
                        bundle
                    )
                }
            })
            adapter = recyclerViewAdapter

        }
    }

    private fun initViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.catsLiveData.observe(viewLifecycleOwner, { cats ->
                cats?.let {

                    lifecycleScope.launch {
                        recyclerViewAdapter.submitData(it)

                    }
                }

            })
        }
    }
}
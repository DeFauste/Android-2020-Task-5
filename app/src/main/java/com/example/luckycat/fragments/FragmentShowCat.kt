package com.example.luckycat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luckycat.R
import com.example.luckycat.adapter.NewsLoaderStateAdapter
import com.example.luckycat.adapter.RecyclerViewAdapter
import com.example.luckycat.databinding.FragmentShowCatBinding
import com.example.luckycat.ui.CatsListViewModel
import com.example.luckycat.ui.ItemClickListener
import kotlinx.coroutines.launch

class FragmentShowCat : Fragment() {

    private var _binding: FragmentShowCatBinding? = null
    private val binding get() = _binding!!
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var navigationFragment: NavController
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private val viewModel: CatsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowCatBinding.inflate(inflater, container, false)
        val view = binding.root
        gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        initRecyclerView()
        initViewModel()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationFragment = Navigation.findNavController(view)

        with(binding) {
            recycler.adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoaderStateAdapter(),
                footer = NewsLoaderStateAdapter()
            )
        }

        recyclerViewAdapter.addLoadStateListener { state ->
            with(binding) {
                recycler.isVisible = state.refresh != LoadState.Loading
                progress.isVisible = state.refresh == LoadState.Loading
                errorConnect.isVisible = state.refresh == LoadState.Loading
            }
        }
        recyclerViewAdapter.addLoadStateListener { state ->
            if (state.refresh == LoadState.Loading) {
                recyclerViewAdapter.refresh()
            } else if (state.refresh != LoadState.Loading) {
                recyclerViewAdapter.retry()
            }
        }
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = binding.recycler
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
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
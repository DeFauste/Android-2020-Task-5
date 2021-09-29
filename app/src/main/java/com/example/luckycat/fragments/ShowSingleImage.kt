package com.example.luckycat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.luckycat.R
import com.example.luckycat.databinding.FragmentShowCatBinding
import com.example.luckycat.databinding.FragmentShowSingleImageBinding


private const val URL_IMAGE = "URL_IMAGE"

class ShowSingleImage : Fragment() {

    private var url: String? = null
    private var _binding: FragmentShowSingleImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_IMAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowSingleImageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(binding.image)
            .load(url)
            .into(binding.image)
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            ShowSingleImage().apply {
                arguments = Bundle().apply {
                    putString(URL_IMAGE, url)
                }
            }
    }
}
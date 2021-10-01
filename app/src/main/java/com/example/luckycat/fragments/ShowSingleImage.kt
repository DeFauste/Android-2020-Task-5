package com.example.luckycat.fragments

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.luckycat.databinding.FragmentShowSingleImageBinding

private const val URL_IMAGE = "URL_IMAGE"
private const val FOLDER_CAT = "/Luckycat/"

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
        inflater: LayoutInflater,
        container: ViewGroup?,
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

        binding.saveImageBt.setOnClickListener {
            if (!isCallPermissionGranted()) requestCallPermissions()
            if (isCallPermissionGranted())
                saveImage()
        }
    }

    private fun saveImage() {
        val fileName = url!!.substring(url!!.lastIndexOf('/') + 1, url!!.length)
        val request = DownloadManager.Request(Uri.parse(url))
        request.setTitle(url)
        request.setDescription("Downloading...")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // ktlint-disable max-line-length
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            FOLDER_CAT + fileName
        )
        val manager = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

    private fun isCallPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCallPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            100
        )
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
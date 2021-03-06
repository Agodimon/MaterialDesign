package com.bignerdranch.android.materialdesign.ui.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.createBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.materialdesign.R
import com.bignerdranch.android.materialdesign.databinding.FragmentEarthBinding
import com.bignerdranch.android.materialdesign.model.rest.PictureOfTheDayDataEarth
import com.bignerdranch.android.materialdesign.util.toast
import com.bignerdranch.android.materialdesign.viewmodel.EarthViewModel
import com.bumptech.glide.Glide

class EarthFragment : Fragment() {
    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EarthViewModel by lazy {
        ViewModelProvider(this).get(EarthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayDataEarth) {
        when (data) {
            is PictureOfTheDayDataEarth.Success -> {

                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast("Url is empty")
                } else {
                    context?.let {
                        Glide.with(it)
                            .load(url)
                            .placeholder(R.drawable.ic_no_photo_vector)
                            .error(R.drawable.ic_load_error_vector)
                            .into(binding.imageViewEarth)
                    }
                }
            }
            is PictureOfTheDayDataEarth.Loading -> {
            }
            is PictureOfTheDayDataEarth.Error -> {
                toast(data.error.message)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
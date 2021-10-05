package com.bignerdranch.android.materialdesign.ui.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.materialdesign.R
import com.bignerdranch.android.materialdesign.databinding.FragmentMarsBinding
import com.bignerdranch.android.materialdesign.model.rest.PictureOfTheDayDataMars
import com.bignerdranch.android.materialdesign.util.toast
import com.bignerdranch.android.materialdesign.viewmodel.MarsViewModel
import com.bumptech.glide.Glide

class MarsFragment : Fragment() {

    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MarsViewModel by lazy {
        ViewModelProvider(this).get(MarsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayDataMars) {
        when (data) {
            is PictureOfTheDayDataMars.Success -> {

                val serverResponseData = data.serverResponseData
                val url = serverResponseData.imgSrc
                if (url.isNullOrEmpty()) {
                    toast("Url is empty")
                } else {
                    context?.let {
                        Glide.with(it)
                            .load(url)
                            .placeholder(R.drawable.ic_no_photo_vector)
                            .error(R.drawable.ic_load_error_vector)
                            .into(binding.imageViewMars)
                    }
                }
            }
            is PictureOfTheDayDataMars.Loading -> {
            }
            is PictureOfTheDayDataMars.Error -> {
                toast(data.error.message)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

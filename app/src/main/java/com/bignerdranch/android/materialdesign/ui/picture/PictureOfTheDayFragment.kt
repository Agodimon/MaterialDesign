package com.bignerdranch.android.materialdesign.ui.picture

import SettingsFragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import com.bignerdranch.android.materialdesign.R
import com.bignerdranch.android.materialdesign.databinding.MainFragmentBinding
import com.bignerdranch.android.materialdesign.databinding.MainFragmentStartBinding
import com.bignerdranch.android.materialdesign.model.rest.PictureOfTheDayData
import com.bignerdranch.android.materialdesign.ui.MainActivity
import com.bignerdranch.android.materialdesign.ui.api.ApiActivity
import com.bignerdranch.android.materialdesign.ui.collapsingtoolbar.CollapsingToolbarActivity
import com.bignerdranch.android.materialdesign.util.hide
import com.bignerdranch.android.materialdesign.util.show
import com.bignerdranch.android.materialdesign.util.toast
import com.bignerdranch.android.materialdesign.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import kotlinx.android.synthetic.main.main_fragment.*

const val BOTTOM_SHEET_HEADER = "BottomSheetHeader"
const val BOTTOM_SHEET_CONTENT = "BottomSheetContent"

class PictureOfTheDayFragment : Fragment() {

    private var _binding: MainFragmentStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetHeader: TextView
    private lateinit var bottomSheetContent: TextView
    private var isExpanded = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}"
                )
            })
        }
        setBottomSheetBehaviour(view.findViewById(R.id.bottom_sheet_container))
        bottomSheetHeader = view.findViewById(R.id.bottom_sheet_description_header)
        bottomSheetContent = view.findViewById(R.id.bottom_sheet_description)
        setBottomAppBar(view)
        viewModel.getData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        binding.imageView.setOnClickListener() {
            isExpanded = !isExpanded
            val set = TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
            TransitionManager.beginDelayedTransition(binding.main, set)
            binding.imageView.scaleType = if (isExpanded) {
                ImageView.ScaleType.CENTER_CROP
            } else {
                ImageView.ScaleType.FIT_CENTER
            }
        }
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                with(binding) {
                    main.show()
                    loadingLayout.hide()
                }
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.image
                data.serverResponseData.explanation?.let {
                    binding.includeLayoutTv.textView.text = it
                }
                if (url.isNullOrEmpty()) {
                    toast("Url is empty")
                } else {
                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                    bottomSheetHeader.text = serverResponseData.title
                    bottomSheetContent.text = serverResponseData.explanation
                }
            }

            is PictureOfTheDayData.Loading -> {
                with(binding) {
                    main.visibility = View.GONE
                    loadingLayout.visibility = View.VISIBLE
                }
            }

            is PictureOfTheDayData.Error -> {
                with(binding) {
                    main.visibility = View.VISIBLE
                    loadingLayout.visibility = View.GONE
                }
                toast(data.error.message)
            }
        }
    }

    private fun setBottomSheetBehaviour(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = from(bottomSheet)
        bottomSheetBehavior.state = STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    STATE_DRAGGING -> toast("STATE_DRAGGING")
                    STATE_COLLAPSED -> toast("STATE_COLLAPSED")
                    STATE_EXPANDED -> toast("STATE_EXPANDED")
                    STATE_HALF_EXPANDED -> toast("STATE_HALF_EXPANDED")
                    STATE_HIDDEN -> toast("STATE_HIDDEN")
                    STATE_SETTLING -> toast("STATE_SETTLING")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(context, CollapsingToolbarActivity::class.java)
        intent.putExtra(BOTTOM_SHEET_HEADER, bottomSheetHeader.text)
        intent.putExtra(BOTTOM_SHEET_CONTENT, bottomSheetContent.text)
        when (item.itemId) {
            R.id.app_bar_fav -> startActivity(Intent(context, ApiActivity::class.java))
            R.id.app_bar_settings -> activity?.apply {
                this.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, SettingsFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            R.id.app_bar_search -> toast(getString(R.string.search))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                with(binding) {
                    bottomAppBar.navigationIcon = null
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                }
            } else {
                isMain = true
                with(binding) {
                    bottomAppBar.navigationIcon = ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_hamburger_menu_bottom_bar
                    )
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}
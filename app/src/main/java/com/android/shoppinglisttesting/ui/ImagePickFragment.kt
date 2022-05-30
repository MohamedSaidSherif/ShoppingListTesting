package com.android.shoppinglisttesting.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.shoppinglisttesting.R
import com.android.shoppinglisttesting.adapters.ImageAdapter
import com.android.shoppinglisttesting.other.Constants.GRID_SPAN_COUNT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_image_pick.*
import javax.inject.Inject

@AndroidEntryPoint
class ImagePickFragment @Inject constructor(
    val imageAdapter: ImageAdapter
) : Fragment(R.layout.fragment_image_pick) {

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        setUpRecycleView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurImageUrl(it)
        }
    }

    private fun setUpRecycleView() {
        rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }
    }
}
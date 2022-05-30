package com.android.shoppinglisttesting.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.shoppinglisttesting.R

class ImagePickFragment: Fragment(R.layout.fragment_image_pick) {

    private lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
    }
}
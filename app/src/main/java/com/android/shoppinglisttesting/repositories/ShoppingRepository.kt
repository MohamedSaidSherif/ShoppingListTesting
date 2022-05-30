package com.android.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import com.android.shoppinglisttesting.data.local.ShoppingItem
import com.android.shoppinglisttesting.data.remote.responses.ImageResponse
import com.android.shoppinglisttesting.other.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>
    fun observeTotalPrice(): LiveData<Float>
    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}
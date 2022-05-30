package com.android.shoppinglisttesting.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.android.shoppinglisttesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@SmallTest for unit test
//@MediumTest for integrated test
//@LargeTest for UI test

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class) // Tell junit that test will run on android device not on local JVM
@SmallTest // Optional
class ShoppingDaoTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.shoppingDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runTest {
        val shoppingItem = ShoppingItem(
            name = "name",
            amount = 1,
            price = 1f,
            imageUrl = "url",
            id = 1
        )
        dao.insertShoppingItem(shoppingItem)
        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runTest {
        val shoppingItem = ShoppingItem(
            name = "name",
            amount = 1,
            price = 1f,
            imageUrl = "url",
            id = 1
        )
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)
        val result = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(result).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceSum() = runTest {
        val shoppingItem1 = ShoppingItem("name", 2, 10f, "url", 1)
        val shoppingItem2 = ShoppingItem("name", 4, 5.5f, "url", 2)
        val shoppingItem3 = ShoppingItem("name", 0, 100f, "url", 3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPriceSum).isEqualTo(2*10f + 4*5.5f)
    }
}
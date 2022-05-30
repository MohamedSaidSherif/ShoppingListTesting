package com.android.shoppinglisttesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.android.shoppinglisttesting.getOrAwaitValue
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

//@SmallTest for unit test
//@MediumTest for integrated test
//@LargeTest for UI test

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest // Optional
@HiltAndroidTest
class ShoppingDaoTestUsingHiltDI {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()
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

        Truth.assertThat(allShoppingItems).contains(shoppingItem)
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
        Truth.assertThat(result).doesNotContain(shoppingItem)
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
        Truth.assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f)
    }
}
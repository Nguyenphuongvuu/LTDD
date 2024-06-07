package haw.bmaajp.groceriesapp.domain.repository

import haw.bmaajp.groceriesapp.domain.model.Account
import haw.bmaajp.groceriesapp.domain.model.ProductItem
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertProducts(products: List<ProductItem>)
    fun getAllProduct(): Flow<List<ProductItem>>
    suspend fun getSelectedProduct(productId: Int): ProductItem
    fun getAllProductCart(isCart: Boolean): Flow<List<ProductItem>>
    suspend fun addCart(productItem: ProductItem)
    suspend fun deleteCart(productItem: ProductItem)
    fun searchProduct(query: String): Flow<List<ProductItem>>

    //account
    fun getAccount(id:Int): Flow<Account>
    fun checkAccount(userName:String, password:String):Int
    fun checkUserName(userName: String):Int
    fun checkEmail(email:String):Int
    fun getIdUser(userName:String, password:String): Int
    suspend fun getLastInsertId():Int
    suspend fun insertAccount(account: Account)
}
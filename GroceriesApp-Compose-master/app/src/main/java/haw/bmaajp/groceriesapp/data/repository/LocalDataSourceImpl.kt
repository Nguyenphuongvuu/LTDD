package haw.bmaajp.groceriesapp.data.repository

import haw.bmaajp.groceriesapp.data.local.ProductDatabase
import haw.bmaajp.groceriesapp.domain.model.Account
import haw.bmaajp.groceriesapp.domain.model.ProductItem
import haw.bmaajp.groceriesapp.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    productDatabase: ProductDatabase
) : LocalDataSource {

    private val productDao = productDatabase.productDao()
    private val accountDao = productDatabase.accountDao()

    override suspend fun insertProducts(products: List<ProductItem>) =
        productDao.insertProducts(products)

    override fun getAllProduct(): Flow<List<ProductItem>> = productDao.getAllProducts()

    override suspend fun getSelectedProduct(productId: Int): ProductItem =
        productDao.getSelectedProduct(productId = productId)

    override fun getAllProductCart(isCart: Boolean): Flow<List<ProductItem>> =
        productDao.getAllProductCart(isCart)

    override suspend fun addCart(productItem: ProductItem) = productDao.addCart(productItem)

    override suspend fun deleteCart(productItem: ProductItem) {
        productItem.isCart = false
        productDao.deleteCart(productItem)
    }

    override fun searchProduct(query: String): Flow<List<ProductItem>> =
        productDao.searchProduct(query)

    //account
    override fun getAccount(id: Int): Flow<Account> =
        accountDao.getAccount(id)

    override fun checkAccount(userName: String, password: String): Int =
        accountDao.checkAccount(userName, password)

    override fun checkUserName(userName: String): Int =
        accountDao.checkUserName(userName)

    override fun checkEmail(email: String): Int =
        accountDao.checkEmail(email)

    override fun getIdUser(userName: String, password: String): Int =
        accountDao.getIdUser(userName, password)

    override suspend fun getLastInsertId(): Int =
        accountDao.getLastInsertId()

    override suspend fun insertAccount(account: Account) =
        accountDao.insertAccount(account)
}
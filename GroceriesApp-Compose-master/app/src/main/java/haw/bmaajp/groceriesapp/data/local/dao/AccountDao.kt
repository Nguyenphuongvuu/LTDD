package haw.bmaajp.groceriesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import haw.bmaajp.groceriesapp.domain.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("select * from TaiKhoan where I_id_tai_khoan = :id")
    fun getAccount(id:Int): Flow<Account>

    @Query("select count(*) from taikhoan " +
            "where T_ten_tai_khoan = :userName and T_mat_khau = :password")
    fun checkAccount(userName:String, password:String):Int

    @Query("select count(*) from taikhoan " +
            "where T_ten_tai_khoan = :userName")
    fun checkUserName(userName: String):Int

    @Query("select count(*) from taikhoan " +
            "where T_email = :email")
    fun checkEmail(email:String):Int

    @Query("select I_id_tai_khoan from taikhoan " +
            "where T_ten_tai_khoan = :userName and T_mat_khau = :password")
    fun getIdUser(userName:String, password:String): Int

    @Query("SELECT I_id_tai_khoan FROM TaiKhoan ORDER BY I_id_tai_khoan DESC LIMIT 1")
    suspend fun getLastInsertId():Int

    @Insert
    suspend fun insertAccount(account: Account)
}